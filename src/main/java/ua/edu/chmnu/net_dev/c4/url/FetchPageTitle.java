package ua.edu.chmnu.net_dev.c4.url;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//12
public class FetchPageTitle {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/1st_millennium");
            URLConnection conn = url.openConnection();

            // Read the HTML content
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();

            // Read HTML line by line
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();

            // Use regex to find the title tag
            Pattern titlePattern = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = titlePattern.matcher(content.toString());

            if (matcher.find()) {
                String title = matcher.group(1);
                System.out.println("Title: " + title);
            } else {
                System.out.println("No title found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
