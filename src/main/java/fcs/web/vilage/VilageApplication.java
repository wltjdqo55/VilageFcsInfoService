package fcs.web.vilage;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class VilageApplication {

	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		try {
			weatherData.lookUpWeather();
		} catch(IOException | JSONException e){
			e.printStackTrace();
		}


	}
}
