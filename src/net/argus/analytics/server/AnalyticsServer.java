package net.argus.analytics.server;

import java.io.IOException;

import net.argus.beta.com.server.Server;
import net.argus.event.com.server.ServerEvent;
import net.argus.event.com.server.ServerListener;

public class AnalyticsServer extends Server implements ServerListener {
	
	public static final int ANALITICS_PORT = 7865;
	
	public AnalyticsServer() throws IOException {
		super(ANALITICS_PORT);
		
		addServerListener(this);
	}

	@Override
	public void newClient(ServerEvent e) {	
		new AnalyticsServerProcess(e.getSocket()).start();
	}

}
