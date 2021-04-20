package ru.job4j.threadexam.parse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.job4j.threadexam.model.Camera;
import ru.job4j.threadexam.model.CameraSource;
import ru.job4j.threadexam.model.VideoSource;
import ru.job4j.threadexam.model.Token;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public VideoSource parseSource(String link) {
        String content = getContent(link);
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        return new VideoSource(obj.get("urlType").toString(), obj.get("videoUrl").toString());
    }

    public Token parseToken(String link) {
        String content = getContent(link);
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        return new Token(obj.get("value").toString(), Integer.parseInt(obj.get("ttl").toString()));
    }

    public String getContent(String link) {
        String content = "";
        try (BufferedReader input =
                     new BufferedReader(
                             new InputStreamReader(
                                     new URL(link).openStream()
                             ))) {
            content = input.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }

    public CameraSource convertJsonToCameraSource(Object json) {
        JSONObject obj = (JSONObject) json;
        return new CameraSource(
                Integer.parseInt(obj.get("id").toString()),
                obj.get("sourceDataUrl").toString(),
                obj.get("tokenDataUrl").toString()
        );
    }

    public List<CameraSource> parseCameraSources(String link) {
        List<CameraSource> result = new ArrayList<>();
        String content = getContent(link);
        content = "{\"arr\":" + content + "}";
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        JSONArray array =  (JSONArray) obj.get("arr");
        array.forEach(json -> result.add(convertJsonToCameraSource(json)));
        return result;
    }

    public Camera convertRootToCamera(CameraSource cameraSource) {
        Token token = parseToken(cameraSource.getTokenDataUrl());
        VideoSource videoSource = parseSource(cameraSource.getSourceDataUrl());
        return new Camera(
                cameraSource.getId(),
                videoSource.getUrlType(),
                videoSource.getVideoUrl(),
                token.getValue(),
                token.getTtl()
        );
    }
}
