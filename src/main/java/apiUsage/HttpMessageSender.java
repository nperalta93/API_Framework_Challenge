package apiUsage;

import entities.*;
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

    public Response postRequestToEndpoint(String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response deleteRequestToEndpoint(String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        when().
                        delete(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint (Auth credentials, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(credentials).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint(ValueRated value, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(value).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint(MovieList list, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(list).
                        when().
                        post(requestURL).
                        andReturn();
    }

    public Response postRequestToEndpoint(Movie movie, String endpoint){
        String requestURL = url + endpoint;
        return
                given().
                        contentType(ContentType.JSON).
                        body(movie).
                        when().
                        post(requestURL).
                        andReturn();
    }
}