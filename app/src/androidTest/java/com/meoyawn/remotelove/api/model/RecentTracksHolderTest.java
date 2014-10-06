package com.meoyawn.remotelove.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meoyawn.remotelove.Dagger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adelnizamutdinov on 10/5/14
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecentTracksHolderTest {
  @Inject ObjectMapper objectMapper;

  @Before public void setUp() {
    Dagger.instance$.inject(Robolectric.application, this);
  }

  @Test public void testNowPlayingParsing() throws Exception {
    Track[] track = parse("nowplaying.json", RecentTracksHolder.class).recenttracks.track;
    assertThat(track[0].name).isNotNull();
  }

  <T> T parse(String fileName, Class<T> clazz) throws IOException {
    return objectMapper.readValue(
        "{\"recenttracks\":{\"track\":{\"artist\":{\"name\":\"Seu Jorge\"," +
            "\"mbid\":\"c73d651d-87c9-4c5e-8abb-4ec9c4077241\",\"url\":\"Seu Jorge\"," +
            "\"image\":[{\"#text\":\"http:\\/\\/userserve-ak.last.fm\\/serve\\/34\\/31930885" +
            ".jpg\",\"size\":\"small\"},{\"#text\":\"http:\\/\\/userserve-ak.last" +
            ".fm\\/serve\\/64\\/31930885.jpg\",\"size\":\"medium\"}," +
            "{\"#text\":\"http:\\/\\/userserve-ak.last.fm\\/serve\\/126\\/31930885.jpg\"," +
            "\"size\":\"large\"},{\"#text\":\"http:\\/\\/userserve-ak.last" +
            ".fm\\/serve\\/252\\/31930885.jpg\",\"size\":\"extralarge\"}]},\"loved\":\"0\"," +
            "\"name\":\"Bola De Meia\",\"streamable\":\"0\"," +
            "\"mbid\":\"e3f95c41-352e-43e5-aedb-52ef1e5bcda2\",\"album\":{\"#text\":\"Cru\"," +
            "\"mbid\":\"7b331647-49c1-3c82-9b02-27a84241aef7\"},\"url\":\"http:\\/\\/www.last" +
            ".fm\\/music\\/Seu+Jorge\\/_\\/Bola+De+Meia\"," +
            "\"image\":[{\"#text\":\"http:\\/\\/userserve-ak.last.fm\\/serve\\/34s\\/90643055" +
            ".jpg\",\"size\":\"small\"},{\"#text\":\"http:\\/\\/userserve-ak.last" +
            ".fm\\/serve\\/64s\\/90643055.jpg\",\"size\":\"medium\"}," +
            "{\"#text\":\"http:\\/\\/userserve-ak.last.fm\\/serve\\/126\\/90643055.jpg\"," +
            "\"size\":\"large\"},{\"#text\":\"http:\\/\\/userserve-ak.last" +
            ".fm\\/serve\\/300x300\\/90643055.jpg\",\"size\":\"extralarge\"}]," +
            "\"date\":{\"#text\":\"4 Oct 2014, 17:52\",\"uts\":\"1412445143\"}}," +
            "\"@attr\":{\"user\":\"STIGGPWNZ\",\"page\":\"1\",\"perPage\":\"1\"," +
            "\"totalPages\":\"81583\",\"total\":\"81583\"}}}" +
            "",
        clazz);
  }

  InputStreamReader reader(String filename) throws IOException {
    InputStream stream = getClass().getResourceAsStream("/" + filename);
    return new InputStreamReader(stream);
  }
}
