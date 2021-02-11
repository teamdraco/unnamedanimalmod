package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.FlashlightFishModel;
import mod.coda.unnamedanimalmod.entity.FlashlightFishEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlashlightFishGlowLayer<T extends FlashlightFishEntity, M extends FlashlightFishModel<T>> extends LayerRenderer<T, M> {
    private static final RenderType TEXTURE = RenderType.getEyes(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/flashlight_fish/flashlight_fish_e.png"));

    public FlashlightFishGlowLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.getRenderType());
        if (entitylivingbaseIn.world.getLight(entitylivingbaseIn.getPosition()) < 11) {
            this.getEntityModel().render(matrixStackIn, ivertexbuilder,  2000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public RenderType getRenderType() {
        return TEXTURE;
    }
}
