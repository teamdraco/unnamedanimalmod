package teamdraco.unnamedanimalmod.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.MarineIguanaModel;
import teamdraco.unnamedanimalmod.common.entity.MarineIguanaEntity;

@OnlyIn(Dist.CLIENT)
public class MarineIguanaGlowLayer<T extends MarineIguanaEntity, M extends MarineIguanaModel<T>>  extends RenderLayer<T, M> {
    private static final RenderType TEXTURE = RenderType.eyes(new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/marine_iguana/marine_iguana_e.png"));

    public MarineIguanaGlowLayer(RenderLayerParent<T, M> rendererIn) {
        super(rendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.getRenderType());
        if (entitylivingbaseIn.getVariant() == 4) {
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 2000, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0F);
        }
    }

    public RenderType getRenderType() {
        return TEXTURE;
    }
}
