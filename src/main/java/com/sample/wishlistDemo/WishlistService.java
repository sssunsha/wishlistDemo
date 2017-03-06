package com.sample.wishlistDemo;

import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.Optional;
import javax.annotation.ManagedBean;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import com.sample.wishlistDemo.OAuthService;
import com.sample.wishlistDemo.api.generated.Wishlist;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;


@ManagedBean
@PropertySource("classpath:application.properties")
public class WishlistService{
	
	@Value("${GRANT_TYPE}")
	private String grantType;
	
	@Value("${BASEURL}")
	private String baseUrl;
	
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
	
	@Autowired
    private OAuthService oAuthService;
	
	private final String LINKKEY = "link";
	
	private final String ACCESSTOKENKEY = "access_token";
	
	
	public WishlistService() {
    }
	
	// handle for getting all wishlists of owner
	 public Wishlist[] getWishlists(final YaasAwareParameters yaasAware) {
        String accessToken = getAccessToken();
        System.out.println("accessToken = " + accessToken);
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(prepareHeader(accessToken));
        String url = prepareBaseUrl(yaasAware);
        Wishlist[] wishlists = new RestTemplate().exchange(url, HttpMethod.GET, requestHttpEntity, Wishlist[].class).getBody();
        return wishlists;
	 }
	 
	 // handle for post wishlists
	 public String postWishList(final YaasAwareParameters yaasAware, final Wishlist wishlist) {
		 
		String accessToken = getAccessToken();
		System.out.println("accessToken = " + accessToken);
		HttpEntity<String> requestHttpEntity = new HttpEntity<>(convertToJson(wishlist, yaasAware), prepareHeader(accessToken));
		String url = prepareBaseUrl(yaasAware);
		Map<String, String> resultMap = new RestTemplate().postForObject(url, requestHttpEntity, Map.class);
		if (resultMap != null && resultMap.containsKey(LINKKEY))
		{
			return resultMap.get(LINKKEY);
		}
		else
		{
			return null;
		}
	 }
	 
	 // get the accessToken
	private String getAccessToken() {
	    Map<String, String> tokenMap = oAuthService.getAccessToken().orElse(null);
	    return tokenMap.get(ACCESSTOKENKEY);
	}
	
	// convert the wishlist object to json string
	private String convertToJson(final Wishlist wishlist, final YaasAwareParameters yaasAware){
		JSONObject wishlistObj = new JSONObject();
		        
        wishlistObj.put("id", wishlist.getId());
        wishlistObj.put("url", wishlist.getUrl());
        wishlistObj.put("owner", yaasAware.getHybrisUser());
        wishlistObj.put("title", wishlist.getTitle());
        wishlistObj.put("description", wishlist.getDescription());
        wishlistObj.put("createdAt", wishlist.getCreatedAt());
        wishlistObj.put("items", wishlist.getItems());
        
        System.out.print(wishlistObj);
        return wishlistObj.toString();
	}
	
	// prepare the headers for POST and GET
    private HttpHeaders prepareHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
    
    // prepare the base url for POST and GET
    private String prepareBaseUrl(final YaasAwareParameters yaasAware) {
        return baseUrl + "/" + tenant + "/" + clientName + "/data/" + yaasAware.getHybrisUser();
    }
}