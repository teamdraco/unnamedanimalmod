package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.PacmanFrogModel;
import mod.coda.unnamedanimalmod.entity.PacmanFrogEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PacmanFrogRenderer extends MobRenderer<PacmanFrogEntity, PacmanFrogModel<PacmanFrogEntity>> {
    protected static final ResourceLocation ADULT = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/pacman_frog/adult.png");
    protected static final ResourceLocation CHILD = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/pacman_frog/child.png");
    private final PacmanFrogModel adult;
    private final PacmanFrogModel child;

    public PacmanFrogRenderer(EntityRendererManager manager) {
        super(manager, new PacmanFrogModel.Adult(), 0.2f);
        adult = entityModel;
        child = new PacmanFrogModel.Child();
    }

    @Override
    public void render(PacmanFrogEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        entityModel = entity.isChild() ? child : adult;
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(PacmanFrogEntity entity) {
        if (entity.isChild()) {
            return CHILD;
        }
        else {
            return ADULT;
        }
    }

    protected void applyRotations(PacmanFrogEntity entity, MatrixStack matrix, float var1, float var2, float var3) {
        super.applyRotations(entity, matrix, var1, var2, var3);
        if (entity.isChild()) {
            float rotate = 4.3F * MathHelper.sin(0.6F * var1);
            matrix.rotate(Vector3f.YP.rotationDegrees(rotate));
            if (!entity.isInWater()) {
                matrix.translate(0.0, 0.10000000149011612D, 0.0D);
                matrix.rotate(Vector3f.ZP.rotationDegrees(90.0F));
            }
        }
        if (!entity.isChild()) {
            matrix.translate(-0.03,0, 0);
        }
        if (entity.isHidden()) {
            matrix.translate(0, -0.35, 0);
            matrix.rotate(Vector3f.XP.rotationDegrees(35));
        }
    }
}