package ua.edu.chmnu.net_dev.c4.url;
//1
import java.net.URL;
public class URLExample {
    public static void main(String[] args) {
        try {
            URL url = new
                    URL("https://www.example.com:80/index.html?user=test#section");
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Fragment: " + url.getRef());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
