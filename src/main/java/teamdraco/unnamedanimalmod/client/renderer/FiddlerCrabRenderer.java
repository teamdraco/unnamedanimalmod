package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.BananaSlugModel;
import teamdraco.unnamedanimalmod.client.model.FiddlerCrabModel;
import teamdraco.unnamedanimalmod.common.entity.BananaSlugEntity;
import teamdraco.unnamedanimalmod.common.entity.FiddlerCrabEntity;

@OnlyIn(Dist.CLIENT)
public class FiddlerCrabRenderer extends MobRenderer<FiddlerCrabEntity, FiddlerCrabModel<FiddlerCrabEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/fiddler_crab/body_0.png");

    public FiddlerCrabRenderer(EntityRendererManager manager) {
        super(manager, new FiddlerCrabModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(FiddlerCrabEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(FiddlerCrabEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(90));
    }
}
