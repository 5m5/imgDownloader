package com.andreev;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class IniParser {

    public void parseIni(String filePath) throws IOException {
        var ini = new Ini(new File(filePath));
        minFileSizeInBytes = Long.parseLong(ini.get("root", "minKb")) * 1024;
        downloadUrl = ini.get("root", "downloadURL");
        downloadDir = ini.get("root", "downloadDir");
    }

    public long getMinFileSizeInBytes() {
        return minFileSizeInBytes;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDownloadDir() {
        return downloadDir;
    }

    public void setMinFileSizeInBytes(long minFileSizeInBytes) {
        this.minFileSizeInBytes = minFileSizeInBytes;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }

    private long minFileSizeInBytes;
    private String downloadUrl;
    private String downloadDir;
}
