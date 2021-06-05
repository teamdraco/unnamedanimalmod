package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.FlashlightFishModel;
import teamdraco.unnamedanimalmod.common.entity.FlashlightFishEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlashlightFishRenderer extends MobRenderer<FlashlightFishEntity, FlashlightFishModel<FlashlightFishEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/flashlight_fish/flashlight_fish.png");

    public FlashlightFishRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FlashlightFishModel<>(), 0.1F);
        this.addLayer(new FlashlightFishGlowLayer(this));
    }

    public ResourceLocation getTextureLocation(FlashlightFishEntity entity) {
        return TEXTURE;
    }

    protected void setupRotations(FlashlightFishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate((double)0.1F, (double)0.1F, (double)-0.1F);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }

    }
}