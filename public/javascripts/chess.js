var userID
var Moves = []
var Pieces = []
var Squares = []
var isFirstClick = true
var gamerPlaysWhite = true
var whiteIsUp = false
var legalMoves, currentLegalMoves
var startingFen
var fen, currentFen
var moveDisablable = true
var movesHistory = ''
var tmpMovesHistory
var isFen = false
var thinking = false		// true if server is thinking
var playWithFen = false
//fenEnpassant + ' ' + fenPawnMoves + ' ' + fenMoveNumber
//var fenNoteObj = { position: '', turn: '', castling: '', enpassant: '', pawn: 0, moves: 0,
//                   fenCastlingHelper: { 'h1': 'K', 'a1': 'Q', 'h8': 'k', 'a8': 'q', 'e1': 'K,Q', 'e8': 'k,q' },
//                   init: function() {
//                       this.position = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR'
//                       this.turn = 'w'
//                       this.castling = 'KQkq'
//                       this.enpassant = '-'
//                       this.pawn = 0
//                       this.moves = 1
//                   },
//                   changeCastling: function(squareFrom) {
//                       if (this.fenCastlingHelper[squareFrom] === undefined) return
//                       var toDel = this.fenCastlingHelper[squareFrom].split(',')
//                       for(var i=0; i<toDel.length; i++) {
//                           var newCastling = ''
//                           for(var j=0; j<this.castling.length; j++) {
//                               if (this.castling[j] !== toDel[i]) newCastling += this.castling[j]
//                           }
//                       }
//                       if (newCastling.length == 0) this.castling = '-'
//                       else this.castling = newCastling
//    } }
var settings = { showToMove: true, whoPlay: 'ai_human', showTimer: false, howLongThink: 5 }

function pause(millisecondi)
{
    var now = new Date();
    var exitTime = now.getTime() + millisecondi;

    while(true) {
        now = new Date();
        if(now.getTime() > exitTime) return;
    }
}

function unload() {
    $.ajax( {
        url : '/deleteID/' + userID,
        type: 'GET',
        async: false
    })
 }

function init() {
    $(document).ready(function() {
		$('button[name="move"]').attr('disabled', 'true')
		$('button[name="moveback"]').attr('disabled', 'true')
		$('button[name="savegame"]').attr('disabled', 'true')
		$('button[name="loadgame"]').attr('disabled', 'true')
		$(window).resize(function() {
			for(x in Squares) {
				if (typeof Squares[x] == 'object')
					Squares[x].setLocation()
			}
		}) 
		Array.prototype.string = function() {
			var str = ''
			for (var i = 0; i < this.length; i++) 
				str += this[i] + ' '
			return str.trim()
		}
		Array.prototype.get = function(ver, hor) {
			for(var x in this) {
				if(this[x].ver == ver && this[x].hor == hor)
					return this[x]
			}
		}
//        Array.prototype.toJSON = function() {
//            return 'a '
//        }

//        window.onbeforeunload = function() {
//            alert("Unload2")
//            return "text";
//        }

        $.get('/init/' + settings.howLongThink, function(id) {
			userID = id
//			$('#welcome').append('<span> ID: ' + userID + '<//span>')
            $('#welcome').html('<span>Lucky game, player!</span>')
			loadGame()
			newPosition()
		})
    })
	var k = 0
	var abcdefgh = 'abcdefgh'
		for(j = 1; j<=8; j++)
			for(i = 0; i<abcdefgh.length; i++) {
				Squares[k++] = new Square(abcdefgh[i],j)
			}
}

function Square(_ver, _hor) {
	var piece
	var ver = this.ver = _ver; var hor = this.hor = _hor
	var element = $('<div>', {
		id: ver+hor,
		class: 'square'
	})
	
	$('.board').append(element)
	$(element).click(function() {
		if (thinking) return
		if (isFirstClick) {
			if (settings.whoPlay == 'ai_ai') return
			if(isMovable(ver, hor)) {
				$(this).addClass('clickedFrom')
			} else return
			
			var legal = getLegalMoves(ver, hor)
			for(i=0; i<legal.length; i++) {
				Squares.get(legal[i][2], legal[i][3]).setClicked(true, false)
			}
		} else {
			if ($(this).hasClass('clickedTo')) {
				var move = $('.clickedFrom').attr('id')+ver+hor
				addMoveToSet(move, legalMoves, fen)
				movesHistory = movesHistory + ' ' + move
// It means a castling was done by player. Move will be finished later from doMove function.
				if (doMove(move)) return
                finishMove(move)
       		}
			sweepPanels()
//			$('.clickedFrom').removeClass('clickedFrom')
//			$('.clickedTo').css('border-color', '')
//			$('.clickedTo').removeClass('clickedTo')
		}
		isFirstClick = !isFirstClick
	})
	this.setPiece = function(_piece) {
		piece = this.piece = _piece
		$(element).addClass(piece.getClass())
	}
	this.getPiece = function() { return this.piece }
	this.setClicked = function(isClicked, isFrom) {
		if (isClicked) 
			if (isFrom) $(element).addClass('clickedFrom')
			else {
				$(element).addClass('clickedTo')
				if (!settings.showToMove) $('.clickedTo').css('border-color', 'transparent')
			}
		else 
			if (isFrom) $(element).removeClass('clickedFrom')
			else {
				$('.clickedTo').css('border-color', '')
				$(element).removeClass('clickedTo')
			}
	} 
	this.removePiece = function() {
		if (piece !== undefined) {
			$(element).removeClass(piece.getClass())
			delete piece
			delete this.piece
		}
	}

	this.setLocation = function() {
		$(element).css( {top: ((whiteIsUp ? hor-1 : 8-hor)*56/*+$('.board').position().top*/) + 'px', 
			left: ((whiteIsUp ? 104 - ver.charCodeAt(0) : ver.charCodeAt(0) - 97)*56/*+$('.board').position().left*/) + 'px'} )
	}
	this.setLocation()
}

function Piece(type, isWhite) {
	var type
	var ver
	var hor
	var x
	var y
	var isClicked
	this.type = type
	this.isClicked = isClicked = false
	this.isWhite = isWhite
	this.setPosition = function(_ver, _hor) {
		Squares.get(_ver, _hor).setPiece(this)
	}
	this.getClass = function() {
		switch(type) {
		case 'k' :
			return (isWhite ? 'w' : 'b') + 'king'
			break
		case 'q' :
			return (isWhite ? 'w' : 'b') + 'queen'
			break
		case 'r' :
			return (isWhite ? 'w' : 'b') + 'rook'
			break
		case 'b' :
			return (isWhite ? 'w' : 'b') + 'bishop'
			break
		case 'n' :
			return (isWhite ? 'w' : 'b') + 'knight'
			break
		case 'p' :
			return (isWhite ? 'w' : 'b') + 'pawn'
			break
		}
	}
}

function Move(_note) {
	var note = this.note = _note
	var fen, legal
	this.setFen = function(_fen) { fen = this.fen = _fen }
	this.setLegal = function(_legal) { legal = this.legal = _legal }
//    this.toJSON = function() {
//        return '"note":"' + this.note + '","fen":' + this.fen + '","legal":"' + this.legal + '"'
//    }
}

function newPosition(move) {
	if (thinking) return
	sweepAll()
	$.getJSON('/new', function(json) {
		fen = json.fen
		
//		console.info('data.whiteIsUp: ' + data.whiteIsUp + ' type:' + (typeof data.whiteIsUp))
//		whiteIsUp = data.whiteIsUp.trim() == 'true'
		legalMoves = json.legal.split(' ')
//		console.info(legalMoves)
		
		setPositionFromFen(fen)
//        fenNoteObj.init()

		$('button[name="move"]').removeAttr('disabled')
		$('button[name="loadgame"]').removeAttr('disabled')

		for(x in Squares) {
			if (typeof Squares[x] == 'object')
				Squares[x].setLocation()
		}
		$('#notation').html(' ')
		if ($('#winpanel').css('display') == 'block') {
			$('#winpanel').css('display', 'none')
			$('#wintitlepanel').css('display', 'none')
		}
		playWithFen = false
//		console.info('fen: ' + data.fen)
	})
}

function updatePosition() {
//	console.log('movesHistory: "' + movesHistory + '"')
//	console.log(movesHistory.trim().split(' ').length + ' :: ' + (movesHistory.trim().split(' ').length % 2) + ' :: ' + (movesHistory.trim().split(' ').length +1 == 1))
	if (thinking) return
	thinking = true
	sweepPanels()
	setEnableButton(false)
	if (moveDisablable) $('button[name="move"]').attr('disabled', 'true') 
	else {
		$('button[name="move"]').click(function() {
			$.post('/stopPonder', function() { console.log('stop ponder') })
		})		
	}

	addMoveToPage($('<img>', {'id': 'thinking', 'src': '/assets/images/thinking.gif'}),
                  movesHistory.trim().length == 0 ||  movesHistory.trim().split(' ').length % 2 + 1 == 1 )
	$.ajax( {
		url : '/next/' + playWithFen,
		data : JSON.stringify({ history: movesHistory.trim(), fen: startingFen }),
		type : 'POST',
		contentType: 'application/json',
		success: function(json) {
			$('button[name="move"]').click(updatePosition)

			var move = json.bestmove.substring(json.bestmove.indexOf('bestmove') + 'bestmove'.length, json.bestmove.length).trim()
            movesHistory += ' ' + move
//			console.info('fenfrompos: ' + getFenFromPosition())
//			console.info('	  oldfen: ' + json.oldfen)

			addMoveToSet(move, json.oldlegal.split(' '), json.oldfen)

            if (json.status.indexOf('bestmove') !== 0) {
                gameOver(json.status); return
            }
            fen = currentFen = json.newfen
            legalMoves = currentLegalMoves = json.newlegal.split(' ')
			doMove(move)
			addClickToMove($('#thinking').parent(), move)
			$('#thinking').replaceWith(move)
			thinking = false
			if (json.gamover !== 'PROCESS') {
				gameOver(json.gamover)
				setEnableButton(true)
				return
			} else if (settings.whoPlay == 'ai_ai') {
				setTimeout(updatePosition, 500)
			} else {
				setEnableButton(true)
			} } } )
}

function doMove(move) {
	var squareFrom = Squares.get(move[0], move[1])
	var squareTo = Squares.get(move[2], move[3])
	
	if (squareFrom !== undefined) {
		var pieceFrom = squareFrom.getPiece()
		if (pieceFrom !== undefined && squareTo !== undefined) {
			var pieceTo = squareTo.getPiece()

            // Something additional conditions for fen notation
//            fenNoteObj.turn = movesHistory.trim().split(' ').length % 2 == 1 ? 'b' : 'w'
//            fenNoteObj.enpassant = '-'
//            fenNoteObj.pawn += 1
//            fenNoteObj.moves = (movesHistory.trim().split(' ').length / 2 >> 0) + 1
//
//            if (pieceFrom.type == 'p') {
//                fenNoteObj.pawn = 0
//                if (Math.abs(squareFrom.hor - squareTo.hor) > 1) {
//                    fenNoteObj.enpassant = squareFrom.ver + (pieceFrom.isWhite ? '3' : '6')                                // en passant square
//                }
//            } else if (pieceFrom.type == 'r' || pieceFrom.type == 'k') {
//                if (fenNoteObj.castling !== '-') fenNoteObj.changeCastling(squareFrom.ver + squareFrom.hor)
//            }
            // ==============================================

			if (pieceTo != undefined) { squareTo.removePiece()/*; fenNoteObj.pawn = 0	*/}										// capture
			if (Math.abs(squareFrom.ver.charCodeAt(0) - squareTo.ver.charCodeAt(0)) > 1 && pieceFrom.type == 'k') {			// castling
				doMove(getRookForCastling(squareFrom, squareTo))
//                fenNoteObj.pawn -= 1
            }
//            doMove(getRookForCastling(true, squareFrom, squareTo) + getRookForCastling(false, squareFrom, squareTo) )
			if (squareFrom.ver != squareTo.ver && pieceFrom.type == 'p' && pieceTo == undefined)							// en passant
				Squares.get(squareTo.ver, squareFrom.hor).removePiece()
			if ((squareTo.hor == 1 || squareTo.hor == 8) && pieceFrom.type == 'p') {										// promotion
				if (move[4] !== undefined) pieceFrom = new Piece(move[4], squareTo.hor == 8)
				else {
					var promotedPieces = { q: 'queen', r: 'rook', b: 'bishop', n: 'knight' }
					function finishPromotion() {
						sweepPanels()
//						$('.promotion').css('display', 'none')
//						$('.clickedFrom').removeClass('clickedFrom')
//						$('.clickedTo').css('border-color', '')
//						$('.clickedTo').removeClass('clickedTo')
						isFirstClick = true
						squareTo.setPiece(pieceFrom)
						squareFrom.removePiece()
						
						finishMove(move)
//						updatePosition(move)
						for (var i in promotedPieces) {
							$('#' + promotedPieces[i]).removeClass((pieceFrom.isWhite ? 'w' : 'b') + promotedPieces[i])
							$('#' + promotedPieces[i]).off('click')
						}
					}
						
					$('.promotion').css('display', 'block')
					for (var i in promotedPieces) {
						$('#' + promotedPieces[i]).addClass((pieceFrom.isWhite ? 'w' : 'b') + promotedPieces[i])
//						I don't know why it doesn't want to be called by anonimously (lambda-way), without the variable 'f'
						var f = function(i) {
							$('#' + promotedPieces[i]).click(function() {
								pieceFrom = new Piece(i, squareTo.hor == 8)
								move += i
								movesHistory += i 
								finishPromotion()
							})
						}
						f(i)
					}
//                    fenNoteObj.position = getFenFromPosition()
					return true
				}
			}
			squareTo.setPiece(pieceFrom)
			squareFrom.removePiece()
//            fenNoteObj.position = getFenFromPosition()
		}
	}
	return false
}

function finishMove(move) {
//    Moves[Moves.length-1].setNewFen(getFen())
    addMoveToPage(move, movesHistory.trim().split(' ').length % 2 == 1)
    if (settings.whoPlay != 'human_human') {
        updatePosition(move)
        return
    }

    setEnableButton(false)
	$.ajax( {
		url : '/getLegalMoves/' + playWithFen, 
		data : JSON.stringify({ history: movesHistory.trim(), 
			fen: ( function(i) {
				if (playWithFen) {
					return startingFen 
				} else { return fen	}
			})() 
		}),
		type : 'POST',
		contentType: 'application/json', 
		success: function(json) {
			fen = currentFen = json.fen
			legalMoves = currentLegalMoves = json.legal.split(' ')
			setEnableButton(true)
		},
		error: function(err) {
			setEnableButton(true)
			alert('status ' + err.status + '\n' + err.responseText)
		}
	})
}

function doMoveBack() {
	if (thinking) return
    removeGrayMoves()
    Moves.length = movesHistory.trim().split(' ').length
	if (Moves[Moves.length - 1] === undefined) return
	var lastMove = movesHistory.substring(movesHistory.lastIndexOf(' '), movesHistory.length).trim()
	movesHistory = movesHistory.substring(0, movesHistory.lastIndexOf(' '))
	
	sweepBoard()
	fen = currentFen = Moves[Moves.length - 1].fen
    legalMoves = currentLegalMoves = Moves[Moves.length - 1].legal
	setPositionFromFen(fen)

	delete Moves[Moves.length - 1]
	$('#notation span').last().remove()
	if ($('#notation > div').last().children().length == 0)
		$('#notation > div').last().remove()
	
	Moves.length = Moves.length - 1
    sweepPanels()

//	if ($('#winpanel').css('display') == 'block') {
//		$('#winpanel').css('display', 'none')
//		$('#wintitlepanel').css('display', 'none')
//	}
}

function setPosition(from, to) {
	var piece = Pieces.get(from[0], from[1]); 
	if (piece !== undefined) piece.setPosition(to[0], to[1])
}

function isMovable(ver, hor) {
	var piece = Squares.get(ver, hor).piece
	var spl = movesHistory.trim().split(' ')
//	console.log(' movesHistory: "' + movesHistory.trim() + '" spl: ' + spl + ' size=' + spl.length)
//	console.log('piece: ' + piece.type + ' piece.isWhite: ' + piece.isWhite + ' cond: ' + (Moves.length % 2 == 0))
	return piece !== undefined &&
//		   piece.isWhite === Moves.length % 2 == 0
		   (piece.isWhite === ((movesHistory.trim().split(' ').length % 2 == 0) || movesHistory.trim().length == 0))
}

function getLegalMoves(ver, hor) {
	var legal = [], i=0
	for(c in legalMoves) {
		if (legalMoves[c][0] == ver && legalMoves[c][1] == hor)
			legal[i++] = legalMoves[c]
	}
	return legal
}

function addMoveToSet(move, oldLegal, oldFen, newLegal, newFen) {
	var moveObj = new Move(move)
	moveObj.setFen(oldFen)
	moveObj.setLegal(oldLegal)
    Moves[Moves.length] = moveObj
}

function addMoveToPage(move, isWhite, dontCreateClick) {
    var moveElement
    var semiMoveElement = $('<span>')
    semiMoveElement.append(move)
    removeGrayMoves()

    if (typeof move !== 'object' && !dontCreateClick) {
        addClickToMove(semiMoveElement, move)
    }
    if (isWhite) {
        moveElement = $('<div>', {'text':  ($('#notation').children().length+1) + '. '/*, class: 'toDelete'*/})
        $('#notation').append(moveElement)
        semiMoveElement.append('&nbsp;')
    } else {
        moveElement = $('#notation').children().last()
    }
    moveElement.append(semiMoveElement)
    var theight = 0
    $('#notation').children().each(function() {
        theight += $(this).height()
    })
    $('#notation').scrollTop(theight - $('#notation').height())
}

function addClickToMove(semiMoveElement, move) {
	var moveObj = Moves[Moves.length-1]
    var moveNum = Moves.length
	semiMoveElement.css('cursor', 'pointer')
    tmpMovesHistory = movesHistory
	semiMoveElement.click(function() {
		if (thinking) return
		sweepBoard()
		var parentIndex = $(this).parent().index()
		var index = $(this).index()
        $('div#notation').find('span').css('color', 'inherit')
        $('div#notation .toDelete').removeClass('toDelete')
        $('div#notation > div:lt(' + (parentIndex+1) + ')').css('color', 'inherit')
        $('div#notation > div:gt(' + (parentIndex) + ')').css('color', 'gray')
        $('div#notation > div:gt(' + (parentIndex) + ')').addClass('toDelete')
        $('div#notation > div:gt(' + (parentIndex) + ')').children('span').addClass('toDelete')
		$(this).next('span').css('color', 'gray'); $(this).next('span').addClass('toDelete')

//        setPositionFromFen(moveObj.newfen)
//		doMove(moveObj.note)

		var movesHstArray = tmpMovesHistory.trim().split(' ')     // array
        movesHstArray.length = parentIndex * 2 + index + 1
        movesHistory = movesHstArray.join(' ')

        legalMoves = Moves.length > moveNum ? Moves[moveNum].legal : currentLegalMoves
        fen = Moves.length > moveNum ? Moves[moveNum].fen : currentFen
        setPositionFromFen(fen)
	})
}

function moveDownShow() {
	var $spanArray = $('div#notation').find('span').not('.toDelete')
	spanIndex = $spanArray.index($spanArray.last()) - 1
	if (spanIndex >= 0) $spanArray[spanIndex].click()
}

function moveUpShow() {
	var $spanArray = $('div#notation').find('span.toDelete')
	if ($spanArray.length > 0) $spanArray[0].click()
}

function removeGrayMoves() {
    $('.toDelete').remove()
}

function getFenFromPosition() {
//"fen" -> "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
	var k = 0
	var out = ''
	var abcdefgh = 'abcdefgh'
		for(j = 8; j>0; j--) {
			if (k != 0) { out += k; k=0 }
			if (out.length != 0) out += '/'
			for(i = 0; i<abcdefgh.length; i++) {
				var piece = Squares.get(abcdefgh[i],j).piece
				if (piece !== undefined) {
					if (k != 0) { out += k; k=0 }
					out += (piece.isWhite ? piece.type.toUpperCase() : piece.type.toLowerCase())
				} else {
					k++
				}
			}
		}
    if (k != 0) { out += k; k=0 }
//	console.log('FEN: ' + out)
	return out
}

function getFen() {
//    return fenNoteObj.position + ' ' +
//           fenNoteObj.turn + ' ' +
//           fenNoteObj.castling + ' ' +
//           fenNoteObj.enpassant + ' ' +
//           fenNoteObj.pawn + ' ' +
//           fenNoteObj.moves
//    for(var key in fenNoteObj)
//        if (fenNoteObj.hasOwnProperty(key))
//            out += fenNoteObj[key]+' '
}

function setPositionFromFen(fen) {
	fenFinished = false
	var	horIndex = 0
	var	verIndex = 0
	var k = 0
	fen = fen.trim()

	for(i=0; !fenFinished && i < fen.length; i++) {
		var currentChar = fen[i];
		var digit = parseInt(currentChar)
		if (!isNaN(digit)) {
			verIndex += digit;
		} else if (currentChar == '/') {
			horIndex++
			verIndex = 0
		} else if (currentChar == ' ') {
			fenFinished = true;
		} else {
			var isWhite = currentChar == currentChar.toUpperCase()
			Pieces[k] = new Piece(currentChar.toLowerCase(), isWhite) 
			Pieces[k].setPosition(String.fromCharCode(verIndex+97), 8 - horIndex)
			k++; verIndex++
		}
	}
}

function sweepAll() {
	movesHistory = ''
	Moves = []
	sweepBoard()
}

function sweepBoard() {
	var pieces = ['king', 'queen', 'rook', 'bishop', 'knight', 'pawn']
	var colors = ['w', 'b']
	var k = 0
	var abcdefgh = 'abcdefgh'
		for(j = 1; j<=8; j++)
			for(i = 0; i<abcdefgh.length; i++)
				Squares.get(abcdefgh[i],j).removePiece()
//				Squares[k++] = new Square(abcdefgh[i],j)

				for(i in pieces)
					for(j in colors) {
						$('.square').removeClass(colors[j] + pieces[i])
					}
	sweepPanels()
}

function sweepPanels() {
//	isFirstClick = true
	$('.promotion').css('display', 'none')
	$('.clickedFrom').removeClass('clickedFrom')
	$('.clickedTo').css('border-color', '')
	$('.clickedTo').removeClass('clickedTo')
	$('#winpanel').css('display', 'none')
	$('#wintitlepanel').css('display', 'none')
}

function setEnableButton(val) {
	if (val) {
		$('button[name="newgame"]').removeAttr('disabled')
		$('button[name="savegame"]').removeAttr('disabled')
		$('button[name="loadgame"]').removeAttr('disabled')
		$('button[name="move"]').removeAttr('disabled')

		$('button[name="moveback"]').removeAttr('disabled')
	} else {
		$('button[name="newgame"]').attr('disabled', 'true')
		$('button[name="savegame"]').attr('disabled', 'true')
		$('button[name="loadgame"]').attr('disabled', 'true')
		$('button[name="moveback"]').attr('disabled', 'true')
	}
    enableMoveBtn(val)
}

function gameOver(result) {
//    addMoveToPage(result/*, false, true*/)
//	console.log('Game over: ' + result+ ' top='+$('.board').position().top+' left='+$('.board').position().left)
//	var element = $('<img>')
//	$(element).attr('src', )
//	$(element).addClass('square')
    thinking = false
    removeGrayMoves()
    $('#thinking').remove()
    if ($('#thinking').parents('div').first().children('span').length <= 1)
        $('#thinking').parents('div').first().remove()
    setEnableButton(true)
	$('#winpanel').css('display', 'block')
	$('#winpanel').css({ top: $('.board').position().top + 'px', left: $('.board').position().left + 'px' })
	$('#wintitlepanel').css('display', 'block')
	$('#wintitlepanel').css({ top: $('.board').position().top + 'px', left: $('.board').position().left + 'px' })
//	$('#wintitlepanel').css({ 'top': '100px', 'left': '200px' })
	var res = (result.trim() == 'BLACK_MATE') ? 'Black win!' :
			  (result.trim() == 'WHITE_MATE') ? 'White win!' : 'Draw!' 
	$('#wintitle').html(res)
}
//function getRookForCastling(firstSquare, from, to) {
function getRookForCastling(from, to) {
    function getHalf(firstSquare, from, to) {
        var isw = from.piece.isWhite
        var issh = to.ver.charCodeAt(0) - from.ver.charCodeAt(0) > 0
        var square = firstSquare ? isw ? issh ? Squares.get('h', 1) : Squares.get('a', 1)
								 : issh ? Squares.get('h', 8) : Squares.get('a', 8)
					   : isw ? issh ? Squares.get('f', 1) : Squares.get('d', 1)
								 : issh ? Squares.get('f', 8) : Squares.get('d', 8)
        return square.ver + square.hor
    }
    return getHalf(true, from, to) + getHalf(false, from, to)
}

function turnSide() {
    console.log('turnSide')
//	sweepAll()
	whiteIsUp = !whiteIsUp
	for ( var i in Squares) {
		if (typeof Squares[i] == 'object') Squares[i].setLocation()
	}
//	setPositionFromFen(fen)
}

function moveNow() {
	updatePosition('')
}

function start() {
	$.get('/start', function(data) {
		console.info('returned: ' + data)
	})
}

function isFen(cb) {
	isFen = cb.checked 
}

function showAvailableMoves(input) {
	if (input.checked) $('.clickedTo').css('border-color', '')
	else $('.clickedTo').css('border-color', 'transparent')
	settings.showToMove = input.checked
}

function setPlayers(input) {
	if ($(':radio#ai_ai').attr('checked')) { settings.whoPlay = 'ai_ai' } else  
	if ($(':radio#human_human').attr('checked')) { settings.whoPlay = 'human_human' } else settings.whoPlay = 'ai_human'
}

function setPonderTime() {
	moveDisablable = true
	if ($(':radio#time1').attr('checked')) { settings.howLongThink = 1 } else  
	if ($(':radio#time5').attr('checked')) { settings.howLongThink = 5 } else 
	if ($(':radio#time10').attr('checked')) { settings.howLongThink = 10 } else {
		settings.howLongThink = -1
		console.log('moveDisablable = false')
		moveDisablable = false 
	} 
	$.get('/setPonderTime/' + settings.howLongThink)
}
function doSmth() {
//	Я «Пять недель на воздушном шаре» вообще всю жизнь с одиннадцатой главы всю жизнь читал.
    for (var i=0; i<Moves.length; i++) {
        console.info(Moves[i].note)
        console.info('oldfen: ' + Moves[i].oldfen)
        console.info('newfen: ' + Moves[i].newfen)
        console.info('oldlegal: ' + Moves[i].oldlegal)
        console.info('newlegal: ' + Moves[i].newlegal)
        console.log('\n')
    }
}

function print(move) {
//	for ( var x in Squares) {
//		console.log(Squares[x].ver + Squares[x].hor + " " + Squares[x].piece.isWhite + " " + Squares[x].piece.type )
//				+ (Squares[x].piece == undefined ? 'und' : Squares[x].piece.type) + 
//				+ (Squares[x].piece == undefined ? 'und' : Squares[x].piece.isWhite))
//	}
	console.log('####################################')
    if (move) {
        console.log('move: ' + move.note)
        console.log('move.fen: ' + move.fen)
        console.log('move.legal: ' + move.legal)
	} 
	
	console.log('----------------------------------------')
	console.log('getFenFromPosition: ' + getFen())
	console.log('               FEN: ' + fen)
	console.log('History: ' + movesHistory)
	console.log('Legal: ' + legalMoves)
//	for(var i=0; i < Moves.length; i++) {
//		console.log(Moves[i].note + ' :: ' + Moves[i].fen)
//	}
    console.log(JSON.stringify(Moves))
	console.log('####################################')
}

function saveGame() {
	downloadFile({
		history: movesHistory.trim(),
		fen: fen,
		legal: legalMoves.string(),
        moves: Moves,
		userid: userID	})
}

function loadGame() {
    var files, file, extension
    function loadGame(file) {
//    	console.log('============================')
//    	console.log('userid=' + userID)
//    	console.log('movesHistory=' + movesHistory)
//    	console.log('fen=' + fen)
//    	console.log('============================')
        $.ajax({
            url: '/loadGame',  //server script to process data
            type: 'POST',
            xhr: function() {  // custom xhr
                myXhr = $.ajaxSettings.xhr()
                if(myXhr.upload){ // check if upload property exists
                    myXhr.upload.addEventListener('progress',progressHandlingFunction, false) // for handling the progress of the upload
                }
                return myXhr
            },
            async: false,
            //Ajax events
            beforeSend: function() { },
            success: function(json) {
//            	console.log('data.fen: ' + json.fen)
//            	console.log('data.legal: ' + json.newlegal)
//            	console.log('data.history: ' + json.history)
            	if (json.fen == undefined || json.fen.trim() == "") return
            	sweepAll()

                var movesJson = JSON.parse(json.moves)[0]

                $('#notation').children().remove()
            	startingFen = fen = currentFen = json.fen
            	legalMoves = currentLegalMoves = json.newlegal.split(' ')
            	playWithFen = json.history == undefined || json.history.trim() == ''
            	movesHistory = json.history
            	setPositionFromFen(fen)
            	$(':file').val('')

                // Arrays movesJson (received from JSON) and movesHst (turned from moves history) must have the same length
                // to avoid incorrect recovery from fen
                var withoutClick = movesJson.length === 0
            	if (movesHistory.trim().length > 1) {
	            	var movesHst = movesHistory.trim().split(' ')
	            	for(var i=0; i<movesHst.length; i++) {
                        var note = withoutClick ? movesHst[i] : movesJson[i].note
                        if (!withoutClick) addMoveToSet(movesJson[i].note, movesJson[i].legal.trim().split(' '), movesJson[i].fen)
	            		addMoveToPage(note, i%2==0, withoutClick)
	            	}
            	}
//            	$('button[name="move"]').removeAttr('disabled')
//            	$('button[name="loadgame"]').removeAttr('disabled')
                setEnableButton(true)
            },
            error: function() { console.log('ERROR') },
            // Form data
            data: new FormData($('form')[0]),
            //Options to tell JQuery not to process data or worry about content-type
            cache: false,
            contentType: false,
            processData: false
        });
    }
    
   	$(':file').change(function(e) {
   		loadGame(e.target.files[0])
  	})
      	
    function progressHandlingFunction(e) {
    	if(e.lengthComputable){
    		$('progress').attr({value:e.loaded,max:e.total});
    	}
    }
}



