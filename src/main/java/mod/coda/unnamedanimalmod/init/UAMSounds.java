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

    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_AMBIENT = REGISTRY.register("southern_right_whale.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.ambient")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_DEATH = REGISTRY.register("southern_right_whale.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.death")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_HURT = REGISTRY.register("southern_right_whale.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.hurt")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_SONG = REGISTRY.register("southern_right_whale.song", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.song")));

    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_AMBIENT = REGISTRY.register("greater_prairie_chicken.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.ambient")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_DEATH = REGISTRY.register("greater_prairie_chicken.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.death")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_HURT = REGISTRY.register("greater_prairie_chicken.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.hurt")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_BOOMING = REGISTRY.register("greater_prairie_chicken.booming", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.booming")));

    public static final RegistryObject<SoundEvent> MUSK_OX_AMBIENT = REGISTRY.register("musk_ox.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.ambient")));
    public static final RegistryObject<SoundEvent> MUSK_OX_DEATH = REGISTRY.register("musk_ox.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.death")));
    public static final RegistryObject<SoundEvent> MUSK_OX_HURT = REGISTRY.register("musk_ox.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.hurt")));

    public static final RegistryObject<SoundEvent> MARINE_IGUANA_AMBIENT = REGISTRY.register("marine_iguana.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.ambient")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_DEATH = REGISTRY.register("marine_iguana.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.death")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_HURT = REGISTRY.register("marine_iguana.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.hurt")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_TRANSFORMS = REGISTRY.register("marine_iguana.transforms", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.transforms")));

    public static final RegistryObject<SoundEvent> CAPYBARA_AMBIENT = REGISTRY.register("capybara.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.ambient")));
    public static final RegistryObject<SoundEvent> CAPYBARA_DEATH = REGISTRY.register("capybara.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.death")));
    public static final RegistryObject<SoundEvent> CAPYBARA_HURT = REGISTRY.register("capybara.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.hurt")));
}