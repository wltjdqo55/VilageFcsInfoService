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

  private final WeatherService weatherService;

  @GetMapping("/location")
  public String location() {
    return "/location.html";
  }

  @GetMapping("/location/getWeatherInfo")
  @ResponseBody
  public String[] getList(RegionDTO regionDTO) throws IOException {
    WeatherData weatherData = new WeatherData();
    List<Object[]> results = weatherService.getRegionNXNY(regionDTO.getRegionParent(), regionDTO.getRegionChild());
    int nx = 0;
    int ny = 0;
    if (!results.isEmpty()) {
      Object[] row = results.get(0);
      // nx와 ny를 Integer로 변환
      nx = Integer.parseInt(row[0].toString());
      ny = Integer.parseInt(row[1].toString());
    }
    return weatherData.lookUpWeather(nx, ny, regionDTO.getRegionParent() + " " + regionDTO.getRegionChild());
  }

  @GetMapping("/location/getRegionParentList")
  @ResponseBody
  public List<String> getRegionParentList() {
    return weatherService.getRegionParentList();
  }

  @GetMapping("/location/getRegionChildList")
  @ResponseBody
  public List<String> getRegionChildList(RegionDTO regionDTO) {
    return weatherService.getRegionChildList(regionDTO.getRegionParent());
  }
}
