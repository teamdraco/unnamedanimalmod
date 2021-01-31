package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.HonduranWhiteBatModel;
import mod.coda.unnamedanimalmod.entity.HonduranWhiteBatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HonduranWhiteBatRenderer extends MobRenderer<HonduranWhiteBatEntity, HonduranWhiteBatModel<HonduranWhiteBatEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/honduran_white_bat.png");

    public HonduranWhiteBatRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HonduranWhiteBatModel(), 0.25F);
    }

    public ResourceLocation getEntityTexture(HonduranWhiteBatEntity entity) {
        return TEXTURE;
    }

    protected void applyRotations(HonduranWhiteBatEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.getIsBatHanging() && !entityLiving.isChild()) {
            matrixStackIn.translate(0.0D, (double)0.05F, 0.0D);
        }
        else if (entityLiving.getIsBatHanging() && entityLiving.isChild()) {
            matrixStackIn.translate(0.0D, (double)-1.0F, 0.0D);
        }

        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}