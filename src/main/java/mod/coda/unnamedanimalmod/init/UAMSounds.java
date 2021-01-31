package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<SoundEvent> FROG_AMBIENT = REGISTRY.register("frog.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.ambient")));
    public static final RegistryObject<SoundEvent> FROG_DEATH = REGISTRY.register("frog.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.death")));
    public static final RegistryObject<SoundEvent> FROG_HURT = REGISTRY.register("frog.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.hurt")));

    public static final RegistryObject<SoundEvent> WHALE_AMBIENT = REGISTRY.register("whale.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "whale.ambient")));
    public static final RegistryObject<SoundEvent> WHALE_DEATH = REGISTRY.register("whale.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "whale.death")));
    public static final RegistryObject<SoundEvent> WHALE_HURT = REGISTRY.register("whale.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "whale.hurt")));
    public static final RegistryObject<SoundEvent> WHALE_SONG = REGISTRY.register("whale.song", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "whale.song")));
}