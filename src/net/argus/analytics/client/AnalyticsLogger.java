package net.argus.analytics.client;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;
import net.argus.util.debug.Logger;

public class AnalyticsLogger implements Logger {
	
	private List<CJSONValue> logs = new ArrayList<CJSONValue>();
	private List<CJSONValue> errorLogs = new ArrayList<CJSONValue>();

	@Override
	public void log(String text) {
		logs.add(new CJSONString(text));
	}

	@Override
	public void errorLog(String text) {
		errorLogs.add(new CJSONString(text));
	}
	
	public List<CJSONValue> getErrorLogs() {
		return errorLogs;
	}
	
	public List<CJSONValue> getLogs() {
		return logs;
	}
	
}
