package ua.edu.chmnu.net_dev.c4.url;
//2
import java.net.URL;

public class URLValidate {
    public static void main(String[] args) {
        try {
            //valid
            URL url1 = new
                    URL("https://www.example.com:80/index.html?user=test#section");
            //invalid
            //URL url2 = new
            //        URL("hs://www.example.com:80/index.html?user=test#section");
            System.out.println("URL is valid");
        } catch (Exception e) {
            System.out.println("Invalid URL");
            e.printStackTrace();
        }
    }
}
