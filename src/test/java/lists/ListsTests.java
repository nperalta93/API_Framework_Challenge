package lists;

import apiUsage.RestfulTheMovieDbApi;
import entities.Auth;
import entities.Movie;
import entities.MovieList;
import helpers.ListGenerator;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ListsTests {
    private static Properties props;
    private static RestfulTheMovieDbApi api;

    @BeforeClass
    public static void createTestEnvironment (){
        props = new Properties();
        try {
            props.load(new FileInputStream("application.properties"));
        }catch (IOException var2){
            System.out.println("An error occurred trying to read the properties file");
        }
        api = new RestfulTheMovieDbApi(props.getProperty("url"), props.getProperty("apiKey"));
    }

    @Test
    public void createList(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        MovieList list = ListGenerator.createRandomList();
        Response response = api.createList(list, sessionId);
        String listId = String.valueOf(response.then().extract().path("list_id"));
        list.setList_id(listId);
        Assert.assertEquals("The result you requested could not be found", 201, response.getStatusCode());
    }

    @Test
    public void getListsDetails(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        List<Integer> ids = api.getListsIds(sessionId);
        Random rand = new Random();
        Response response = api.getListDetails(ids.get(rand.nextInt(ids.size())));
        Assert.assertEquals("The result you requested could not be found", 200, response.getStatusCode());
    }

    @Test
    public void addMovie(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        List<Integer> ids = api.getListsIds(sessionId);
        Random rand = new Random();
        int listId = ids.get(rand.nextInt(ids.size()));
        Movie movie = new Movie(String.valueOf(rand.nextInt(700000-2)+2));
        Response response = api.addMovie(movie, sessionId, listId);
        Assert.assertEquals("The result you requested could not be found", 201, response.getStatusCode());
    }

    @Test
    public void clearList(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        List<Integer> ids = api.getListsIds(sessionId);
        Random rand = new Random();
        int listId = ids.get(rand.nextInt(ids.size()));
        Response response = api.clearList(sessionId, listId, true);
        Assert.assertEquals("The result you requested could not be found", 201, response.getStatusCode());
    }

    @Test
    public void deleteList(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        List<Integer> ids = api.getListsIds(sessionId);
        Random rand = new Random();
        int listId = ids.get(rand.nextInt(ids.size()));
        Response response = api.deleteList(sessionId, listId);
        Assert.assertEquals("The result you requested could not be found", 201, response.getStatusCode());
    }

    @Test
    public void removeFromList(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        List<Integer> ids = api.getListsIds(sessionId);
        Random rand = new Random();
        int listId = ids.get(rand.nextInt(ids.size()));
        List <String> moviesIds= api.getListDetails(listId).then().extract().path("items.id");
        String movie_id = String.valueOf(moviesIds.get(rand.nextInt(moviesIds.size())));
        Movie movie = new Movie(movie_id);
        Response response = api.removeFromList(movie, sessionId, listId);
        Assert.assertEquals("The result you requested could not be found", 200, response.getStatusCode());
    }
}
