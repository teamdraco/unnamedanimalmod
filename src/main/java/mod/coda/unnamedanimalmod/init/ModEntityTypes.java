package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.entity.FireSalamanderEntity;
import mod.coda.unnamedanimalmod.entity.HornedViperEntity;
import mod.coda.unnamedanimalmod.entity.HumpheadParrotfishEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final RegistryObject<EntityType<FireSalamanderEntity>> FIRE_SALAMANDER_ENTITY = ENTITY_TYPES.register("fire_salamander_entity", () -> EntityType.Builder.<FireSalamanderEntity>create(FireSalamanderEntity::new, EntityClassification.CREATURE).size(0.4f, 0.2f).build(new ResourceLocation(Main.MOD_ID, "fire_salamander_entity").toString()));
    public static final RegistryObject<EntityType<HornedViperEntity>> HORNED_VIPER_ENTITY = ENTITY_TYPES.register("horned_viper_entity", () -> EntityType.Builder.<HornedViperEntity>create(HornedViperEntity::new, EntityClassification.CREATURE).size(0.8f, 0.2f).build(new ResourceLocation(Main.MOD_ID, "horned_viper_entity").toString()));
    public static final RegistryObject<EntityType<HumpheadParrotfishEntity>> HUMPHEAD_PARROTFISH = ENTITY_TYPES.register("humphead_parrotfish", () -> EntityType.Builder.<HumpheadParrotfishEntity>create(HumpheadParrotfishEntity::new, EntityClassification.WATER_CREATURE).size(0.9f, 0.7f).build(new ResourceLocation(Main.MOD_ID, "humphead_parrotfish").toString()));
}
