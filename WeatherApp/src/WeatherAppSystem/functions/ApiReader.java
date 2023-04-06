package WeatherAppSystem.functions;
import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiReader {
	// Variables/properties
	private String API_URL;
	private String API_URL2;

	//Getter & setter
	public String getAPI_URL() {
		return API_URL;
	}
	
	public String getAPI_URL2() {
		return API_URL2;
	}

	public void setAPI_URL(String apiurl) {
		this.API_URL = apiurl;
	}

	public void setAPI_URL2(String apiurl2) {
		this.API_URL2 = apiurl2;
	}
	
	//Constructor
	public ApiReader(String apiurl) throws Exception, IOException {
		this.API_URL = apiurl;
	}

	public String connect() throws MalformedURLException, IOException {
		URL url = new URL(this.API_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responsecode = conn.getResponseCode();
		
		if(responsecode == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = br.readLine();
			return response;
		} else {
			return null;
		}
	}
}