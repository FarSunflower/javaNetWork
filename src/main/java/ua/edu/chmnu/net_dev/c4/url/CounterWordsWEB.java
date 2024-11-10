package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//7
public class CounterWordsWEB {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://en.wikipedia.org/wiki/1st_millennium");
            URLConnection conn = url.openConnection();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }

            reader.close();
            in.close();

            // Convert content to a string and split it into words
            String text = content.toString();
            String[] words = text.split("\\s+");  // Split by whitespace to get words
            int wordCount = words.length;

            System.out.println("Word Count: " + wordCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
