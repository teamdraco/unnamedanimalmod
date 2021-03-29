package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.CapybaraModel;
import mod.coda.unnamedanimalmod.entity.CapybaraEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapybaraRenderer extends MobRenderer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara.png");

    public CapybaraRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CapybaraModel<>(), 0.5F);
    }

    public ResourceLocation getEntityTexture(CapybaraEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void applyRotations(CapybaraEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.scale(0.77f, 0.77f, 0.77f);
    }
}