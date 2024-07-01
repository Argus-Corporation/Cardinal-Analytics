package net.argus.analytics.client;

import net.argus.cjson.value.CJSONObject;

public interface AnalyticsNode {
	
	public CJSONObject analyse(AnalyticsContext context);
	
	public String getNodeName();

}
