package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.UAMModelLayers;
import teamdraco.unnamedanimalmod.client.model.CapybaraModel;
import teamdraco.unnamedanimalmod.client.renderer.layer.CapybaraChestLayer;
import teamdraco.unnamedanimalmod.common.entity.CapybaraEntity;

@OnlyIn(Dist.CLIENT)
public class CapybaraRenderer extends MobRenderer<CapybaraEntity, CapybaraModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/capybara.png");
    private static final ResourceLocation MARIO = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/mario.png");

    public CapybaraRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CapybaraModel(renderManagerIn.bakeLayer(UAMModelLayers.CAPYBARA)), 0.5F);
        addLayer(new CapybaraChestLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CapybaraEntity entity) {
        if (entity.getName().getString().equals("Mario")) {
            return MARIO;
        }
        return TEXTURE;
    }

    @Override
    protected void setupRotations(CapybaraEntity entityLiving, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, poseStack, ageInTicks, rotationYaw, partialTicks);
        poseStack.scale(0.77f, 0.77f, 0.77f);
        if (entityLiving.isInWater() && !entityLiving.isBaby()) {
            poseStack.translate(0, -0.625, 0);
        }
    }
}
