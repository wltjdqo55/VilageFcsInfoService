# 기상청 단기예측 날씨 API (공공데이터포털)

## 개요
이 프로젝트는 각 지역별로 최신 날씨 정보를 확인할 수 있는 간단하게 구현한 웹 페이지입니다.<br />
공공데이터포털의 오픈 API를 활용하였고 기상청의 날씨정보 API를 사용해서 값을 가져옵니다.
<br />
## 주요 기능
1. 현재 날씨 : 각 지역별 현재 날씨 정보를 제공합니다.
2. 지역 강수현황, 습도, 1시간 강수량, 기온, 풍향, 풍속 등의 정보를 제공합니다.
## 사용 기술
- JAVA, Spring boot를 사용하여 개발 하였습니다.
- 엑셀에 있는 지역 정보를 CSV로 변환하여 PostgresSQL에 저장 하였습니다.
- 화면 UI는 HTML, VUE.JS로 표출하였습니다.
## 사용 API
1. 기상청_단기예보
2. 기상청 초단기예보
3. Geolocation API 
    - GPS를 이용해서 현재 좌표(위도,경도)를 구하는 API
4. Geocoder API
    - 좌표정보를 주소로 변환하는 API
## 사용 방법
1. 공공데이터포털에서 인증 키 발급받기
2. 단기예보 조회서비스 오픈 API 신청
3. 단기예보 조회서비스 오픈API 활용가이드_격자 위경도 EXCEL 파일의 데이터를 필요한 데이터만 추출해서 CSV파일로 변환후 POSTGRES SQL에 등록 
4. 코드 구현
## 실행 화면
![화면](https://github.com/user-attachments/assets/06d9fd19-87d6-4490-a994-f68e78b75e82)
![화면](https://github.com/user-attachments/assets/a8bd69a6-d41c-4401-a5b8-104732158f40)
![화면](https://github.com/user-attachments/assets/9a1f0351-4282-45ea-a7aa-78bc663f8802)
![화면](https://github.com/user-attachments/assets/27680b06-1c9f-4dcb-9323-f5a29ac192cf)
![화면](https://github.com/user-attachments/assets/7135a468-7eec-4eff-9e57-6998bf05a919)