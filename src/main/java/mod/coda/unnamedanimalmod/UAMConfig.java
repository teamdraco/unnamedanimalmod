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

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Animal Spawn Weight");
            blackDiamondStingraySpawnWeight = builder.comment("Spawn weight of Black Diamond Stingrays").defineInRange("black_diamond_stingray_spawn_weight", 1, 1, 1000);
            tomatoFrogSpawnWeight = builder.comment("Spawn weight of Tomato Frogs").defineInRange("tomato_frog_spawn_weight", 3, 1, 1000);
            elephantnoseFishSpawnWeight = builder.comment("Spawn weight of Elephantnose Fish").defineInRange("elephantnose_fish_spawn_weight", 1, 1, 1000);
            southernRightWhaleSpawnWeight = builder.comment("Spawn weight of Elephantnose Fish").defineInRange("elephantnose_fish_spawn_weight", 50, 1, 1000);
            builder.pop();
        }

        public static void reload() {
            UAMConfig.blackDiamondStingraySpawnWeight = INSTANCE.blackDiamondStingraySpawnWeight.get();
            UAMConfig.tomatoFrogSpawnWeight = INSTANCE.tomatoFrogSpawnWeight.get();
            UAMConfig.elephantnoseFishSpawnWeight = INSTANCE.elephantnoseFishSpawnWeight.get();
            UAMConfig.southernRightWhaleSpawnWeight = INSTANCE.southernRightWhaleSpawnWeight.get();
        }
    }
}
