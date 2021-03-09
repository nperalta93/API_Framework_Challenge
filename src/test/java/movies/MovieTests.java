package movies;

import apiUsage.RestfulTheMovieDbApi;
import entities.*;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class MovieTests {

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
    public void getMovieDetails(){
        Random rand_id = new Random();
        int id = rand_id.nextInt(700000-2)+2;
        System.out.println(id);
        Response response = api.getMovieDetails(id);
        Assert.assertEquals("The movie does not exists on the database", 200, response.getStatusCode());
    }

    @Test
    public void rateMovie(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        api.ValidateLogin(auth);
        Response sessionResponse = api.createSessionId(auth);
        String sessionId = sessionResponse.then().extract().path("session_id");
        Random rand = new Random();
        Value value = new Value(String.valueOf(rand.nextInt(10+1)-1));
        int id = rand.nextInt(700000-2)+2;
        Response response = api.rateMovie(value, id, sessionId);
        Assert.assertEquals("The result you requested could not be found", 201, response.getStatusCode());
    }
}
