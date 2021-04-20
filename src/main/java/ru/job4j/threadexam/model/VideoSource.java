package ru.job4j.threadexam.model;

public class VideoSource {

    private final String urlType;
    private final String videoUrl;

    public VideoSource(String urlType, String videoUrl) {
        this.urlType = urlType;
        this.videoUrl = videoUrl;
    }

    public String getUrlType() {
        return urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public String toString() {
        return "VideoSource{"
                + "urlType='" + urlType + '\''
                + ", videoUrl='" + videoUrl + '\''
                + '}';
    }
}
