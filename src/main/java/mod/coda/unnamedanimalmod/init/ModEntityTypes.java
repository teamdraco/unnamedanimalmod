package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final EntityType<FireSalamanderEntity> FIRE_SALAMANDER = create("fire_salamander", FireSalamanderEntity::new, EntityClassification.CREATURE, 0.4f, 0.2f, 0x14151b, 0xffab00);
    public static final EntityType<HornedViperEntity> HORNED_VIPER = create("horned_viper", HornedViperEntity::new, EntityClassification.CREATURE, 0.8f, 0.2f, 0xd7c390, 0x644630);
    public static final EntityType<HumpheadParrotfishEntity> HUMPHEAD_PARROTFISH = create("humphead_parrotfish", HumpheadParrotfishEntity::new, EntityClassification.WATER_CREATURE, 0.9f, 0.7f, 0x40705c, 0xd29cac);
    public static final EntityType<PigNosedTurtleEntity> PIG_NOSED_TURTLE = create("pig_nosed_turtle", PigNosedTurtleEntity::new, EntityClassification.WATER_CREATURE, 0.8f, 0.4f, 0x5d6161, 0xd8d5c9);
    public static final EntityType<MuskOxEntity> MUSK_OX = create("musk_ox", MuskOxEntity::new, EntityClassification.CREATURE, 1.2f, 1.5f, 0x783b2e, 0xe0c8a5);
    public static final EntityType<PacmanFrogEntity> PACMAN_FROG = create("pacman_frog", PacmanFrogEntity::new, EntityClassification.CREATURE, 0.4f, 0.4f, 0xbdc722, 0x803f22);

    private static <T extends AnimalEntity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int pri, int sec) {
        final Item.Properties properties = new Item.Properties().group(Main.GROUP);
        EntityType<T> type = create(name, factory, classification, width, height);
        ItemInit.ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(type, pri, sec, properties));
        return type;
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).setTrackingRange(128).build(name);
        ENTITY_TYPES.register(name, () -> type);
        return type;
    }
}