package entities;

public class Auth {
    private String username;
    private String password;
    private String request_token;

    public Auth(String username, String password, String token){
        this.username = username;
        this.password = password;
        this.request_token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken(){ return request_token; }
}
