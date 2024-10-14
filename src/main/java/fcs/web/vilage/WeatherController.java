package fcs.web.vilage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {

  @GetMapping("/location")
  public String location() {
    return "/location.html";
  }

  @GetMapping("/location/getList")
  @ResponseBody
  public List<WeatherDTO> getList(WeatherDTO weatherDTO) throws IOException {
    WeatherData weatherData = new WeatherData();
    return weatherData.lookUpWeather(weatherDTO.getLatitude(), weatherDTO.getLongitude());
  }
}
