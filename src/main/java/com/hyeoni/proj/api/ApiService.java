package com.hyeoni.proj.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:/properties/config.properties")
public class ApiService {

	// API 신청 시 발급받은 인증키 설정
	@Value("${openapi.key}")
	private String AUTH_KEY;

	@Autowired ApiDao apiDao;
	
	@Autowired ModelMapper modelMapper;
	
	
	public void getApi() throws Exception {
		
		LocalDate date = LocalDate.now().minusDays(2);
		String bgnDate = date.format(DateTimeFormatter.ofPattern("yyyyMM"))+"01";
		String endDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		// 발급받은 API 유형(통합(TO0),그룹(GR0))
		String apiurl = "http://www.localdata.go.kr/platform/rest/GR0/openDataApi?authKey=" + AUTH_KEY
				+ "&pageSize=500&lastModTsBgn="+bgnDate+"&lastModTsEnd="+endDate;

		URL url = new URL(apiurl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

		StringBuffer sb = new StringBuffer();
		String tempStr = null;
		while (true) {
			tempStr = br.readLine();
			if (tempStr == null)
				break;
			sb.append(tempStr);
		}
		br.close();

		JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
		JSONObject json = xmlJSONObj.getJSONObject("result").getJSONObject("body").getJSONObject("rows");

		if (json.get("row").getClass().equals(JSONArray.class)) {
			JSONArray jsonArr = (JSONArray) json.get("row");
			for (Object arr : jsonArr) {
				JSONObject obj = (JSONObject) arr; // JSONArray 데이터를 하나씩 가져와 JSONObject로 변환해준다.
				insertDB(obj);
			}
		} else {
			insertDB((JSONObject) json.get("row"));
		}

	}
	
	@SuppressWarnings("unchecked")
	private void insertDB(JSONObject obj) throws Exception {
	    Map<String, Object> map = new ObjectMapper().readValue(obj.toString(), Map.class);
	    apiDao.insertApiData(map);
	}
}
