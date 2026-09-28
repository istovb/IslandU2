package com.javarush.island.istovb.util;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtil {

    private RandomUtil() {}

    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static boolean chance(int percent) {
        if (percent <= 0) return false;
        if (percent >= 100) return true;
        return ThreadLocalRandom.current().nextInt(100) < percent;
    }

    public static int between(int min, int max) {
        if (max <= min) return min;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}