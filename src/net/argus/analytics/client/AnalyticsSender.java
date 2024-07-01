package net.argus.analytics.client;

import net.argus.instance.Instance;
import net.argus.util.ThreadManager;

public abstract class AnalyticsSender {
	
	public static final int DEFAULT_INTERVAL = 300;
	public static final Instance ANALYTICS_SENDER_INSTANCE = new Instance("analytics-sender");

	private AnalyticsModule module;
	
	public AnalyticsSender(AnalyticsModule module, int interval) {
		this.module = module;
	}
	
	public void startSenderLoop() {
		Instance.startThread(new Thread(() ->  {
			ThreadManager.sleep(1000);
			while(true) {
				send(module);
				
				ThreadManager.sleep(DEFAULT_INTERVAL * 1000);
			}
		}), ANALYTICS_SENDER_INSTANCE);
	}
	
	public abstract void send(AnalyticsModule module);
	
	public AnalyticsModule getModule() {
		return module;
	}

}
