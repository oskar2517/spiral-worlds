package me.oskar.spiralworld;

import net.minecraft.util.Interval;
import net.minecraft.world.level.levelgen.synth.Noise;
import org.apache.commons.imaging.formats.bmp.BmpImageParser;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BadAppleNoise implements Noise {

    private static float WHITE = 1f;
    private static float BLACK = -1f;

    private BufferedImage bmp;

    public BadAppleNoise() {
        var image = readResourceAsString("assets/spiral-world/058.bmp");

        try {
            bmp = new BmpImageParser().getBufferedImage(image, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readResourceAsString(String resourceName) {
        try (var inputStream = SpiralWorld.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }

            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error reading resource", e);
        }
    }

    @Override
    public Interval range() {
        return Interval.encapsulating(BLACK, WHITE);
    }

    @Override
    public float get(double x, double z) {
        var imageX = (int) Math.floor(x) + bmp.getWidth() / 2;
        var imageY = (int) Math.floor(z) + bmp.getHeight() / 2;

        if (imageX < 0 || imageY < 0 || imageX >= bmp.getWidth() || imageY >= bmp.getHeight()) {
            return BLACK;
        }

        var pixel = bmp.getRGB(imageX, imageY);

        var brightness = (((pixel >> 16) & 0xFF) + ((pixel >> 8) & 0xFF) + (pixel & 0xFF)) / 3;

        return brightness > 127 ? WHITE : BLACK;
    }

    @Override
    public float get(double x, double y, double z) {
        return get(x, z);
    }
}
