package Base58;

import java.math.BigInteger;
import java.util.Arrays;

public class Base58 {

    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int BASE = ALPHABET.length;

    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }
        BigInteger number = new BigInteger(1, input);
        StringBuilder result = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(BigInteger.valueOf(BASE));
            result.append(ALPHABET[divmod[1].intValue()]);
            number = divmod[0];
        }
        for (int i = 0; i < input.length && input[i] == 0; i++) {
            result.append(ALPHABET[0]);
        }
        return result.reverse().toString();
    }

    public static byte[] decode(String input) {
        if (input.length() == 0) {
            return new byte[0];
        }
        BigInteger number = BigInteger.ZERO;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int value = Arrays.binarySearch(ALPHABET, c);
            if (value < 0) {
                throw new IllegalArgumentException("Invalid character in Base58 string: " + c);
            }
            number = number.multiply(BigInteger.valueOf(BASE)).add(BigInteger.valueOf(value));
        }
        byte[] bytes = number.toByteArray();
        byte[] result = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, result, 0, result.length);
        for (int i = 0; i < input.length() && input.charAt(i) == ALPHABET[0]; i++) {
            result = Arrays.copyOfRange(result, 1, result.length);
        }
        return result;
    }
}


