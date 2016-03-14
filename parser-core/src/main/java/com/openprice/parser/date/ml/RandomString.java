package com.openprice.parser.date.ml;

import java.util.Random;

public class RandomString {

    private static final char[] SYMBOLS;
    private final Random random = new Random();
    private final char[] buf;

    static {
      StringBuilder tmp = new StringBuilder();
      for (char ch = '0'; ch <= '9'; ++ch)
        tmp.append(ch);
      for (char ch = 'a'; ch <= 'z'; ++ch)
        tmp.append(ch);
      for (char ch = 'A'; ch <= 'Z'; ++ch)
          tmp.append(ch);
      SYMBOLS = tmp.toString().toCharArray();
    }

    public RandomString(int length) {
      if (length < 1)
        throw new IllegalArgumentException("length < 1: " + length);
      buf = new char[length];
    }

    public String nextString() {
      for (int idx = 0; idx < buf.length; ++idx)
        buf[idx] = SYMBOLS[random.nextInt(SYMBOLS.length)];
      return new String(buf);
    }
  }
