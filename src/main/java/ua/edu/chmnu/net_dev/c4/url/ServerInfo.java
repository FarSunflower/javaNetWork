package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
import java.net.URLConnection;
//13
public class ServerInfo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/1st_millennium");
            URLConnection conn = url.openConnection();

            // Retrieve the "Server" response header
            String server = conn.getHeaderField("Server");

            if (server != null) {
                System.out.println("Server: " + server);
            } else {
                System.out.println("Server header is not available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
