package teamdraco.unnamedanimalmod.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.renderer.*;
import teamdraco.unnamedanimalmod.client.renderer.item.GreaterPrairieChickenEggRenderer;
import teamdraco.unnamedanimalmod.client.renderer.item.MangroveSnakeEggRenderer;
import teamdraco.unnamedanimalmod.client.renderer.item.MarineIguanaEggRenderer;
import teamdraco.unnamedanimalmod.client.renderer.item.PlatypusEggRenderer;
import teamdraco.unnamedanimalmod.common.block.SaltPowderBlock;
import teamdraco.unnamedanimalmod.common.item.UAMSpawnEggItem;
import teamdraco.unnamedanimalmod.common.item.UAMWaterBucketItem;
import teamdraco.unnamedanimalmod.init.UAMBlocks;
import teamdraco.unnamedanimalmod.init.UAMEntities;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = UnnamedAnimalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UAMEntities.BLACK_DIAMOND_STINGRAY.get(), BlackDiamondStingrayRenderer::new);
        event.registerEntityRenderer(UAMEntities.TOMATO_FROG.get(), TomatoFrogRenderer::new);
        event.registerEntityRenderer(UAMEntities.SOUTHERN_RIGHT_WHALE.get(), SouthernRightWhaleRenderer::new);
        event.registerEntityRenderer(UAMEntities.GREATER_PRAIRIE_CHICKEN.get(), GreaterPrairieChickenRenderer::new);
        event.registerEntityRenderer(UAMEntities.GREATER_PRAIRIE_CHICKEN_EGG.get(), GreaterPrairieChickenEggRenderer::new);
        event.registerEntityRenderer(UAMEntities.FLASHLIGHT_FISH.get(), FlashlightFishRenderer::new);
        event.registerEntityRenderer(UAMEntities.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishRenderer::new);
        event.registerEntityRenderer(UAMEntities.MUSK_OX.get(), MuskOxRenderer::new);
        event.registerEntityRenderer(UAMEntities.BANANA_SLUG.get(), BananaSlugRenderer::new);
        event.registerEntityRenderer(UAMEntities.MARINE_IGUANA.get(), MarineIguanaRenderer::new);
        event.registerEntityRenderer(UAMEntities.PLATYPUS.get(), PlatypusRenderer::new);
        event.registerEntityRenderer(UAMEntities.PLATYPUS_EGG.get(), PlatypusEggRenderer::new);
        event.registerEntityRenderer(UAMEntities.MARINE_IGUANA_EGG.get(), MarineIguanaEggRenderer::new);
        event.registerEntityRenderer(UAMEntities.ELEPHANTNOSE_FISH.get(), ElephantnoseFishRenderer::new);
        event.registerEntityRenderer(UAMEntities.PACMAN_FROG.get(), PacmanFrogRenderer::new);
        event.registerEntityRenderer(UAMEntities.CAPYBARA.get(), CapybaraRenderer::new);
        event.registerEntityRenderer(UAMEntities.ROCKET_KILLIFISH.get(), RocketKillifishRenderer::new);
        event.registerEntityRenderer(UAMEntities.MANGROVE_SNAKE.get(), MangroveSnakeRenderer::new);
        event.registerEntityRenderer(UAMEntities.MANGROVE_BOAT.get(), MangroveBoatRenderer::new);
        // event.registerEntityRenderer(UAMEntities.BLUBBER_JELLY.get(), BlubberJellyRenderer::new);
        event.registerEntityRenderer(UAMEntities.FIDDLER_CRAB.get(), FiddlerCrabRenderer::new);
        event.registerEntityRenderer(UAMEntities.MANGROVE_SNAKE_EGG.get(), MangroveSnakeEggRenderer::new);
        event.registerEntityRenderer(UAMEntities.LEAFY_SEADRAGON.get(), LeafySeadragonRenderer::new);
        event.registerEntityRenderer(UAMEntities.SPOTTED_GARDEN_EEL.get(), SpottedGardenEelRenderer::new);
        event.registerEntityRenderer(UAMEntities.MUDSKIPPER.get(), MudskipperRenderer::new);
    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        ItemColor eggColor = (stack, tintIndex) -> ((UAMSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (UAMSpawnEggItem e : UAMSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        Set<RegistryObject<Block>> blocks = new HashSet<>(UAMBlocks.BLOCKS.getEntries());

        blocks.stream().filter(b -> {
            final Block block = b.get();
            return block instanceof BushBlock || block instanceof LeavesBlock || block instanceof TrapDoorBlock || block instanceof DoorBlock || block instanceof SaltPowderBlock;
        }).forEach(ClientEvents::setCutout);
    }

    public static void setCutout(RegistryObject<Block> b) {
        ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event) {
        BlockColors handler = event.getBlockColors();
        handler.register((p_228059_0_, p_228059_1_, p_228059_2_, p_228059_3_) -> SaltPowderBlock.getColor(), UAMBlocks.SALT.get());
    }

    // avert your eyes for whatever this is...
    public static void registerBucketVariant(UAMWaterBucketItem item) {
        ItemProperties.register(item, new ResourceLocation(UnnamedAnimalMod.MOD_ID, "variant"),
                (stack, world, player, x) -> stack.hasTag() ? stack.getTag().getInt("Variant") : 0);
    }

    public static Consumer<UAMWaterBucketItem> registerBucketVariantCallable() {
        return ClientEvents::registerBucketVariant;
    }
}
