package com.sample.wishlistDemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.Optional;
import javax.annotation.ManagedBean;

@ManagedBean
@PropertySource("classpath:application.properties")

public class OAuthService {
	
	@Value("${GRANT_TYPE}")
	private String grantType;
	
	@Value("${TOKENURL}")
	private String tokenUrl;
	
	@Value("${TENANT}")
    private String tenant; 
	
	@Value("${TYPE}")
    private String type;

	@Value("${CLIENT_NAME}")
    private String clientName;
	
	@Value("${CLIENT_ID}")
	private String clientId;
	
	@Value("${CLIENT_SECRET}")
	private String clientSecret;
	
	@Value("${SCOPE}")	
	private String scopes;
	
	private String contentType = "application/x-www-form-urlencoded";
	
	public OAuthService(){
		
	}
	
    public Optional<Map<String,String>> getAccessToken(){   	
		HttpHeaders headers = new HttpHeaders();   	
		headers.add("content-type", contentType );  	
		RestTemplate restTemplate = new RestTemplate();
		String body = "grant_type=" + grantType + "&client_id=" + clientId + "&client_secret=" + clientSecret +"&scope=" + scopes;
		HttpEntity<Object> requestHttpEntity = new HttpEntity<>( body, headers );
		Map<String,String> tokenMap = restTemplate.postForObject(tokenUrl, requestHttpEntity, Map.class );  			
		return Optional.of(tokenMap);
    }
}