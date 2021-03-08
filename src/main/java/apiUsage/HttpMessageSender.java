package apiUsage;

import entities.Auth;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpMessageSender {
    private String url;

    public HttpMessageSender(String url){
        this.url = url;
    }

    public Response getRequestToEndpoint(String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        when().
                        get(requestURL).
                        andReturn();
    }

    public Response authLogin(Auth credentials, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(credentials).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint (Auth credentials, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(credentials).
                        when().log().body().
                        post(requestURL).
                        andReturn();
    }
}
