package mediocrechess.uci.com.alonsoruibal.chess.search;

import mediocrechess.core.com.alonsoruibal.chess.Config;
import mediocrechess.core.com.alonsoruibal.chess.search.SearchEngine;
import mediocrechess.core.com.alonsoruibal.chess.search.SearchParameters;

public class SearchEngineThreaded extends SearchEngine {

	Thread thread;

	public SearchEngineThreaded(Config config) {
		super(config);
	}

	/**
	 * Threaded version
	 */
	public void go(SearchParameters searchParameters) {
		synchronized (startStopSearchLock) {
			if (!initialized || searching) {
				return;
			}
			searching = true;
			setInitialSearchParameters(searchParameters);
		}

		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops thinking
	 */
	public void stop() {
		synchronized (startStopSearchLock) {
			while (searching) {
				super.stop();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}