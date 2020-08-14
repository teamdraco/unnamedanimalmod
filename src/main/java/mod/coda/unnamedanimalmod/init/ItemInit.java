package mod.coda.unnamedanimalmod.init;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.items.ModEggItem;
import mod.coda.unnamedanimalmod.items.ModPotItem;
import mod.coda.unnamedanimalmod.items.ModPungiItem;
import mod.coda.unnamedanimalmod.items.ModSnakeItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> FIRE_SALAMANDER_POT = ITEMS.register("fire_salamander_pot", () -> new ModPotItem(ModEntityTypes.HORNED_VIPER, new Item.Properties().group(Main.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> HORNED_VIPER_ITEM = ITEMS.register("horned_viper_item", () -> new ModSnakeItem(ModEntityTypes.FIRE_SALAMANDER, new Item.Properties().group(Main.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> PUNGI = ITEMS.register("pungi", () -> new ModPungiItem(new Item.Properties().group(Main.GROUP).maxStackSize(1).maxDamage(3)));
    public static final RegistryObject<Item> PIG_NOSED_TURTLE_BUCKET = ITEMS.register("pig_nosed_turtle_bucket", () -> new FishBucketItem(ModEntityTypes.PIG_NOSED_TURTLE, Fluids.WATER, new Item.Properties().group(Main.GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> FROG_LEGS = ITEMS.register("frog_legs", () -> new Item(new Item.Properties().group(Main.GROUP).food(new Food.Builder().hunger(2).saturation(0.4f).build())));
    public static final RegistryObject<Item> COOKED_FROG_LEGS = ITEMS.register("cooked_frog_legs", () -> new Item(new Item.Properties().group(Main.GROUP).food(new Food.Builder().hunger(5).saturation(0.8f).build())));
    public static final RegistryObject<Item> MUSK_OX_MEAT = ITEMS.register("musk_ox_meat", () -> new Item(new Item.Properties().group(Main.GROUP).food(new Food.Builder().hunger(4).saturation(0.2f).effect(new EffectInstance(Effects.SLOWNESS, 100, 0), 0.75f).build())));
    public static final RegistryObject<Item> COOKED_MUSK_OX_MEAT = ITEMS.register("cooked_musk_ox_meat", () -> new Item(new Item.Properties().group(Main.GROUP).food(new Food.Builder().hunger(10).saturation(0.6f).effect(new EffectInstance(Effects.SLOWNESS, 100, 0), 0.25f).meat().build())));
    public static final RegistryObject<Item> PACMAN_FROG_EGG = ITEMS.register("pacman_frog_egg", () -> new ModEggItem(new Item.Properties().group(Main.GROUP).maxStackSize(16)));
}
