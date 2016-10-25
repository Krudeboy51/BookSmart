package mrkking.book;

/**
 * Created by Kory on 10/17/2016.
 */
public class AppConfig {

    public static String URL_BASE = "http://mrkking.com/badman/php/";

    // Server user login url
    public static String URL_LOGIN = URL_BASE+"login.php";

    // Server user register url
    public static String URL_REGISTER = URL_BASE+"Register.php";

    //Server Password Change
    public static String URL_CHANGEPASSWORD = URL_BASE+"changePass.php";

    //Server Add New Post
    public static String URL_ADD_POST = URL_BASE+"createpost.php";

    //Get Post List From Server
    public static String URL_GET_ALL_POST="http://mrkking.com/badman/php/postlist.php";


}
