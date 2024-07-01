package net.argus.analytics.client;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONBuilder;
import net.argus.cjson.value.CJSONArray;
import net.argus.cjson.value.CJSONObject;
import net.argus.instance.Instance;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class AnalyticsModule {
	
	public static final int DEFAULT_INTERVAL = 300;
	
	public static final Instance ANALYTICS_INSTANCE = new Instance("analytics");
	
	private List<AnalyticsNode> nodes = new ArrayList<AnalyticsNode>();
	private List<CJSON> analytics = new ArrayList<CJSON>();
	
	private AnalyticsLogger logger = new AnalyticsLogger();
	
	private String moduleName;
	
	private int interval;
	private boolean running = true;
	
	public AnalyticsModule(String analyticsModuleName, int interval) {
		this.moduleName = analyticsModuleName;
		
		if(interval == 0)
			interval = DEFAULT_INTERVAL;
		
		this.interval = interval;
		Debug.addLoggeur(logger);
	}
	
	public void start() {
		running = true;
		Instance.startThread(new Thread(() -> {
			start0();
		}), ANALYTICS_INSTANCE);
	}
	
	private void start0() {

		while(running) {
			CJSONBuilder builder = new CJSONBuilder();
			AnalyticsContext context = new AnalyticsContext(this);
			
			builder.addObject(".", "context", context.toCJSON());
			
			builder.addValue(".", "logs", new CJSONArray(logger.getLogs()));
			builder.addValue(".", "error_logs", new CJSONArray(logger.getErrorLogs()));
			
			builder.addObject(".", "analytics", new CJSONObject());
			
			for(AnalyticsNode node : nodes) {
				CJSONObject ret = node.analyse(context);
				builder.addObject("analytics", node.getNodeName(), ret);		
			}
			
			analytics.add(new CJSON(builder));
			ThreadManager.sleep(interval * 1000);
		}
	}
	
	public void stop() {
		running = false;
	}

	
	public void addNode(AnalyticsNode node) {
		nodes.add(node);
	}
	
	
	public List<AnalyticsNode> getNodes() {
		return nodes;
	}
	
	public String getMouleName() {
		return moduleName;
	}
	
	public boolean hasNext() {
		return analytics.size()>0;
	}
	
	public CJSON next() {
		if(analytics.size() == 0)
			return null;
		
		CJSON val = analytics.get(0);
		analytics.remove(0);
		
		return val;
	}
	
	public int getInterval() {
		return interval;
	}

}
