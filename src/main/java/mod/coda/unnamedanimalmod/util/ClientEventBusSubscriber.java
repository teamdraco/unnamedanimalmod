package mod.coda.unnamedanimalmod.util;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.render.*;
import mod.coda.unnamedanimalmod.entity.PigNosedTurtleEntity;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FIRE_SALAMANDER, FireSalamanderEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HORNED_VIPER, HornedViperEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HUMPHEAD_PARROTFISH, HumpheadParrotfishEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PIG_NOSED_TURTLE, PigNosedTurtleEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MUSK_OX, MuskOxEntityRenderer::new);
    }
}
