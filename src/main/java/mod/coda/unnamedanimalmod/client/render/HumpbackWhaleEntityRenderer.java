package mod.coda.unnamedanimalmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.HumpbackWhaleEntityModel;
import mod.coda.unnamedanimalmod.entity.HumpbackWhaleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HumpbackWhaleEntityRenderer extends MobRenderer<HumpbackWhaleEntity, HumpbackWhaleEntityModel<HumpbackWhaleEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/humpback_whale.png");

    public HumpbackWhaleEntityRenderer(EntityRendererManager manager) {
        super(manager, new HumpbackWhaleEntityModel<>(), 3.0f);
    }

    @Override
    public ResourceLocation getEntityTexture(HumpbackWhaleEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(HumpbackWhaleEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
        matrixStackIn.scale(1.5f, 1.5f, 1.5f);
        matrixStackIn.translate(0.0f, 0.2f, 0.0f);
    }
}
