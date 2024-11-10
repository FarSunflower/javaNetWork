package ua.edu.chmnu.net_dev.c4.url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//9
public class URLCheckReach {
    public static void main(String[] args) {
        try {
            //reachable
            URL url = new URL("https://www.google.com");
            //not reachable
            //URL url = new URL("htt://www.google.com");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("HEAD");
            int responseCode = httpConn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            System.out.println("URL is reachable");
        } catch (Exception e) {
            System.out.println("URL is not reachable");
            e.printStackTrace();
        }
    }
}
