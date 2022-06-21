package teamdraco.unnamedanimalmod.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.CapybaraModel;
import teamdraco.unnamedanimalmod.common.entity.CapybaraEntity;

public class CapybaraChestLayer extends RenderLayer<CapybaraEntity, CapybaraModel> {
    private static final ResourceLocation SINGLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/single_chest.png");
    private static final ResourceLocation DOUBLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/double_chest.png");

    public CapybaraChestLayer(MobRenderer<CapybaraEntity, CapybaraModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource bufferProvider, int packedLightIn, CapybaraEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch) {
        final int chestCount = entity.getChestCount();
        if (chestCount > 0) {
            CapybaraModel model = getParentModel();
            model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch);
            model.renderToBuffer(stack, bufferProvider.getBuffer(RenderType.entityCutoutNoCull(chestCount > 1 ? DOUBLE_CHEST : SINGLE_CHEST)), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
