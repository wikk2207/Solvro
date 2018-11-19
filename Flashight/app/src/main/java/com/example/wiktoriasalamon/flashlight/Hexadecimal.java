package com.example.wiktoriasalamon.flashlight;

import java.util.Random;

/**
 * Klasa Hexadecimal generuje i przechowuje losowa liczbe szesnastkową z zakresu 0-FF (0-255).
 */
public class Hexadecimal {
  private int decimal;
  private String hexadecimal;

  Hexadecimal() {
    Random random = new Random();
    decimal = random.nextInt(256);
    hexadecimal = convertDecToHex(decimal);
  }

  /**
   * Funkcja convertDecToHex zamienia liczbę dziesiętną na szesnastkową.
   *
   * @param dec Liczba dziesiętna, która ma być przekonwertowana.
   * @return Liczba szestnastkowa zapisana jako String.
   */
  public static String convertDecToHex(int dec) {
    String result = "";
    int number;
    int rest;

    while (dec > 0) {
      number = dec / 16;
      rest = dec - (number * 16);
      dec = number;
      if (rest >= 10) {
        switch (rest) {
          case 10:
            result = "A" + result;
            break;
          case 11:
            result = "B" + result;
            break;
          case 12:
            result = "C" + result;
            break;
          case 13:
            result = "D" + result;
            break;
          case 14:
            result = "E" + result;
            break;
          case 15:
            result = "F" + result;
            break;
          default:
            break;
        }
      } else {
        result = String.valueOf(rest) + result;
      }
    }

    return result;
  }

  public int getDecimal() {
    return decimal;
  }

  public String getDecimalString() {
    return Integer.toString(decimal);
  }

  public String getHexadecimal() {
    return hexadecimal;
  }

  public boolean numberEquals (String number, int mode) {
    if (mode == 1) {
      int numberInt = Integer.parseInt(number);
      if(numberInt == decimal) {
        return true;
      } else {
        return false;
      }
    } else {
      if(number.equalsIgnoreCase(hexadecimal)) {
        return true;
      } else {
        return false;
      }
    }
  }

}