package com.saharak.sprintBootTemplate.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service("lineService")
public class LineService {

  public void sendNoti(final String message) {
    final String url = "https://notify-api.line.me/api/notify";
    final RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Bearer 4cFBQiVlW27iUhdw7jWwkSuXLLK7aPKlCbUnSGl81jJ");

    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("message", message);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    restTemplate.postForEntity(url, request, String.class);
  }
}