package apiUsage;

import entities.*;
import io.restassured.response.Response;
import entities.Auth;

import java.util.List;

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
        return response;
    }

    public Response rateMovie(ValueRated value, int id, String sessionId){
        Response response = messageSender.postRequestToEndpoint(value, "/movie/" + id + "/rating?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }

    public Response createList(MovieList list, String sessionId){
        Response response = messageSender.postRequestToEndpoint (list, "/list?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }

    public List<Integer> getListsIds(String sessionId){
        Response accountResponse = messageSender.getRequestToEndpoint("/account/lists?api_key=" + this.apiKey + "&session_id=" + sessionId);
        String id = String.valueOf(accountResponse.then().extract().path("id"));
        Response response = messageSender.getRequestToEndpoint("/account/" + id + "/lists?api_key=" + this.apiKey + "&session_id=" + sessionId);
        List <Integer> listIds = response.then().extract().path("results.id");
        return listIds;
    }

    public Response getListDetails(int id){
        Response response = messageSender.getRequestToEndpoint("/list/" + String.valueOf(id) + "?api_key=" + this.apiKey);
        return response;
    }

    public Response addMovie(Movie movie, String sessionId, int listId){
        Response response = messageSender.postRequestToEndpoint(movie, "/list/" + listId + "/add_item?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }

    public Response clearList(String sessionId, int listId, boolean confirmation){
        Response response =messageSender.
                postRequestToEndpoint("/list/" + listId + "/clear?api_key=" + this.apiKey + "&session_id=" + sessionId + "&confirm=" + confirmation);
        return response;
    }

    public Response deleteList(String sessionId, int listId){
        Response response = messageSender.deleteRequestToEndpoint("/list/" + listId + "?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }

    public Response removeFromList(Movie movie, String sessionId, int listId){
        Response response =
                messageSender.postRequestToEndpoint(movie, "/list/" + listId + "/remove_item?api_key=" + this.apiKey + "&session_id=" + sessionId);
        return response;
    }
}
