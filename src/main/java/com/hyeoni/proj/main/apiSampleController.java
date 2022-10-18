package com.hyeoni.proj.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@PropertySource("classpath:/properties/config.properties")
@Controller
public class apiSampleController {

	// API 신청 시 발급받은 인증키 설정
	@Value("${openapi.key}") 
	private String AUTH_KEY;
	
	@RequestMapping(value = "/getApiCall.do", method = { RequestMethod.POST, RequestMethod.GET })
	public void getSampleApi(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {
		// API 호출 시 조회 할 변수 설정 - 요청 변수 참조(jsp화면에서 입력 받은 값 셋팅)
		String localcode = req.getParameter("localcode");
		String bgnYmd = req.getParameter("bgnYmd");
		String endYmd = req.getParameter("endYmd");

		// 발급받은 API 유형(통합(TO0),그룹(GR0))
		String apiurl = "http://www.localdata.go.kr/platform/rest/GR0/openDataApi?authKey=" + AUTH_KEY + "&pageSize=500";

		//조건 설정에 따른 URL변경
		if (localcode != null) {
			apiurl += "&localCode=" + localcode;
		}
		if (bgnYmd != null && endYmd != null) {
			apiurl += "&bgnYmd=" + bgnYmd + "&endYmd=" + endYmd;
		}

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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.getWriter().write(sb.toString());
		
		JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
		JSONObject json = xmlJSONObj.getJSONObject("result").getJSONObject("body").getJSONObject("rows");
		
		if(json.get("row").getClass().equals(JSONArray.class)) {
			JSONArray jsonArr = (JSONArray) json.get("row");
			for(Object arr : jsonArr) {
				JSONObject obj = (JSONObject) arr; // JSONArray 데이터를 하나씩 가져와 JSONObject로 변환해준다.
				insertDB(obj);
			}
		} else {
			insertDB((JSONObject) json.get("row"));
		}
		
	}
	
	private void insertDB(JSONObject obj) {
	    //PK
	    System.out.println(obj.get("opnSfTeamCode"));
	    System.out.println(obj.get("mgtNo"));
	    System.out.println(obj.get("opnSvcId"));
	    //다르면 insert, 같으면 update
	    
	    //스케줄러 , OPEN API는 이번달 1일부터 오늘일자 기준 D-2일까지의 변동분 데이터를 제공
	    //API를 통해 이번달 변동분 데이터를 가져오실 경우, 요청변수 lastModTsBgn와 lastModTsEnd에 호출하실 시작일자와 종료일자를 입력
	    //예) 5월 1일부터 오늘(5월 13일)까지의 변동분 데이터를 호출할 경우, lastModTsBgn : 20220501 / lastModTsEnd : 20220513 입력 후 호출
	    //https://www.localdata.go.kr/devcenter/apiGuide.do?menuNo=20002
	}
}
