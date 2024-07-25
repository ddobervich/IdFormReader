package Filters;

import Interfaces.Drawable;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

public class FindIdFilter implements PixelFilter, Drawable {
    private static final int BLACK_BORDER_THRESHOLD = 180;
    private int x = 473, y = 7;
    private float width = 23.3f, height = 23f;
    private int digitImgSize = 28;

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        short[][][] digits = extractDigits(pixels, x, y, width, height, 5);

        for (int digit = 0; digit < 5; digit++) {
            removeBlackBorder(digits[digit]);
        }

        drawDigits(pixels, digits, 300, 100);

        img.setPixels(pixels);
        return img;
    }

    private void drawDigits(short[][] pixels, short[][][] digits, int r, int c) {
        int w = digits[0][0].length;
        int h = digits[0].length;

        for (int digit = 0; digit < 5; digit++) {
            for (int tr = r + digit*h; tr < r + digit*h + h; tr++) {
                for (int tc = c; tc < c+w; tc++) {
                    pixels[tr][tc] = digits[digit][tr - (r + digit*h)][tc - c];
                }
            }
        }
    }

    private void removeBlackBorder(short[][] digit) {
        for (int c = 0; c < 10; c++) {
            if (averageRowVal(digit, c) < BLACK_BORDER_THRESHOLD) {
                colorRow(digit, c, 255); // turn col white
            }
            if (averageRowVal(digit, digit[0].length - c - 1) < BLACK_BORDER_THRESHOLD) {
                colorRow(digit, digit[0].length - c - 1, 255); // turn col white
            }
        }

        for (int r = 0; r < 10; r++) {
            if (averageColVal(digit, r) < BLACK_BORDER_THRESHOLD) {
                colorColumn(digit, r, 255); // turn col white
            }
            if (averageColVal(digit, digit.length - r - 1) < BLACK_BORDER_THRESHOLD) {
                colorColumn(digit, digit.length - r - 1, 255); // turn col white
            }
        }
    }

    private float averageRowVal(short[][] digit, int c) {
        float sum = 0;
        for (int r = 0; r < digit.length; r++) {
            sum += digit[r][c];
        }
        return sum / digit.length;
    }

    private void colorRow(short[][] digit, int c, int value) {
        for (int r = 0; r < digit.length; r++) {
            digit[r][c] = (short)value;
        }
    }

    private void colorColumn(short[][] digit, int r, int value) {
        for (int c = 0; c < digit[r].length; c++) {
            digit[r][c] = (short)value;
        }
    }

    private float averageColVal(short[][] digit, int r) {
        float sum = 0;
        for (int c = 0; c < digit[r].length; c++) {
            sum += digit[r][c];
        }
        return sum / digit[r].length;
    }

    private short[][][] extractDigits(short[][] pixels, int x, int y, float width, float height, int num) {
        short[][][] digits = new short[num][digitImgSize][digitImgSize];

        for (int boxNum = 0; boxNum < 5; boxNum++) {
            extractDigit(pixels, x+width*boxNum, y, width, height, boxNum, digits, 3, 3);
        }

        return digits;
    }

    private void extractDigit(short[][] pixels, float x, int y, float width, float height, int boxNum, short[][][] digits, int rowOffset, int colOffset) {
        for (int r = y; r < y+height; r++) {
            for (int c = (int) x; c < x+width; c++) {
                digits[boxNum][rowOffset + r-y][colOffset + (int) (c-x)] = pixels[r][c];
            }
        }
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.stroke(255,0,0);
        window.strokeWeight(1);
        window.fill(255, 0, 0, 0);
        for (int boxNum = 0; boxNum < 5; boxNum++) {

            window.rect(x + width*boxNum, y, width, height);
        }
    }
}
