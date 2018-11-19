package com.example.wiktoriasalamon.flashlight;

import org.junit.Assert;
import org.junit.Test;

public class HexadecimalTest {
  @Test
  public void ConvertDecToHex_returnsTrue() {
    final String expected = "E05";
    final String actual = Hexadecimal.convertDecToHex(3589);
    Assert.assertEquals(expected, actual);
  }

}
