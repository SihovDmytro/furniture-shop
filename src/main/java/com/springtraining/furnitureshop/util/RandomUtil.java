package com.springtraining.furnitureshop.util;

import java.util.Random;

public class RandomUtil {
    public static String generateRandomNumbers(int length) {
        StringBuilder numbers = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            numbers.append(random.nextInt(10));
        }
        return numbers.toString();
    }

    public static long generateLong() {
        return new Random().nextLong();
    }
}
