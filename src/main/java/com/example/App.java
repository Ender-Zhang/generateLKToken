/*
 * @Author: Ender-Zhang 102596313+Ender-Zhang@users.noreply.github.com
 * @Date: 2023-02-19 16:37:04
 * @LastEditors: Ender-Zhang 102596313+Ender-Zhang@users.noreply.github.com
 * @LastEditTime: 2023-03-28 11:43:51
 * @FilePath: \demo\src\main\java\com\example\App.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import io.livekit.server.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.*;
/**
 * Hello world!
 *
 */
public class App 
{

    public static String createToken(String apiKey, String secretKey){
    //   AccessToken token = new AccessToken("API3wGw4ma49quv", "yeqkJPY8M4x7IUduKXYXpGRAy2zB66YmzDa87ikR7dE");
	AccessToken token = new AccessToken(apiKey, secretKey);
	token.setName("zyc123");
    //   token.setIdentity("admin");
	  token.setIdentity("user");
      token.setMetadata("metadata");
      token.addGrants(new RoomJoin(true), new Room("test"), new RoomList(true), new RoomAdmin(true), new RoomCreate(true));
	// token.addGrants(new RoomJoin(true), new Room("test"), new RoomAdmin(true));
	System.out.println(token.toJwt());
      return token.toJwt();
    }

	public static String getMsg2(String apiKey, String secretKey) throws IOException{
		OkHttpClient client = new OkHttpClient().newBuilder()
		.build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{ \"names\": [\"test\"] }");
		// RequestBody body = RequestBody.create(mediaType, "{ \"names\":[] }");
		// RequestBody body = RequestBody.create(mediaType, "{ \"room\": [\"test\"] }");
		String token = "Bearer " + createToken(apiKey, secretKey);
		Request request = new Request.Builder()
		// .url("http://localhost:7880/twirp/livekit.RoomService/ListRooms")
		.url("wss://testyuz.livekit.cloud/twirp/livekit.RoomService/ListRooms")
		.method("POST", body)
		// .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUElya0J6QWtEWUtaNGQiLCJleHAiOjE2NzY4NjQ2MjcsInN1YiI6ImFkbWluIiwianRpIjoiYWRtaW4iLCJuYW1lIjoienljIiwibWV0YWRhdGEiOiJtZXRhZGF0YSIsInZpZGVvIjp7InJvb21Kb2luIjp0cnVlLCJyb29tIjoidGVzdCIsInJvb21MaXN0Ijp0cnVlLCJyb29tQWRtaW4iOnRydWUsInJvb21DcmVhdGUiOnRydWV9fQ.nAC_R50TptX5u0X7Yg3_aCEQ67osnCU7WWUa7YqRjWo")
		.addHeader("Authorization", token)
		.addHeader("Content-Type", "application/json")
		.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public static void main( String[] args ) throws IOException
    {
        // System.out.println( "Hello World!");
		// getMsg2();
		// JSONObject jo = JSON.parseObject(getMsg2());
		// String res = jo.get("rooms").toString();
		// System.out.println(res);
		String apikey = "";
		String secretkey = "";
		System.out.println(getMsg2(apikey, secretkey));
    }
}
