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

	public JSONObject optionA(Scanner scan) throws IOException, ParseException {
	    System.out.println("Please enter the name of a city:");
	    String input = optionC(scan);
	    if (input == null || input.isEmpty()) {
		System.err.println("Incorrect input, please try again!");
		return null;
	    }

	    String geoApi = "http://api.openweathermap.org/geo/1.0/direct?q=" + input
		    + ",826&limit=1&appid=eab646dbd2d8af731b95792030c4d645";
	    String dataApi = "https://api.openweathermap.org/data/2.5/onecall?%s&exclude=minutely,hourly&units=metric&appid=eab646dbd2d8af731b95792030c4d645";

	    try (ApiReader reader = new ApiReader(geoApi)) {
		JSONParser parser = new JSONParser();
		JSONObject cityData = (JSONObject) parser.parse(reader.connect());
		Double lat = (Double) cityData.get("lat");
		Double lon = (Double) cityData.get("lon");
		dataApi = String.format(dataApi, "lat=" + lat + "&lon=" + lon);
	    } catch (IOException | ParseException e) {
		System.err.println(e);
		return null;
	    }

	    try (ApiReader reader = new ApiReader(dataApi)) {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(reader.connect());
	    } catch (IOException | ParseException e) {
		System.err.println(e);
		return null;
	    }
	}

	public void optionB(Scanner scan, JSONObject weatherData) {
	    // Extract the current weather data and weather summary from the JSON object
	    JSONObject current = weatherData.getJSONObject("current");
	    JSONArray weather = current.getJSONArray("weather");
	    JSONObject summary = weather.getJSONObject(0);

	    // Print the weather report
	    System.out.println("Weather report:");

	    // Print the date of the weather report
	    System.out.printf("Date: %s\n", new Date(current.getLong("dt") * 1000));

	    // Print the current temperature and feels-like temperature in Celsius
	    System.out.printf("Current Temperature: %.2f °C : Feels like: %.2f °C\n", current.getDouble("temp") - 273.15,
		    current.getDouble("feels_like") - 273.15);

	    // Print the wind speed and direction in meters per second and degrees, respectively
	    System.out.printf("Wind speed: %.1f m/s Wind direction: %d degrees\n", current.getDouble("wind_speed"),
		    current.getInt("wind_deg"));

	    // Print the relative humidity as a percentage and the visibility in meters
	    System.out.printf("Relative Humidity: %d%%\nVisibility: %d metres\n", current.getInt("humidity"),
		    current.getInt("visibility"));

	    // Print the weather type and description
	    System.out.printf("Weather type: %s\nWeather description: %s\n", summary.getString("main"),
		    summary.getString("description"));

	    // Prompt the user to return to the main menu
	    System.out.println("---Press enter to return to main menu---");
	    scan.nextLine();
	}

	public String optionC(Scanner scan) throws FileNotFoundException {
	    ArrayList<String> cities = new ArrayList<>();
	    try (Scanner file = new Scanner(new FileReader("city.txt"))) {
		while (file.hasNext()) {
		    cities.add(file.next().toUpperCase());
		}
	    }

	    String input = scan.nextLine().toUpperCase();
	    if (input.length() == 3) {
		cities.stream()
			.filter(city -> city.startsWith(input))
			.forEach(System.out::println);
		System.out.println("Please specify the city:");
		input = scan.next().toUpperCase();
	    }
	    return input;
	}

}
