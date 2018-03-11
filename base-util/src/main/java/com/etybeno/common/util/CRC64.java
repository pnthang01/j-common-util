package com.etybeno.common.util;

/**
 * Created by thangpham on 27/02/2018.
 */
public final class CRC64 {

    private static final long POLY64REV = 0xd800000000000000L;
    private static final long POLY64 = 0x42F0E1EBA9EA3693L;

    private static final long[] LOOKUPTABLE;
    private static final long[] LOOKUPTABLE2;

    static {
        LOOKUPTABLE = new long[0x100];
        fillTable(LOOKUPTABLE, POLY64REV);
        //
        LOOKUPTABLE2 = new long[0x100];
        fillTable(LOOKUPTABLE2, POLY64);
    }

    private static void fillTable(long[] table, long seed) {
        for (int i = 0; i < 0x100; i++) {
            long crc = i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ seed;
                } else {
                    crc = (crc >>> 1);
                }
            }
            table[i] = crc;
        }
    }


    /**
     * Calculates the CRC64 checksum for the given data array.
     *
     * @param data data to calculate checksum for
     * @return checksum value
     */
    public static long hashByAlgo1(final byte[] data) {
        long sum = 0;
        for (final byte b : data) {
            final int lookupidx = ((int) sum ^ b) & 0xff;
            sum = (sum >>> 8) ^ LOOKUPTABLE[lookupidx];
        }
        return sum;
    }

    private CRC64() {
    }

    /**
     * The checksum of the data
     *
     * @param data The data to checksum
     * @return The checksum of the data
     */
    public static long hashByAlgo2(final byte[] data) {
        long checksum = 0;
        for (int i = 0; i < data.length; i++) {
            final int lookupidx = ((int) checksum ^ data[i]) & 0xff;
            checksum = (checksum >>> 8) ^ LOOKUPTABLE2[lookupidx];
        }
        return checksum;
    }

}

