package me.oskar.spiralworld.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.oskar.spiralworld.BadAppleNoise;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.Noise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RandomState.class)
abstract class RandomStateMixin {

    @Unique
    private BadAppleNoise badAppleNoise = new BadAppleNoise();

    @ModifyReturnValue(method = "getOrCreateNoise", at = @At("RETURN"))
    private Noise wrapNoise(Noise noise, ResourceKey<NormalNoise> key) {
        if (key != Noises.CONTINENTALNESS) return noise;

        return badAppleNoise;
    }
}
