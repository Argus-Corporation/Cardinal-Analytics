package net.argus.analytics.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import net.argus.cjson.CJSONBuilder;
import net.argus.cjson.value.CJSONObject;
import net.argus.system.UserSystem;
import net.argus.util.Version;

public class AnalyticsContext {
	
	public static final Version VERSION = new Version("1.0.0");
	
	private UUID uuid;
	private AnalyticsModule module;
	private long timestamp;
	
	public AnalyticsContext(AnalyticsModule module) {
		uuid = UUID.randomUUID();
		
		this.module = module;
		this.timestamp = System.currentTimeMillis();
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public AnalyticsModule getModule() {
		return module;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public CJSONObject toCJSON() {
		CJSONBuilder builder = new CJSONBuilder();
		
		builder.addString(".", "uuid", uuid.toString());
		builder.addString(".", "timestamp", Long.toString(timestamp));
		builder.addString(".", "version", VERSION.toString());
		builder.addString(".", "device_id", getDeviceID());
		
		return builder.getMainObject();
	}
	
	public static String getDeviceID() {
		try {
			return InetAddress.getLocalHost().getHostName() + "_" + UserSystem.getProperty("user.name");
		}catch(UnknownHostException e) {
			return "school_default";
		}
	}
	
	@Override
	public String toString() {
		return "context@" + uuid;
	}

}
