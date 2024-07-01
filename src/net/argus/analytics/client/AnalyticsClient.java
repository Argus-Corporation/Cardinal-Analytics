package net.argus.analytics.client;

import java.net.UnknownHostException;

import net.argus.beta.com.client.Client;

public class AnalyticsClient extends Client {
	
	public static final int ANALITICS_PORT = 7865;

	public AnalyticsClient(String host) throws UnknownHostException {
		super(host, ANALITICS_PORT);
	}

}
