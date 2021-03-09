package lists;

import apiUsage.RestfulTheMovieDbApi;
import org.junit.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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


}
