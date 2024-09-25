package com.nseit.shareholder1.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.duoweb.DuoWeb;
import com.nseit.shareholder1.model.DuoModel;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DuoService {
	
	@Autowired
	ResponseUtil response;
	
	@Value(value = "${ikey}")
	private String ikey;

	@Value(value = "${skey}")
	private String skey;

	@Value(value = "${akey}")
	private String akey;

	@Value(value = "${host}")
	private String host;

	@Autowired
	DuoWeb duoWeb;

	public ResponseEntity<?> serveDuoFrame(String username,String version) {
		try {

			String request = duoWeb.signRequest(ikey, skey, akey, username);
			if (request.startsWith("ERR|")) {

				return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
			}
			Map<String, String> response1 = new HashMap<String, String>();
			String frameHtml = getFramePage(host, request);

			response1.put("host", host);
			response1.put("request", request);
//             return ResponseEntity.ok(frameHtml);
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, response1, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
		
	}

//	public ResponseEntity<?> duoResponse(DuoModel duoModel,String version) {
//		String duoResponse = duoModel.getSigResponse();
//
//		try {
//			if (duoResponse == null) {
//
//				return response.getAuthResponse("NO_DATA", HttpStatus.FORBIDDEN, null, version);
//
//			} else {
//				String username = DuoWeb.verifyResponse(ikey, skey, akey, duoResponse);
//
//				// success responseEntity
//				return response.getAuthResponse("SUCCESS", HttpStatus.OK, username, version);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//			// error responseEntity
//		}
//	}

	private static String getFramePage(final String host, final String request) {
		// In a real implementation this would be accomplished via a JSP or a templating
		// engine
		String eol = System.getProperty("line.separator");
		String framePage = "<!DOCTYPE html>" + eol + "<html>" + eol + "  <head>" + eol
				+ "    <title>Duo Authentication Prompt</title>" + eol
				+ "    <meta name='viewport' content='width=device-width, initial-scale=1'>" + eol
				+ "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>" + eol
				+ "    <link rel='stylesheet' type='text/css' href='/Duo-Frame.css'>" + eol + "  </head>" + eol
				+ "  <body>" + eol + "    <h1>Duo Authentication Prompt</h1>" + eol
				+ "    <script src='/Duo-Web-v2.js'></script>" + eol + "    <iframe id='duo_iframe'" + eol
				+ "            title='Two-Factor Authentication'" + eol + "            frameborder='0'" + eol
				+ "            data-host='" + host + "'" + eol + "            data-sig-request='" + request + "'" + eol
				+ "    </iframe>" + eol + "  </body>" + eol + "</html>";

		return framePage;
	}

}
