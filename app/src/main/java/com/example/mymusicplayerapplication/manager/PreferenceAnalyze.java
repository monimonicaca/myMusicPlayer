package com.example.mymusicplayerapplication.manager;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PreferenceAnalyze {
  private static final String URL="https://spark-api-open.xf-yun.com/v1/chat/completions";
  private static final String TOKEN="Bearer tjniVKDZyMXKHdMdSdFI:UqaBFIEUjzcpRzAzGWXz";
  public static String getAnalyzeResult(List<String> songNames){
      try {
          URL obj = new URL(URL);
          HttpURLConnection con = (HttpURLConnection) obj.openConnection();
          con.setRequestMethod("POST");
          con.setRequestProperty("Authorization", TOKEN);
          con.setRequestProperty("Content-Type", "application/json");
          con.setDoOutput(true);
          con.setChunkedStreamingMode(0);
          String jsonData = "{\n" +
                  "  \"max_tokens\": 4096,\n" +
                  "  \"top_k\": 4,\n" +
                  "  \"temperature\": 0.5,\n" +
                  "  \"messages\": [\n" +
                  "    {\n" +
                  "      \"role\": \"system\",\n" +
                  "      \"content\": \"你是一位擅长分析的人,根据一份歌名集合格式为[]，分析这个人爱听什么类型的歌曲并且给出一份分析报告，并以json格式返回。格式为{\\\"data\\\": {\\\"songGenrePreference\\\": [],\\\"result\\\": \\\"\\\"}}，注意result字段的内容中所有的人称代词都为你，这里的内容最好丰富一点，不用把提到的歌曲都列出来，只举例一两个就行。不要加上“从你提供的歌单来看”这种话\"\n" +
                  "    },\n" +
                  "    {\n" +
                  "      \"role\": \"user\",\n" +
                  "      \"content\": \"" + songNames + "\"\n" +
                  "    }\n" +
                  "  ],\n" +
                  "  \"model\": \"4.0Ultra\"\n" +
                  "}";
          //Log.d("data", JSON.toJSON(jsonData).toString());
          try (OutputStream os = con.getOutputStream()) {
              byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
              os.write(input, 0, input.length);
          }
          int responseCode = con.getResponseCode();
          //System.out.println("Response Code: " + responseCode);
          if (responseCode != HttpURLConnection.HTTP_OK) {
              BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
              StringBuilder errorResponse = new StringBuilder();
              String errorLine;
              while ((errorLine = errorReader.readLine()) != null) {
                  errorResponse.append(errorLine);
              }
              Log.d("errorResponse", errorResponse.toString());
              return errorResponse.toString();
          }
          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
          String inputLine;
          StringBuilder response = new StringBuilder();
          while ((inputLine = in.readLine()) != null) {
              response.append(inputLine);
          }
          in.close();
          //Log.d("response", response.toString());
          return response.toString();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
}
