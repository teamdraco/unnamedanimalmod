package mod.coda.unnamedanimalmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = UnnamedAnimalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UAMConfig {
    public static int blackDiamondStingraySpawnWeight;
    public static int tomatoFrogSpawnWeight;
    public static int elephantnoseFishSpawnWeight;
    public static int southernRightWhaleSpawnWeight;
    public static int greaterPrairieChickenSpawnWeight;
    public static int muskOxSpawnWeight;
    public static int humpheadParrotfishSpawnWeight;
    public static int flashlightFishSpawnWeight;
    public static int marineIguanaSpawnWeight;
    public static int bananaSlugSpawnWeight;
    public static int platypusSpawnWeight;
    public static int pacmanFrogSpawnWeight;

    @SubscribeEvent
    public static void configLoad(ModConfig.ModConfigEvent event) {
        try {
            ForgeConfigSpec spec = event.getConfig().getSpec();
            if (spec == Common.SPEC) Common.reload();
        }
        catch (Throwable e) {
            UnnamedAnimalMod.LOGGER.error("Something went wrong updating the Unnamed Animal Mod config, using previous or default values! {}", e.toString());
        }
    }

    public static class Common {
        public static final Common INSTANCE;
        public static final ForgeConfigSpec SPEC;

        static {
            Pair<Common, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Common::new);
            INSTANCE = pair.getLeft();
            SPEC = pair.getRight();
        }

        public final ForgeConfigSpec.IntValue blackDiamondStingraySpawnWeight;
        public final ForgeConfigSpec.IntValue tomatoFrogSpawnWeight;
        public final ForgeConfigSpec.IntValue elephantnoseFishSpawnWeight;
        public final ForgeConfigSpec.IntValue southernRightWhaleSpawnWeight;
        public final ForgeConfigSpec.IntValue greaterPrairieChickenSpawnWeight;
        public final ForgeConfigSpec.IntValue muskOxSpawnWeight;
        public final ForgeConfigSpec.IntValue humpheadParrotfishSpawnWeight;
        public final ForgeConfigSpec.IntValue flashlightFishSpawnWeight;
        public final ForgeConfigSpec.IntValue marineIguanaSpawnWeight;
        public final ForgeConfigSpec.IntValue bananaSlugSpawnWeight;
        public final ForgeConfigSpec.IntValue platypusSpawnWeight;
        public final ForgeConfigSpec.IntValue pacmanFrogSpawnWeight;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Animal Spawn Weight");
            blackDiamondStingraySpawnWeight = builder.comment("Spawn weight of Black Diamond Stingrays").defineInRange("black_diamond_stingray_spawn_weight", 1, 1, 1000);
            tomatoFrogSpawnWeight = builder.comment("Spawn weight of Tomato Frogs").defineInRange("tomato_frog_spawn_weight", 3, 1, 1000);
            elephantnoseFishSpawnWeight = builder.comment("Spawn weight of Elephantnose Fish").defineInRange("elephantnose_fish_spawn_weight", 1, 1, 1000);
            southernRightWhaleSpawnWeight = builder.comment("Spawn weight of Southern Right Whales").defineInRange("southern_right_whale_spawn_weight", 5, 1, 1000);
            greaterPrairieChickenSpawnWeight = builder.comment("Spawn weight of Greater Prairie Chickens").defineInRange("greater_prairie_chicken_spawn_weight", 15, 1, 1000);
            muskOxSpawnWeight = builder.comment("Spawn weight of Musk Ox").defineInRange("musk_ox_spawn_weight", 1, 1, 1000);
            humpheadParrotfishSpawnWeight = builder.comment("Spawn weight of Humphead Parrotfish").defineInRange("humphead_parrotfish_spawn_weight", 5, 1, 1000);
            flashlightFishSpawnWeight = builder.comment("Spawn weight of Flashlight Fish").defineInRange("flashlight_fish_spawn_weight", 4, 1, 1000);
            marineIguanaSpawnWeight = builder.comment("Spawn weight of Marine Iguanas").defineInRange("marine_iguana_spawn_weight", 15, 1, 1000);
            bananaSlugSpawnWeight = builder.comment("Spawn weight of Banana Slugs").defineInRange("banana_slug_spawn_weight", 45, 1, 1000);
            platypusSpawnWeight = builder.comment("Spawn weight of Platypuses").defineInRange("platypus_spawn_weight", 5, 1, 1000);
            pacmanFrogSpawnWeight = builder.comment("Spawn weight of Pacman Frogs").defineInRange("pacman_spawn_weight", 2, 1, 1000);
            builder.pop();
        }

        public static void reload() {
            UAMConfig.blackDiamondStingraySpawnWeight = INSTANCE.blackDiamondStingraySpawnWeight.get();
            UAMConfig.tomatoFrogSpawnWeight = INSTANCE.tomatoFrogSpawnWeight.get();
            UAMConfig.elephantnoseFishSpawnWeight = INSTANCE.elephantnoseFishSpawnWeight.get();
            UAMConfig.southernRightWhaleSpawnWeight = INSTANCE.southernRightWhaleSpawnWeight.get();
            UAMConfig.greaterPrairieChickenSpawnWeight = INSTANCE.greaterPrairieChickenSpawnWeight.get();
            UAMConfig.muskOxSpawnWeight = INSTANCE.muskOxSpawnWeight.get();
            UAMConfig.humpheadParrotfishSpawnWeight = INSTANCE.humpheadParrotfishSpawnWeight.get();
            UAMConfig.flashlightFishSpawnWeight = INSTANCE.flashlightFishSpawnWeight.get();
            UAMConfig.marineIguanaSpawnWeight = INSTANCE.marineIguanaSpawnWeight.get();
            UAMConfig.bananaSlugSpawnWeight = INSTANCE.bananaSlugSpawnWeight.get();
            UAMConfig.platypusSpawnWeight = INSTANCE.platypusSpawnWeight.get();
            UAMConfig.pacmanFrogSpawnWeight = INSTANCE.pacmanFrogSpawnWeight.get();
        }
    }
}
