package net.argus.analytics.server;

import java.io.IOException;

import net.argus.beta.com.CardinalSocket;
import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.file.CJSONFile;
import net.argus.instance.Instance;

public class AnalyticsServerProcess extends Thread {
	
	private static final Instance ANALYTICS_SERVER_PROCESS_INSTANCE = new Instance("analytics_data");

	private CardinalSocket socket;
	
	public AnalyticsServerProcess(CardinalSocket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		String data = null;
		try {
			while((data = socket.nextString()) != null) {
				saveData(data);
			}
		}catch(StackOverflowError e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void saveData(String data) throws IOException {
		CJSON cjson = CJSONParser.getCJSON(data);
		CJSONFile file = new CJSONFile(cjson.getString("context.uuid"), cjson.getString("context.device_id"), ANALYTICS_SERVER_PROCESS_INSTANCE);
		if(!file.exists())
			file.createFile();
		file.write(cjson);
	}

}
