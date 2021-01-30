package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.HonduranWhiteBatModel;
import mod.coda.unnamedanimalmod.client.model.TomatoFrogModel;
import mod.coda.unnamedanimalmod.entity.HonduranWhiteBatEntity;
import mod.coda.unnamedanimalmod.entity.TomatoFrogEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TomatoFrogRenderer extends MobRenderer<TomatoFrogEntity, TomatoFrogModel<TomatoFrogEntity>> {
    protected static final ResourceLocation ADULT = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/tomato_frog.png");
    protected static final ResourceLocation CHILD = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/tomato_frog_tadpole.png");
    private final TomatoFrogModel adult;
    private final TomatoFrogModel child;

    public TomatoFrogRenderer(EntityRendererManager manager) {
        super(manager, new TomatoFrogModel.Adult(), 0.2f);
        adult = entityModel;
        child = new TomatoFrogModel.Child();
    }

    @Override
    public void render(TomatoFrogEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        entityModel = entity.isChild() ? child : adult;
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(TomatoFrogEntity entity) {
        if (entity.isChild()) {
            return CHILD;
        } else {
            return ADULT;
        }
    }

    protected void applyRotations(TomatoFrogEntity entity, MatrixStack matrix, float var1, float var2, float var3) {
        super.applyRotations(entity, matrix, var1, var2, var3);
        if(entity.isChild()) {
            float rotate = 4.3F * MathHelper.sin(0.6F * var1);
            matrix.rotate(Vector3f.YP.rotationDegrees(rotate));
            if (!entity.isInWater()) {
                matrix.translate(0.0, 0.10000000149011612D, 0.0D);
                matrix.rotate(Vector3f.ZP.rotationDegrees(90.0F));
            }
        }
    }
}