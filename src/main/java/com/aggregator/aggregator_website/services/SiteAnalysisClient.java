package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.dto.siteanalysis.SiteAnalysisDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Component
public class SiteAnalysisClient {

    private RestTemplate restTemplate = new RestTemplate();
    private String similarWebApi ="https://data.similarweb.com/api/v1";

    public SiteAnalysisDto getSiteAnalysis(String domain){
        String url = similarWebApi + "/data?domain="+ domain;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.55");
        HttpEntity<?> httpEntity = new HttpEntity<>("parameters", headers);
        ResponseEntity<SiteAnalysisDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,SiteAnalysisDto.class);
        return responseEntity.getBody();
    }
}
