package com.golfhandicapcalculator.enterprise;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ExternalFeedService {

    private final RestTemplate restTemplate;

    @Value("${external.feed.url}")
    private String externalFeedUrl;

    public ExternalFeedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExternalFeed() {
        ResponseEntity<String> response = restTemplate.getForEntity(externalFeedUrl, String.class);
        return response.getBody();
    }
}
