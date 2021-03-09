package apiUsage;

import entities.Value;
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
        Response response = messageSender.postRequestToEndpoint(credentials, "/authentication/token/validate_with_login?api_key=" + this.apiKey);
        return response;
    }

    public Response createSessionId (Auth credentials){
        Response response = messageSender.postRequestToEndpoint(credentials, "/authentication/session/new?api_key=" + this.apiKey);
        return response;
    }

    public Response getMovieDetails(int id){
        Response response = messageSender.getRequestToEndpoint("/movie/"+ id +"?api_key=" + apiKey);
        System.out.println(response.then().extract().path("original_title"));
        return response;
    }

    public Response rateMovie(Value value, int id, String sessionId){
        Response response = messageSender.postRequestToEndpoint(value, "/movie/" + id + "/rating?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }

}
