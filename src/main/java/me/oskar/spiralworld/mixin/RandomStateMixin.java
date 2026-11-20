package me.oskar.spiralworld.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.oskar.spiralworld.SpiralNoise;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.Noise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(RandomState.class)
abstract class RandomStateMixin {

    @Unique
    private static final Set<ResourceKey<NormalNoise>> SPIRAL_NOISES = Set.of(
            Noises.CONTINENTALNESS, Noises.EROSION, Noises.RIDGE,
            Noises.TEMPERATURE, Noises.VEGETATION, Noises.JAGGED);

    @ModifyReturnValue(method = "getOrCreateNoise", at = @At("RETURN"))
    private Noise wrapNoise(Noise noise, ResourceKey<NormalNoise> key) {
        return SPIRAL_NOISES.contains(key) ? new SpiralNoise(noise) : noise;
    }
}