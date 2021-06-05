package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.HumpheadParrotfishModel;
import teamdraco.unnamedanimalmod.common.entity.HumpheadParrotfishEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HumpheadParrotfishRenderer extends MobRenderer<HumpheadParrotfishEntity, HumpheadParrotfishModel<HumpheadParrotfishEntity>> {
    private static final ResourceLocation ADULT = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/humphead_parrotfish/adult.png");
    private static final ResourceLocation CHILD = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/humphead_parrotfish/child.png");
    private final HumpheadParrotfishModel adult;
    private final HumpheadParrotfishModel child;

    public HumpheadParrotfishRenderer(EntityRendererManager manager) {
        super(manager, new HumpheadParrotfishModel.Adult(), 0.5f);
        adult = model;
        child = new HumpheadParrotfishModel.Child();
    }

    @Override
    public void render(HumpheadParrotfishEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        model = entity.isBaby() ? child : adult;
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getTextureLocation(HumpheadParrotfishEntity entity) {
        if (entity.isBaby()) {
            return CHILD;
        } else {
            return ADULT;
        }
    }

    protected void setupRotations(HumpheadParrotfishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
        if (!entityLiving.isInWater()) {
            matrixStackIn.translate((double)0.1F, (double)0.1F, (double)-0.1F);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }

    }
}