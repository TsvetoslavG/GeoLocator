package net.c0dei.geolocator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

import net.minecraft.util.org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class IPLookup {
	
	public static String lookupParam(InetAddress ip, String param) {
		String playerIP = ip.toString().replaceAll("/", "");
		if(!playerIP.matches("(?i).*127.0.0.1*")) {
			String apiAddress = "http://ip-api.com/json/" + playerIP;
			String JSONData = null;
			try {
				JSONData = IOUtils.toString(new URL(apiAddress));
			} catch(IOException ie) {
				ie.printStackTrace();
			}
			try {
				JSONObject JSONResult = new JSONObject(JSONData);
				if(JSONResult.get("status").equals("fail")) {
					return "ERROR_JSONFAILURE";
				} else {
					return JSONResult.get(param).toString();
				}
			} catch(JSONException je) {
				je.printStackTrace();
				return "ERROR_OTHEREXCEPTION";
			}
		} else {
			return "ERROR_LOCALIP";
		}
	}
	
	public static String getCity(InetAddress ip) {
		return lookupParam(ip, "city");
	}
	
	public static String getLat(InetAddress ip) {
		return lookupParam(ip, "lat");
	}
	
	public static String getLon(InetAddress ip) {
		return lookupParam(ip, "lon");
	}
	
	public static String getZip(InetAddress ip) {
		return lookupParam(ip, "zip");
	}
	
	public static String getTimezone(InetAddress ip) {
		return lookupParam(ip, "timezone");
	}
	
	public static String getAS(InetAddress ip) {
		return lookupParam(ip, "as");
	}
	
	public static String getISP(InetAddress ip) {
		return lookupParam(ip, "isp");
	}
	
}