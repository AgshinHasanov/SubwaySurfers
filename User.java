public class User {
    private String username;
    private String password;
    
    public User(String username, String password) {
        try {
            setUsername(username);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Invalid input detected in Username. Please provide valid credentials.");
        }
        try {
            setPassword(password);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Invalid input detected in Password. Please provide valid credentials.");
        }
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if(username == null) throw new NullPointerException();
        if(username.isBlank()) throw new IllegalArgumentException();
        for(int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z') || (c >= '0' && c <= '9')) continue;
            else{
                throw new IllegalArgumentException();
            } 
        }
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if(password == null) throw new NullPointerException();
        if(password.isBlank()) throw new IllegalArgumentException();
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z') || (c >= '0' && c <= '9')) continue;
            else{
                throw new IllegalArgumentException();
            } 
        }
        this.password = password;
    }
    @Override
    public String toString() {
        return "Username = " + username + ", Password = " + password;
    }    
}


