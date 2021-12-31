package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.FlashlightFishModel;
import teamdraco.unnamedanimalmod.client.renderer.layer.FlashlightFishGlowLayer;
import teamdraco.unnamedanimalmod.common.entity.FlashlightFishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlashlightFishRenderer extends MobRenderer<FlashlightFishEntity, FlashlightFishModel<FlashlightFishEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/flashlight_fish/flashlight_fish.png");

    public FlashlightFishRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new FlashlightFishModel<>(), 0.1F);
        this.addLayer(new FlashlightFishGlowLayer(this));
    }

    public ResourceLocation getTextureLocation(FlashlightFishEntity entity) {
        return TEXTURE;
    }

    protected void setupRotations(FlashlightFishEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * Mth.sin(0.6F * ageInTicks);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate((double)0.1F, (double)0.1F, (double)-0.1F);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }

    }
}
