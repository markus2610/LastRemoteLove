package com.meoyawn.remotelove.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adelnizamutdinov on 10/3/14
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LastFmTest {
  @Test public void testMd5() {
    final String actual = ApiPackage.md5("123");
    assertThat(actual).hasSize(32).isEqualTo("202cb962ac59075b964b07152d234b70");
    assertThat(ApiPackage.md5("AHAHA")).isEqualTo("0e0d3b55d9348830fbf722f45a8cb8d2");
  }
}
