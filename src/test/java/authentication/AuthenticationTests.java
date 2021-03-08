package authentication;

import apiUsage.RestfulTheMovieDbApi;
import entities.Auth;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AuthenticationTests {
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
        api = new RestfulTheMovieDbApi (props.getProperty("url"), props.getProperty("apiKey"));
    }

    @Test
    public void getToken(){
        System.out.println(api.getToken());
    }

    @Test
    public void validateWithLogin(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        Response response = api.ValidateLogin(auth);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void CreateSessionId(){
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        String token = api.getToken();
        Auth auth = new Auth(username, password, token);
        Response response = api.ValidateLogin(auth);
        Response response2 = api.createSessionId(auth);
        System.out.println(response2.then().extract().path("success"));
        Assert.assertEquals(200, response2.getStatusCode());
    }

    /*@Test
    public void createSessionId(){
        Response response = api.createSessionId();
        String session_id = response.then().extract().path("session_id");
        System.out.println(session_id);
        Assert.assertEquals(200, response.getStatusCode());
    }*/



}
