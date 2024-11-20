package ua.edu.chmnu.net_dev.c4.url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloadExample {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar downloader.jar <fileURL> <savePath>");
            return;
        }

        String fileURL = args[0];
        String savePath = args[1];

        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                long contentLength = httpConn.getContentLengthLong();

                if (disposition != null && disposition.contains("filename=")) {
                    int index = disposition.indexOf("filename=");
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                } else {
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
                }

                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = savePath + File.separator + fileName;

                File outputDir = new File(savePath);
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }

                try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
                    long totalBytesRead = 0;
                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;

                        if (contentLength > 0) {
                            int progressPercentage = (int) ((totalBytesRead * 100) / contentLength);
                            System.out.print("\rDownload progress: " + progressPercentage + "%");
                        }
                    }

                    System.out.println("\nDownload complete: " + saveFilePath);
                }
                inputStream.close();
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
