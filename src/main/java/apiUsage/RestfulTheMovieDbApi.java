package apiUsage;

import io.restassured.response.Response;
import entities.Auth;

public class RestfulTheMovieDbApi {
    private final String url;
    private final String apiKey;
    private final HttpMessageSender messageSender;


    public RestfulTheMovieDbApi (String url, String apiKey){
        Response response;
        this.url = url;
        this.apiKey = apiKey;
        this.messageSender = new HttpMessageSender(url);
    }

    public String getToken(){
        Response response = messageSender.getRequestToEndpoint("/authentication/token/new?api_key=" + this.apiKey);
        String token = response.then().extract().path("request_token");
        return token;
    }

    public Response ValidateLogin (Auth credentials){
        Response response = messageSender.authLogin(credentials, "/authentication/token/validate_with_login?api_key=" + this.apiKey);
        return response;
    }

    public Response createSessionId (Auth credentials){
        Response response = messageSender.postRequestToEndpoint(credentials, "/authentication/session/new?api_key=" + this.apiKey);
        return response;

    }

}
