package net.argus.analytics;

import java.io.IOException;

import net.argus.analytics.server.AnalyticsServer;
import net.argus.util.debug.Debug;

public class AnalyticsMain {
	
	public static void main(String[] args) throws IOException {
		AnalyticsServer server = new AnalyticsServer();
		server.open();
		Debug.log("Start receiving data at localhost:" + server.getPort());
	}

}
