package teamdraco.unnamedanimalmod.init;

import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<SoundEvent> FROG_AMBIENT = SOUNDS.register("frog.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.ambient")));
    public static final RegistryObject<SoundEvent> FROG_DEATH = SOUNDS.register("frog.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.death")));
    public static final RegistryObject<SoundEvent> FROG_HURT = SOUNDS.register("frog.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "frog.hurt")));

    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_AMBIENT = SOUNDS.register("southern_right_whale.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.ambient")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_DEATH = SOUNDS.register("southern_right_whale.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.death")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_HURT = SOUNDS.register("southern_right_whale.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.hurt")));
    public static final RegistryObject<SoundEvent> SOUTHERN_RIGHT_WHALE_SONG = SOUNDS.register("southern_right_whale.song", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "southern_right_whale.song")));

    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_AMBIENT = SOUNDS.register("greater_prairie_chicken.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.ambient")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_DEATH = SOUNDS.register("greater_prairie_chicken.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.death")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_HURT = SOUNDS.register("greater_prairie_chicken.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.hurt")));
    public static final RegistryObject<SoundEvent> GREATER_PRAIRIE_CHICKEN_BOOMING = SOUNDS.register("greater_prairie_chicken.booming", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "greater_prairie_chicken.booming")));

    public static final RegistryObject<SoundEvent> MUSK_OX_AMBIENT = SOUNDS.register("musk_ox.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.ambient")));
    public static final RegistryObject<SoundEvent> MUSK_OX_DEATH = SOUNDS.register("musk_ox.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.death")));
    public static final RegistryObject<SoundEvent> MUSK_OX_HURT = SOUNDS.register("musk_ox.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "musk_ox.hurt")));

    public static final RegistryObject<SoundEvent> MARINE_IGUANA_AMBIENT = SOUNDS.register("marine_iguana.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.ambient")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_DEATH = SOUNDS.register("marine_iguana.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.death")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_HURT = SOUNDS.register("marine_iguana.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.hurt")));
    public static final RegistryObject<SoundEvent> MARINE_IGUANA_TRANSFORMS = SOUNDS.register("marine_iguana.transforms", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "marine_iguana.transforms")));

    public static final RegistryObject<SoundEvent> CAPYBARA_AMBIENT = SOUNDS.register("capybara.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.ambient")));
    public static final RegistryObject<SoundEvent> CAPYBARA_DEATH = SOUNDS.register("capybara.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.death")));
    public static final RegistryObject<SoundEvent> CAPYBARA_HURT = SOUNDS.register("capybara.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "capybara.hurt")));

    public static final RegistryObject<SoundEvent> MANGROVE_SNAKE_AMBIENT = SOUNDS.register("mangrove_snake.ambient", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "mangrove_snake.ambient")));
    public static final RegistryObject<SoundEvent> MANGROVE_SNAKE_DEATH = SOUNDS.register("mangrove_snake.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "mangrove_snake.death")));
    public static final RegistryObject<SoundEvent> MANGROVE_SNAKE_HURT = SOUNDS.register("mangrove_snake.hurt", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "mangrove_snake.hurt")));

    public static final RegistryObject<SoundEvent> FIDDLER_CRAB_DEATH = SOUNDS.register("fiddler_crab.death", () -> new SoundEvent(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "fiddler_crab.death")));
}
