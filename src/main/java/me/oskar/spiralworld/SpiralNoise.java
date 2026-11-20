package me.oskar.spiralworld;

import net.minecraft.util.Interval;
import net.minecraft.world.level.levelgen.synth.Noise;

public class SpiralNoise implements Noise {

    private static final double TWIST = 3.0;
    private final Noise baseNoise;

    public SpiralNoise(Noise baseNoise) {
        this.baseNoise = baseNoise;
    }

    @Override
    public Interval range() {
        return baseNoise.range();
    }

    @Override
    public float get(double x, double z) {
        var r = Math.sqrt(x * x + z * z);
        if (r < 1.0E-9) return baseNoise.get(x, z);

        var a = TWIST * Math.log(r);
        var c = Math.cos(a);
        var s = Math.sin(a);
        return baseNoise.get(x * c - z * s, x * s + z * c);
    }

    @Override
    public float get(double x, double y, double z) {
        var r = Math.sqrt(x * x + z * z);
        if (r < 1.0E-9) return baseNoise.get(x, y, z);

        var a = TWIST * Math.log(r);
        var c = Math.cos(a);
        var s = Math.sin(a);
        return baseNoise.get(x * c - z * s, y, x * s + z * c);
    }
}