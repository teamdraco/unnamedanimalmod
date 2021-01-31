package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.entity.*;
import mod.coda.unnamedanimalmod.entity.item.GreaterPrairieChickenEggEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UAMEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, UnnamedAnimalMod.MOD_ID);

    public static final RegistryObject<EntityType<HonduranWhiteBatEntity>> HONDURAN_WHITE_BAT = create("honduran_white_bat", EntityType.Builder.create(HonduranWhiteBatEntity::new, EntityClassification.CREATURE).size(0.3f, 0.3f));
    public static final RegistryObject<EntityType<VineSnakeEntity>> VINE_SNAKE = create("vine_snake", EntityType.Builder.create(VineSnakeEntity::new, EntityClassification.CREATURE).size(0.6f, 0.3f));
    public static final RegistryObject<EntityType<BlackDiamondStingrayEntity>> BLACK_DIAMOND_STINGRAY = create("black_diamond_stingray", EntityType.Builder.create(BlackDiamondStingrayEntity::new, EntityClassification.WATER_CREATURE).size(0.8f, 0.2f));
    public static final RegistryObject<EntityType<TomatoFrogEntity>> TOMATO_FROG = create("tomato_frog", EntityType.Builder.create(TomatoFrogEntity::new, EntityClassification.CREATURE).size(0.4f, 0.35f));
    public static final RegistryObject<EntityType<SouthernRightWhaleEntity>> SOUTHERN_RIGHT_WHALE = create("southern_right_whale", EntityType.Builder.create(SouthernRightWhaleEntity::new, EntityClassification.WATER_CREATURE).size(3.0f, 3.0f));
    public static final RegistryObject<EntityType<GreaterPrairieChickenEntity>> GREATER_PRAIRIE_CHICKEN = create("greater_prairie_chicken", EntityType.Builder.create(GreaterPrairieChickenEntity::new, EntityClassification.CREATURE).size(0.8f, 0.8f));

    public static final RegistryObject<EntityType<GreaterPrairieChickenEggEntity>> GREATER_PRAIRIE_CHICKEN_EGG = create("greater_prairie_chicken_egg", EntityType.Builder.<GreaterPrairieChickenEggEntity>create(GreaterPrairieChickenEggEntity::new, EntityClassification.MISC).size(0.25f, 0.25f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(UnnamedAnimalMod.MOD_ID + "." + name));
    }
}