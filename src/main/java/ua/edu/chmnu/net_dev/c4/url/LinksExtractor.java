package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//8
public class LinksExtractor {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://moodle3.chmnu.edu.ua/login/index.php#section-5");
            URLConnection conn = url.openConnection();

            // Read the HTML content of the webpage
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // Regular expression to find href attributes within <a> tags
            Pattern pattern = Pattern.compile("<a\\s+href=[\"'](.*?)[\"']", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(content.toString());

            while (matcher.find()) {
                String link = matcher.group(1);
                System.out.println(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
