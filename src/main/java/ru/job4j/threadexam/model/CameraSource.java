package ru.job4j.threadexam.model;

import java.util.Objects;

public class CameraSource {

    private final int id;
    private final String sourceDataUrl;
    private final String tokenDataUrl;

    public CameraSource(int id, String sourceDataUrl, String tokenDataUrl) {
        this.id = id;
        this.sourceDataUrl = sourceDataUrl;
        this.tokenDataUrl = tokenDataUrl;
    }

    public int getId() {
        return id;
    }

    public String getSourceDataUrl() {
        return sourceDataUrl;
    }

    public String getTokenDataUrl() {
        return tokenDataUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CameraSource that = (CameraSource) o;
        return id == that.id
                && Objects.equals(sourceDataUrl, that.sourceDataUrl)
                && Objects.equals(tokenDataUrl, that.tokenDataUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceDataUrl, tokenDataUrl);
    }

    @Override
    public String toString() {
        return "CameraSource{"
                + "id=" + id
                + ", sourceDataUrl='" + sourceDataUrl + '\''
                + ", tokenDataUrl='" + tokenDataUrl + '\''
                + '}';
    }
}
