package fcs.web.vilage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherDTO {

  private String baseDate;

  private String baseTime;

  private String category;

  private String obsrValue;

  private int nx;

  private int ny;

  private String latitude;

  private String longitude;

  private String address;

  public WeatherDTO(String baseDate, String baseTime, String category, String obsrValue, int nx, int ny, String address){
    this.baseDate = baseDate;
    this.baseTime = baseTime;
    this.category = category;
    this.obsrValue = obsrValue;
    this.nx = nx;
    this.ny = ny;
    this.address = address;
  }

}
