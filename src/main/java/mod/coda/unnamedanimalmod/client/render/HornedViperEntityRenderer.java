package mod.coda.unnamedanimalmod.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import javafx.geometry.Side;
import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.HornedViperEntityModel;
import mod.coda.unnamedanimalmod.entity.HornedViperEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HornedViperEntityRenderer extends MobRenderer<HornedViperEntity, HornedViperEntityModel<HornedViperEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/horned_viper.png");

    public HornedViperEntityRenderer(EntityRendererManager manager) {
        super(manager, new HornedViperEntityModel<>(), 0.45f);
    }

    @Override
    public ResourceLocation getEntityTexture(HornedViperEntity entity) {
        return TEXTURE;
    }
}
