package helpers;

import entities.MovieList;

import java.util.Random;

public class ListGenerator {
    //Based on the code from Santiago Cabrales
    //https://github.com/SantiagoCabrales29/API-Testing-Workshop-2021/blob/c95d8d5ee34069989dd98edbb5bce00df1f7f0cc/api-testing-workshop/src/test/java/com/endava/app/helpers/DataGenerator.java
    public static String createRandomString(){
        return String.valueOf(System.currentTimeMillis()).
                replaceAll("1", "a").
                replaceAll("2", "c").
                replaceAll("3", "f").
                replaceAll("4", "y").
                replaceAll("5", "q").
                replaceAll("6", "s").
                replaceAll("7", "o").
                replaceAll("8", "i").
                replaceAll("9", "l");
    }
    public static MovieList createRandomList(){
        String name = createRandomString();
        String description = createRandomString();
        String language;
        Random rand = new Random();
        if(rand.nextInt(2) == 0){
            language = "en";
        }
        else{ language = "es"; }
        return new MovieList(name, description, language);
    }
}
