package entities;

public class MovieList {
    private String name;
    private String description;
    private String language;
    private String list_id;

    public MovieList(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }
}
