package mod.coda.unnamedanimalmod;

import mod.coda.unnamedanimalmod.client.render.FireSalamanderEntityRenderer;
import mod.coda.unnamedanimalmod.client.render.HornedViperEntityRenderer;
import mod.coda.unnamedanimalmod.client.render.HumpheadParrotfishEntityRenderer;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FIRE_SALAMANDER_ENTITY.get(), FireSalamanderEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HORNED_VIPER_ENTITY.get(), HornedViperEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.HUMPHEAD_PARROTFISH.get(), HumpheadParrotfishEntityRenderer::new);
    }
}
