package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static final RegistryObject<SoundEvent> HORNED_VIPER_DEATH = SOUNDS.register("horned_viper.death", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "horned_viper.death")));
    public static final RegistryObject<SoundEvent> HORNED_VIPER_HURT = SOUNDS.register("horned_viper.hurt", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "horned_viper.hurt")));
    public static final RegistryObject<SoundEvent> HORNED_VIPER_AMBIENT = SOUNDS.register("horned_viper.ambient", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "horned_viper.ambient")));

    public static final RegistryObject<SoundEvent> MUSK_OX_DEATH = SOUNDS.register("musk_ox.death", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "musk_ox.death")));
    public static final RegistryObject<SoundEvent> MUSK_OX_HURT = SOUNDS.register("musk_ox.hurt", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "musk_ox.hurt")));
    public static final RegistryObject<SoundEvent> MUSK_OX_AMBIENT = SOUNDS.register("musk_ox.ambient", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "musk_ox.ambient")));

    public static final RegistryObject<SoundEvent> PACMAN_FROG_DEATH = SOUNDS.register("pacman_frog.death", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "pacman_frog.death")));
    public static final RegistryObject<SoundEvent> PACMAN_FROG_HURT = SOUNDS.register("pacman_frog.hurt", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "pacman_frog.hurt")));
    public static final RegistryObject<SoundEvent> PACMAN_FROG_AMBIENT = SOUNDS.register("pacman_frog.ambient", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "pacman_frog.ambient")));

    public static final RegistryObject<SoundEvent> SNOW_LEOPARD_DEATH = SOUNDS.register("snow_leopard.death", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "snow_leopard.death")));
    public static final RegistryObject<SoundEvent> SNOW_LEOPARD_HURT = SOUNDS.register("snow_leopard.hurt", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "snow_leopard.hurt")));
    public static final RegistryObject<SoundEvent> SNOW_LEOPARD_AMBIENT = SOUNDS.register("snow_leopard.ambient", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "snow_leopard.ambient")));

    public static final RegistryObject<SoundEvent> HUMPBACK_WHALE_DEATH = SOUNDS.register("humpback_whale.death", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "humpback_whale.death")));
    public static final RegistryObject<SoundEvent> HUMPBACK_WHALE_HURT = SOUNDS.register("humpback_whale.hurt", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "humpback_whale.hurt")));
    public static final RegistryObject<SoundEvent> HUMPBACK_WHALE_AMBIENT = SOUNDS.register("humpback_whale.ambient", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "humpback_whale.ambient")));
    public static final RegistryObject<SoundEvent> HUMPBACK_WHALE_SONG = SOUNDS.register("humpback_whale.song", () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, "humpback_whale.song")));
}
