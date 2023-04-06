package WeatherAppSystem.functions;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import WeatherAppSystem.Menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Feature {

	public JSONObject optionA(Scanner scan) throws FileNotFoundException {

		System.out.println("Please enter the name of a city:");
		String input = optionC(scan);
		// If the city name is empty or is null, we ask them again to input a city name
		if (input != "" && input != null) {
			//input = input.toUpperCase();
			System.out.println("You've selected " + input + "!");

			try {
				// In the future we need to validate whether the input is a valid city
				String geoApi = "http://api.openweathermap.org/geo/1.0/direct?q=" + input
						+ ",826&limit=1&appid=eab646dbd2d8af731b95792030c4d645";
				ApiReader reader = new ApiReader(geoApi);

				// This represents the response of the json data from the api
				String jsonResponse = reader.connect();
				// Create parser to convert string to objects/array
				JSONParser parser = new JSONParser();
				// Making response as a json object
				JSONArray jsonArray = (JSONArray) parser.parse(jsonResponse);
				// This represents the city's properties
				JSONObject citydata = (JSONObject) jsonArray.get(0);
				Double lat = (Double) citydata.get("lat");
				Double lon = (Double) citydata.get("lon");

				String dataApi = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon
						+ "&exclude=minutely,hourly&appid=eab646dbd2d8af731b95792030c4d645";
				reader.setAPI_URL(dataApi);
				jsonResponse = reader.connect();
				// Making response as a json object
				JSONObject weatherData = (JSONObject) parser.parse(jsonResponse);

				return weatherData;
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			System.err.println("Incorrect input, please try again!");
			optionA(scan);
		}
		return null;
	}

	public void optionB(Scanner scan ,JSONObject weatherData) {
		JSONObject current = (JSONObject) weatherData.get("current");
		
		System.out.println("Weather report: ");
		
		//date of weather report  // the api provides date in unix time so it must be converted
		Date convertedDate = new Date((long)current.get("dt")*1000); // * by 1000 because api gives it in seconds while java requires milliseconds
		
		System.out.println("Date: " + convertedDate);
		
		//current temperature
		//   uses -273.15 to convert from kelvin to celsius
		System.out.printf("Current Temperature: %.2f °C  :  Feels like: %.2f °C \n", ((Double) current.get("temp")-273.15)
				, ((Double) current.get("feels_like")-273.15));
		
		//wind speed and direction
		System.out.println("Wind speed: " + current.get("wind_speed") + " Wind direction: " + current.get("wind_deg") + " Degrees");
		
		// Feature D extended weather report
		// current humidity
		System.out.println("Relative Humidity: " + current.get("humidity") + "%");
		
		// visibility
		System.out.println("visibility: " + current.get("visibility") + " metres");
		
		//weather summary
		JSONArray weather = (JSONArray) current.get("weather");
		JSONObject summary = (JSONObject) weather.get(0);
		
		System.out.println("Weather type: " + summary.get("main"));
		System.out.println("Weather description: " + summary.get("description")); 
		
		System.out.println("---Press enter to return to main menu---");
		scan.nextLine();
	}

	public String optionC(Scanner scan) throws FileNotFoundException {
		Scanner file = new Scanner(new FileReader("city.txt")); // this reads the file from the current working directory which needs to be changed
		ArrayList<String> city = new ArrayList<>();
		ArrayList<String> newList = new ArrayList<>();
		while (file.hasNext()) {
			city.add(file.next().toUpperCase());
		}
		
		String input = scan.nextLine().toUpperCase();
		if (input.length() == 3) {
			for (int i = 0; i < city.size(); i++) {
				if (input.subSequence(0, 3).equals(city.get(i).subSequence(0, 3))) {
					newList.add(city.get(i));
				}
			}
			if (newList.size() > 1) {
				for (int i = 0; i < newList.size(); i++) {
					System.out.println(newList.get(i));
				}
				System.out.println("please can you specified the city");
				input = scan.next().toUpperCase();
			}else {
				input = newList.get(0);
			}
			
		}
		file.close();
		return input;
	}

}
