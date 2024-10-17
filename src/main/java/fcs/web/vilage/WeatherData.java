package fcs.web.vilage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;

public class WeatherData {

  private String nx = "";	//위도
  private String ny = "";	//경도
// API는 최근 1일간의 자료만 제공하기 때문에 현재시간의 12시간 전으로 설정
  private String baseDate = calculateBaseDate();	// 12시간 전 날짜
  private String baseTime = calculateBaseTime();	// 12시간 전 시간
  private String type = "json";	//조회하고 싶은 type(json, xml 중 고름)

  public String lookUpWeather(int x, int y, String address) throws IOException, JSONException {

//  String address = toAddress(x, y);    // 좌표를 이용하여 주소 구하기

//    System.out.println(address);
//    소수점 제거 x, y
//    nx = x.substring(0, x.indexOf("."));    // 현재 위도
//    ny = y.substring(0, y.indexOf("."));    // 현재 경도
//    System.out.println(nx + " " + ny);
//		참고문서에 있는 url주소
    String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
//    홈페이지에서 받은 키 ( 공공 데이터 포털 )
    String serviceKey = "BSfQt0ifiEuIu2Ms%2BqzwkXCn6eMPnhckqcU9fvN7Z8shVRJTH2Cbm1DHseqaYXD4d0En7PZkbVBMCF%2BWQay6Ug%3D%3D";

    StringBuilder urlBuilder = new StringBuilder(apiUrl);
    urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
    urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(x), "UTF-8")); //경도
    urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(y), "UTF-8")); //위도
    urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
    urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
    urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));	/* 타입 */

    /*
     * GET방식으로 전송해서 파라미터 받아오기
     */
    URL url = new URL(urlBuilder.toString());

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());

    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }

    rd.close();
    conn.disconnect();
    String result= sb.toString();

    //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//
    System.out.println(result);

    List<WeatherDTO> weatherDataList = new ArrayList<>();

    JSONObject jsonObject = new JSONObject(result);
    System.out.println(jsonObject);
    JSONObject response = jsonObject.getJSONObject("response");
    JSONObject body = response.getJSONObject("body");
    JSONArray items = body.getJSONObject("items").getJSONArray("item");


    for(int i = 0 ; i < items.length(); i++) {
      JSONObject item = items.getJSONObject(i);  // JSON 객체를 가져옴
      String baseDate = item.getString("baseDate");
      String baseTime = item.getString("baseTime");
      String category = item.getString("category");
      String obsrValue = item.getString("obsrValue");
      int nx = item.getInt("nx");
      int ny = item.getInt("ny");

      WeatherDTO weatherDTO = new WeatherDTO(baseDate, baseTime, category, obsrValue, nx, ny, address);
      weatherDataList.add(weatherDTO);
    }

    return weatherInfoText(weatherDataList);
  }

  public String toAddress(String x, String y) throws IOException, JSONException {

    JsonReader jsonReader = new JsonReader();

    // 홈페이지에서 받은 키 (국토교통부 - 디지털트윈국토)
    String apiKey = "2BDC8B43-9DE6-3157-810B-3E888EBC89A2";
    // 위도
    String latitude = x;
    // 경도
    String longitude = y;
    // api 테스트
    // # 파라미터 종류 확인 : http://www.vworld.kr/dev/v4dv_geocoderguide2_s002.do
    String reverseGeocodeURL = "http://api.vworld.kr/req/address?"
        + "service=address&request=getAddress&version=2.0&crs=epsg:4326&point="
        +  longitude + "," +  latitude
        + "&format=json"
        + "&type=both&zipcode=true"
        + "&simple=false&"
        + "key="+apiKey;
    String getJson = jsonReader.callURL(reverseGeocodeURL);
    System.out.println("getJson => " + getJson);
    Map<String, Object> map = jsonReader.string2Map(getJson);
    System.out.println("json => " + map.toString());

    // 지도 결과 확인하기
    ArrayList reverseGeocodeResultArr = (ArrayList) ((HashMap<String, Object>) map.get("response")).get("result");

    String parcel_address = "";
    String road_address = "";
    String address = "";

    for (int counter = 0; counter < reverseGeocodeResultArr.size(); counter++) {
      HashMap<String, Object> tmp = (HashMap<String, Object>) reverseGeocodeResultArr.get(counter);
      System.out.println("tmp = > " + tmp);
      String level0 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level0");
      String level1 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level1");
      String level2 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level2");
      //주소 : 도, 시, 구, 동
      address  = (String) tmp.get("text");

      if (tmp.get("type").equals("parcel")) {
        parcel_address = (String) tmp.get("text");
        parcel_address = parcel_address.replace(level0, "").replace(level1, "").replace(level2, "").trim();
      } else {
        road_address = "도로 주소:" + (String) tmp.get("text");
        road_address = road_address.replace(level0, "").replace(level1, "").replace(level2, "").trim();
      }
    }

    System.out.println("parcel_address = > " + parcel_address);
    System.out.println("road_address = > " + road_address);

    return address;
  }

  public static String calculateBaseDate() {
    return LocalDateTime.now().minusHours(12).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  public static String calculateBaseTime() {
    return LocalDateTime.now().minusHours(12).format(DateTimeFormatter.ofPattern("HHmm"));
  }

  public static String weatherInfoText(List<WeatherDTO> list) {
    StringBuilder word = new StringBuilder(list.get(0).getAddress() + " 마을 현재 기상 정보 - \n");

    for (WeatherDTO weather : list) {
      String category = weather.getCategory();
      String obsrValue = weather.getObsrValue();

      if (category.equals("PTY")) {
        word.append("강수형태: ").append(obsrValue).append(" ");
      } else if (category.equals("REH")) {
        word.append("습도: ").append(obsrValue).append("% ");
      } else if (category.equals("PCP")) {
        word.append("1시간 강수량: ").append(obsrValue).append(" mm ");
      } else if (category.equals("TMP")) {
        word.append("현재 기온: ").append(obsrValue).append("℃ ");
      } else if (category.equals("TMN")) {
        word.append("일 최저기온: ").append(obsrValue).append("℃ ");
      } else if (category.equals("TMX")) {
        word.append("일 최고기온: ").append(obsrValue).append("℃ ");
      } else if (category.equals("UUU")) {
        word.append("풍속(동서성분): ").append(obsrValue).append(" m/s ");
      } else if (category.equals("VVV")) {
        word.append("풍속(남북성분): ").append(obsrValue).append(" m/s ");
      } else if (category.equals("VEC")) {
        word.append("풍향: ").append(obsrValue).append("° ");
      } else if (category.equals("WSD")) {
        word.append("풍속: ").append(obsrValue).append(" m/s ");
      } else if (category.equals("SNO")) {
        word.append("1시간 신적설: ").append(obsrValue).append(" cm ");
      } else if (category.equals("SKY")) {
        word.append("하늘상태: ").append(obsrValue).append(" ");
      } else if (category.equals("RN1")) {
        word.append("1시간 강수량: ").append(obsrValue).append(" mm ");
      } else if (category.equals("WAV")) {
        word.append("파고: ").append(obsrValue).append(" m ");
      } else if (category.equals("LGT")) {
        word.append("낙뢰: ").append(obsrValue).append(" kA ");
      }
    }

    return word.toString();
  }
}