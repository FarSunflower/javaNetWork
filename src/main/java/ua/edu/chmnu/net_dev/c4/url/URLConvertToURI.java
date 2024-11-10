package ua.edu.chmnu.net_dev.c4.url;
import java.net.URI;
import java.net.URL;
//14
public class URLConvertToURI {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://example.com/path/to/page");
            URI uri = url.toURI();

            System.out.println("Scheme: " + uri.getScheme());
            System.out.println("Host: " + uri.getHost());
            System.out.println("Path: " + uri.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
