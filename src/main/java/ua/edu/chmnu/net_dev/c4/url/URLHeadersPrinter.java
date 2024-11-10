package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

//10
public class URLHeadersPrinter {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/1st_millennium");
            URLConnection conn = url.openConnection();
            System.out.println("Server: " + conn.getHeaderField("Server"));
            System.out.println("Content Type: " + conn.getContentType());
            System.out.println("Content Length: " + conn.getContentLength());
            System.out.println("Date: " + dateFormat.format(conn.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
