package WeatherAppSystem;

import WeatherAppSystem.functions.*;
import WeatherAppSystem.functions.Feature;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
public class Menu {

	public static void main(String[] args) throws FileNotFoundException {
		//Initialise Menu
		menu();
	}

	public static String menu() throws FileNotFoundException {
		
		// switch statement
		boolean selection = false;
		JSONObject weatherData = new JSONObject();
		
		Scanner scan = new Scanner(System.in);

		Feature feat = new Feature();
		while(selection == false) {
			// title
			System.out.print("---- Weather Forecast App ----  \n\n"+"Please select an option from the Menu: \n");

			// options in the menu
			System.out.print("1. Option A (Basic) \n"+"2. Option B (Basic) \n"+"3. TODO (Currently Blank) \n"+"Q. TODO (Currently Blank) \n");
			
			switch (scan.nextLine().toLowerCase()) {
				case "1":
					weatherData = feat.optionA(scan);
					break;
				case "2":
					//TODO call Previous Weather Forecasts function
					if (weatherData != null) {
						feat.optionB(scan, weatherData);
					}
					else {
						System.err.println("Please use feature A first. ");
					}
					break;
				case "3":
					//TODO (Currently Blank)
					break;
				case "q":
					System.out.println("Successfully exiting the program!..");
					selection = true;
					break;
				default:
					System.err.println("Invalid input. Please try again.");
			}
		}
		//return variable
		return null;
	}
}