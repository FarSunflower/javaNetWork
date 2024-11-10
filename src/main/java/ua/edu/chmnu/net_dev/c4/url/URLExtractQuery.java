package ua.edu.chmnu.net_dev.c4.url;

import java.net.URL;
//6
public class URLExtractQuery {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://example.com/search?query=java&lang=en");
            String query = url.getQuery();

            if (query != null) {
                String[] parameters = query.split("&");
                for (String parameter : parameters) {
                    System.out.println(parameter.replace("=", ": "));
                }
            } else {
                System.out.println("No query parameters found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
