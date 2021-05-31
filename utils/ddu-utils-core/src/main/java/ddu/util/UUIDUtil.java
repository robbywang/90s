package ddu.util;

import java.security.SecureRandom;

public class UUIDUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String randomUUID(int size, boolean alphanumeric) {
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */

        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++) {
          msb = (msb << 8) | (randomBytes[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
          lsb = (lsb << 8) | (randomBytes[i] & 0xff);
        }

        String retVal = digits(msb >> 32, 4, alphanumeric) +
                digits(msb >> 16, 4, alphanumeric) +
                digits(msb, 4, alphanumeric) +
                digits(lsb >> 48, 2, alphanumeric) +
                digits(lsb, 2, alphanumeric);

        // if required size is less that 16, trim it.
        if(retVal.length() > size) {
            retVal = retVal.substring(0, size);
        }
        return retVal;
    }

    private static String digits(long val, int digits, boolean alphanumeric) {
        long hi = 1L << (digits * 4);
        hi = hi | (val & (hi - 1));
        return alphanumeric ? Long.toHexString(hi).substring(1) :
                Long.toOctalString(hi).substring(1);
    }


}
