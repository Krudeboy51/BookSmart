package DataModels;

/**
 * Created by kingke on 10/21/16.
 */

public class UserPost {

    private String title;


    private String postDate;
    private String user;
    private String bookState;

    public UserPost(String title, String postDate, String user, String state) {
        this.title = title;
        this.postDate = postDate;
        this.user = user;
        this.bookState = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBookState() {
        return bookState;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }

}
