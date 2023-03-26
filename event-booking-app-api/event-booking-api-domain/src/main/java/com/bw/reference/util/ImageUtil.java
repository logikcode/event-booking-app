package com.bw.reference.util;

public class ImageUtil {

    private static boolean isMatch(byte[] pattern, byte[] data) {
        if (pattern.length <= data.length) {
            for (int idx = 0; idx < pattern.length; ++idx) {
                if (pattern[idx] != data[idx])
                    return false;
            }
            return true;
        }

        return false;
    }

    public static String getImageType(byte[] data) {
//        filetype    magic number(hex)
//        jpg         FF D8 FF
//        gif         47 49 46 38
//        png         89 50 4E 47 0D 0A 1A 0A
//        bmp         42 4D
//        tiff(LE)    49 49 2A 00
//        tiff(BE)    4D 4D 00 2A

        final byte[] pngPattern = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        final byte[] jpgPattern = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        final byte[] gifPattern = new byte[]{0x47, 0x49, 0x46, 0x38};
        final byte[] bmpPattern = new byte[]{0x42, 0x4D};
        final byte[] tiffLEPattern = new byte[]{0x49, 0x49, 0x2A, 0x00};
        final byte[] tiffBEPattern = new byte[]{0x4D, 0x4D, 0x00, 0x2A};
        if (isMatch(pngPattern, data))
            return "image/png";

        if (isMatch(jpgPattern, data))
            return "image/jpg";

        if (isMatch(gifPattern, data))
            return "image/gif";

        if (isMatch(bmpPattern, data))
            return "image/bmp";

        if (isMatch(tiffLEPattern, data))
            return "image/tif";

        if (isMatch(tiffBEPattern, data))
            return "image/tif";

        return "image/png";
    }
}
