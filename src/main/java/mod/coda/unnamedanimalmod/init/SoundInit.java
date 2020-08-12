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

}
