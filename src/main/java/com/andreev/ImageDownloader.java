package com.andreev;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Objects;

public class ImageDownloader {
    public void connectToUrl() {
        Document document = null;
        try {
            iniParser = new IniParser();
            iniParser.parseIni(Objects.requireNonNull(getClass().getClassLoader().getResource("settings.ini")).getFile());

            document = Jsoup
                    .connect(iniParser.getDownloadUrl())
                    .userAgent("Mozilla/5.0")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert document != null;
        Elements imageElements = document.select("img");

        for (Element imageElement : imageElements) {
            new Thread(() -> {
                String strImageURL = imageElement.attr("abs:src");
                downloadImage(strImageURL);
            }).start();
        }
    }

    private static void downloadImage(String strImageURL) {

        String strImageName =
                strImageURL.substring(strImageURL.lastIndexOf("/") + 1);


        System.out.println("Saving from: " + strImageURL);

        try {
            Connection.Response resultImageResponse = Jsoup
                    .connect(strImageURL)
                    .ignoreContentType(true)
                    .execute();

            var outputStreamName = iniParser.getDownloadDir() + File.separator + strImageName;
            if (resultImageResponse.bodyAsBytes().length >= iniParser.getMinFileSizeInBytes())
                writeToFile(resultImageResponse, outputStreamName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeToFile(Connection.Response resultImageResponse, String outputStreamName) {
        try (OutputStream os = new FileOutputStream(outputStreamName)) {
            os.write(resultImageResponse.bodyAsBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static IniParser iniParser;
}
