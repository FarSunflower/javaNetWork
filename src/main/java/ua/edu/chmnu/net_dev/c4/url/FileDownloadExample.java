package ua.edu.chmnu.net_dev.c4.url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
//4
public class FileDownloadExample {
    public static void main(String[] args) {
        String fileURL = "https://ash-speed.hetzner.com/100MB.bin";
        String savePath = "D:\\java\\java-net-dev-4c";

        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");  // Set the request method to GET for downloading

            int responseCode = httpConn.getResponseCode();

            // Check if the server responds with HTTP 200 (OK)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                long contentLength = httpConn.getContentLengthLong();  // Use getContentLengthLong for large files

                if (disposition != null && disposition.contains("filename=")) {
                    int index = disposition.indexOf("filename=");
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                } else {
                    // Get the file name from the URL if the header is not available
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
                }

                // Create an input stream to read the file from the server
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = savePath + File.separator + fileName;

                // Open an output stream to save the downloaded file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                long totalBytesRead = 0;
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    // Calculate the progress as a percentage
                    if (contentLength > 0) {  // Only calculate progress if content length is known
                        int progressPercentage = (int) ((totalBytesRead * 100) / contentLength);
                        System.out.print("\rDownload progress: " + progressPercentage + "%");
                    }
                }

                outputStream.close();
                inputStream.close();

                System.out.println("\nDownload complete: " + saveFilePath);
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}