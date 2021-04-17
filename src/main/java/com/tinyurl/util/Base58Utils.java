package com.tinyurl.util;

public class Base58Utils {

    private static final char[] DIGITS_58_CODES = {'T', 'o', '9', 'u', 'M', 'W', 's', 'C', '6', 'p', '8', 'g', 'e', 'Z', 'f', 'k', 'N', 'J', 'i', 'v', 'r', '4', 'h', 'b', 'j', 'D', 'z', 'Q', 'G', 'Y', 'A', 't', 'a', 'd', 'R', 'x', 'c', '5', 'q', 'K', 'n', 'H', 'E', 'U', 'y', 'V', 'm', '2', 'w', 'B', 'X', '7', 'S', 'F', 'L', '3', 'P', '1'};

    private static int DIGITS_LEN = 58;

    /**
     * base 58编码。
     *
     * @param num
     * @return
     */
    public static String encodeBase58(long num) {
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / DIGITS_LEN) > 0) {
            buf[--charPos] = DIGITS_58_CODES[(int) (num % DIGITS_LEN)];
            num /= DIGITS_LEN;
        }
        buf[--charPos] = DIGITS_58_CODES[(int) (num % DIGITS_LEN)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * base 58解码。
     *
     * @param code
     * @return
     */
    public static long decodeBase58(String code) {
        char[] charBuf = code.toCharArray();
        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i--) {
            int index = 0;
            for (int j = 0, length = DIGITS_58_CODES.length; j < length; j++) {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (DIGITS_58_CODES[j] == charBuf[i]) {
                    index = j;
                }
            }
            result += index * base;
            base *= DIGITS_LEN;
        }
        return result;
    }

    /**
     * 乱序加密数字。
     *
     * @param num
     * @return
     */
    public static long encodeNum(long num) {
        if (num < 0) {
            num = Math.abs(num);
        }
        String data = String.valueOf(num);
        int mask =(int) (num % 9) + 1;
        int pos = mask;
        if (mask > data.length()) {
            pos = mask % data.length();
        } else if (mask == data.length()) {
            pos = 1;
        }
        num = Long.parseLong(mask + data.substring(pos) + data.substring(0, pos));
        return num;
    }

    /**
     * 乱序解密数字。
     *
     * @param num
     * @return
     */
    public static long decodeNum(long num) {
        String data = String.valueOf(num);
        int mask = Integer.parseInt(data.substring(0, 1));
        int len = data.length() - 1;
        int pos = mask;
        if (mask > len) {
            pos = mask % len;
        } else if (mask == len) {
            pos = 1;
        }
        pos = len - pos;
        return Long.parseLong(data.substring(pos + 1) + data.substring(1, pos + 1));
    }

    public static void main(String[] args) {
//        long time = System.currentTimeMillis();
//        System.out.println("16进制结果" + Long.toUnsignedString(time, 16));
//        System.out.println("36进制结果" + Long.toUnsignedString(time, 36));
//        System.out.println("58进制结果" + decodeLong("ocPEU"));
//        System.out.println(encodeNum(12345678));
//        System.out.println(decodeNum(encodeNum(10000001)));
        System.out.println(decodeBase58("9pkLS"));
        System.out.println(decodeNum(decodeBase58("9pkLS")));

//        for (int i = 23314894; i < 23314994; i++) {
//            System.out.println(encodeBase58(encodeNum(i)));
//            System.out.println(decodeNum(decodeBase58("rz4tB")));
//        }
//
//        List<Character> list = new ArrayList<>();
//        for (char c : DIGITS_58_CODES) {
//            list.add(c);
//        }
//        Collections.shuffle(list);
//        for (Character c : list) {
//            System.out.print("'" + c + "',");
//        }
    }

}
