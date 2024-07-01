package net.argus.analytics.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import net.argus.beta.com.CardinalSocket;
import net.argus.cjson.CJSON;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class DefaultAnalyticsSender extends AnalyticsSender {

	private String host;
	
	public DefaultAnalyticsSender(AnalyticsModule module , String host) {
		this(module, DEFAULT_INTERVAL, host);
	}
	
	public DefaultAnalyticsSender(AnalyticsModule module, int interval, String host) {
		super(module, interval);
		this.host = host;
	}

	@Override
	public void send(AnalyticsModule module) {
		try {
			AnalyticsClient analyticsClient = new AnalyticsClient(host);
			CardinalSocket socket = analyticsClient.open();
			while(module.hasNext()) {
				CJSON data = (CJSON) module.next();

				socket.send(data.toString());
			}
			
			socket.close();
		}catch(NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			Debug.log("Analytics Server not connected (skipped)", Info.ERROR);
		}
		
	}

}
