package ru.job4j.threadexam.parse;

import org.junit.Test;
import ru.job4j.threadexam.model.Camera;
import ru.job4j.threadexam.model.CameraSource;
import ru.job4j.threadexam.model.VideoSource;
import ru.job4j.threadexam.model.Token;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void whenTryParseSource() {
        Parser parser = new Parser();
        VideoSource videoSource = parser
                .parseSource("http://www.mocky.io/v2/5c51b230340000094f129f5d");
        assertThat(videoSource.getUrlType(), is("LIVE"));
        assertThat(videoSource.getVideoUrl(), is("rtsp://127.0.0.1/1"));
    }

    @Test
    public void whenTryParseToken() {
        Parser parser = new Parser();
        Token token = parser
                .parseToken("http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s");
        assertThat(token.getValue(), is("fa4b588e-249b-11e9-ab14-d663bd873d93"));
        assertThat(token.getTtl(), is(120));
    }

    @Test
    public void whenTryParseCameraSource() {
        Parser parser = new Parser();
        CameraSource source = parser
                .parseCameraSources("http://www.mocky.io/v2/5c51b9dd3400003252129fb5").get(0);
        assertThat(source, is(new CameraSource(1,
                "http://www.mocky.io/v2/5c51b230340000094f129f5d",
                "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s")));
    }

    @Test
    public void whenTryParseCamera() {
        Parser parser = new Parser();
        CameraSource source = parser
                .parseCameraSources("http://www.mocky.io/v2/5c51b9dd3400003252129fb5").get(0);
        Camera camera = parser.convertRootToCamera(source);
        assertThat(camera, is(new Camera(1,
                "LIVE",
                "rtsp://127.0.0.1/1",
                "fa4b588e-249b-11e9-ab14-d663bd873d93",
                120)));
    }
}