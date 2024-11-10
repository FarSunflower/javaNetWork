package ua.edu.chmnu.net_dev.c4.url;
import java.io.*;
import java.net.*;
//11
public class RedirectDetection {
    public static void main(String[] args) {
        String initialUrl = "https://moodle3.chmnu.edu.ua/course/view.php?id=36582";
        try {
            URL url = new URL(initialUrl);
            HttpURLConnection httpConn = (HttpURLConnection)
                    url.openConnection();
            httpConn.setInstanceFollowRedirects(false); // Disable auto-follow
            int responseCode = httpConn.getResponseCode();
            while (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode
                    == HttpURLConnection.HTTP_SEE_OTHER) {
                String redirectUrl =
                        httpConn.getHeaderField("Location");
                System.out.println("Redirected to: " + redirectUrl);
                url = new URL(redirectUrl);
                httpConn = (HttpURLConnection) url.openConnection();
                responseCode = httpConn.getResponseCode();
            }
        } catch (Exception e) {
            System.out.println("No redirection");
            e.printStackTrace();
        }
    }

}
