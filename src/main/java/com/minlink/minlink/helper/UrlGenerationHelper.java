package com.minlink.minlink.helper;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlGenerationHelper {

    static {
        UrlGenerationHelper.setNewSeed();
    }

    private UrlGenerationHelper() {
    }

    private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static AtomicInteger seed;
    private static int beginSeed;

    private static int getSeedLong() {
        int newSeed = seed.incrementAndGet();
        if (newSeed <= (beginSeed + 1) * 1000) {
            return newSeed;
        }
        UrlGenerationHelper.setNewSeed();
        return UrlGenerationHelper.getSeedLong();
    }

    private static synchronized void setNewSeed() {
        //This is a dummy implementation where we set the next integer, in actual it should be based on Redis and need not be synchronized
        if (seed == null) {
            seed = new AtomicInteger();
            beginSeed = 0;
        } else {
            beginSeed = seed.incrementAndGet();
            seed = new AtomicInteger();
        }
    }

    public static String generatePrefix() {
        long seed = getSeedLong();
        BigInteger bigValue = BigInteger.valueOf(seed);
        BigInteger radix = BigInteger.valueOf(CHARS.length);
        StringBuilder sb = new StringBuilder();

        while (bigValue.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = bigValue.divideAndRemainder(radix);
            bigValue = divmod[0];
            sb.insert(0, CHARS[divmod[1].intValue()]);
        }

        while (sb.length() < 7) {  // pad with '0' in the beginning if the length is less than 7
            sb.insert(0, '0');
        }
        return sb.substring(sb.length() - 7);  // take the last 7 characters
    }
}
