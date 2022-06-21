package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.TomatoFrogModel;
import teamdraco.unnamedanimalmod.common.entity.TomatoFrogEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TomatoFrogRenderer extends MobRenderer<TomatoFrogEntity, TomatoFrogModel<TomatoFrogEntity>> {
    protected static final ResourceLocation ADULT = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/tomato_frog/adult.png");
    protected static final ResourceLocation CHILD = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/tomato_frog/child.png");
    private final TomatoFrogModel adult;
    private final TomatoFrogModel child;

    public TomatoFrogRenderer(EntityRendererProvider.Context manager) {
        super(manager, new TomatoFrogModel.Adult(), 0.2f);
        adult = model;
        child = new TomatoFrogModel.Child();
    }

    @Override
    public void render(TomatoFrogEntity entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        model = entity.isBaby() ? child : adult;
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(TomatoFrogEntity entity) {
        if (entity.isBaby()) {
            return CHILD;
        } else {
            return ADULT;
        }
    }

    protected void setupRotations(TomatoFrogEntity entity, PoseStack matrix, float var1, float var2, float var3) {
        super.setupRotations(entity, matrix, var1, var2, var3);
        if(entity.isBaby()) {
            float rotate = 4.3F * Mth.sin(0.6F * var1);
            matrix.mulPose(Vector3f.YP.rotationDegrees(rotate));
            if (!entity.isInWater()) {
                matrix.translate(0.0, 0.10000000149011612D, 0.0D);
                matrix.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            if (entity.isBaby()) {
                matrix.translate(0.0, 0.0, 0.15);
            }
        }
    }
}
