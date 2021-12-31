package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.SpottedGardenEelHidingModel;
import teamdraco.unnamedanimalmod.client.model.SpottedGardenEelModel;
import teamdraco.unnamedanimalmod.common.entity.SpottedGardenEelEntity;

public class SpottedGardenEelRenderer extends MobRenderer<SpottedGardenEelEntity, EntityModel<SpottedGardenEelEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/spotted_garden_eel.png");
    private static final SpottedGardenEelModel MODEL = new SpottedGardenEelModel();
    private static final SpottedGardenEelHidingModel HIDING_MODEL = new SpottedGardenEelHidingModel();

    public SpottedGardenEelRenderer(EntityRendererProvider.Context p_i48864_1_) {
        super(p_i48864_1_, MODEL, 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpottedGardenEelEntity p_110775_1_) {
        return TEXTURE;
    }

    @Override
    public void render(SpottedGardenEelEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityIn.isHidden()) {
            model = HIDING_MODEL;
        }
        else {
            model = MODEL;
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
