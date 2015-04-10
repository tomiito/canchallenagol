package ar.com.ironsoft.marroccl.web.app.modules.game.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

/**
 * @author Tomas de Priede
 */
public class FindVideoUrlsTest {

    public static final String FIRST_VIDEO_URL = "https://s3.amazonaws.com/historico.lanacion.com.ar/Partidos/TYC.20150331_{0}.mp4";

    @Test
    public void testFindVideoUrls() throws Exception {
        List<String> urls = new ArrayList<String>();
        int totalVideos = 2;
        int hour = 21;
        int minute = 1;
        //
        for (int i = 0; i < totalVideos; i++) {
            //
            for (int j = 0; j < 99; j++) {
                String url = findVideo(hour, minute, j);
                if (url != null) {
                    urls.add(url);
                    break;
                }
            }
            minute++;
            if (minute == 60) {
                minute = 0;
                hour++;
            }
        }
        //
        System.out.println("************************");
        System.out.println("************************");
        System.out.println("************************");
        //
        for (String url : urls) {
            System.out.println(url);
        }
    }

    public String findVideo(int hour, int minute, int seconds)
            throws IOException {
        HttpClient client = new DefaultHttpClient();
        String url = FIRST_VIDEO_URL.replace("{0}",
                String.format("%02d%02d%02d", hour, minute, seconds));
        System.out.println("C:" + url);
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        //
        boolean video = 200 == response.getStatusLine().getStatusCode();
        if (video) {
            System.out.println("---> " + url);
            return url;
        }
        return null;
    }
}
