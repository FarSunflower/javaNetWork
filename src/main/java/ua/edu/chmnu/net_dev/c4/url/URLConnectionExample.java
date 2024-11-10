package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
import java.net.URLConnection;
//5
public class URLConnectionExample {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://example.com");
            URLConnection conn = url.openConnection();
            System.out.println("Content Type: " + conn.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
