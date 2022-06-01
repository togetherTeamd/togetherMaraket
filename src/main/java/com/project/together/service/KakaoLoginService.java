package com.project.together.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoLoginService {

    public String getAccessToken(String code) throws Exception {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try{
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(reqURL+"?grant_type=authorization_code&client_id=55951b24b88d60ec5899427d4125f8d5" +
                            "&redirect_uri=http://54.180.70.116:8080/login/oauthKakao&code="+code)
                    //.method("POST", body)
                    .method("GET", null)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();

            String strJson = response.body().string();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(strJson);
            JSONObject jsonObj = (JSONObject) obj;

            accessToken = (String)jsonObj.get("access_token");

//            log.info("accessToken : " + accessToken);
        } catch(Exception e){
            log.error("getAccessToken 에러");
            e.printStackTrace();
        }


        return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken){ // return type을 hashmap 으로 해야됨..

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        HashMap<String, Object> userInfo = new HashMap<>();

        try{
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(reqURL)
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer "+ accessToken)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();

            String strJson = response.body().string();
            log.info("getUserInfo : " + strJson);

//            JSONParser jsonParser = new JSONParser();
//            Object obj = jsonParser.parse(strJson);
//            JSONObject jsonObj = (JSONObject) obj;
//
//            long id = (long) jsonObj.get("id");
//            String strId = Long.toString(id);
//            log.info(strId);

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(strJson);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

//            System.out.println(nickname);
//            System.out.println(email);

            userInfo.put("accessToken", accessToken);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        }catch(Exception e){
            log.error("getUserInfo 에러");
            e.printStackTrace();
        }

        return userInfo;

    }

}
