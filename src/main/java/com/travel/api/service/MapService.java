package com.travel.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MapService {
    private static final String API_KEY = "7de6ef4a7dc4548b939d0a1dd3e9cda8"; // 여기에 발급받은 카카오 API 키를 입력하세요

    public double[] getLatLngFromAddress(String address) throws Exception {
        String urlString = "https://dapi.kakao.com/v2/local/search/address.json?query=" +
                            java.net.URLEncoder.encode(address, StandardCharsets.UTF_8);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "KakaoAK " + API_KEY);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray documents = jsonObject.getJSONArray("documents");
        if (documents.length() > 0) {
            JSONObject location = documents.getJSONObject(0);
            double lat = location.getJSONObject("address").getDouble("y");
            double lng = location.getJSONObject("address").getDouble("x");
            return new double[]{lat, lng};
        }
        return null;
    }
}
