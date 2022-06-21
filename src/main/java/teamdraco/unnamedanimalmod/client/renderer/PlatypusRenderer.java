package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.PlatypusModel;
import teamdraco.unnamedanimalmod.common.entity.PlatypusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlatypusRenderer extends MobRenderer<PlatypusEntity, PlatypusModel<PlatypusEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/platypus/platypus.png");
    protected static final ResourceLocation AGENT_P = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/platypus/agent_p.png");
    protected static final ResourceLocation PERRY = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/platypus/perry.png");

    public PlatypusRenderer(EntityRendererProvider.Context manager) {
        super(manager, new PlatypusModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(PlatypusEntity entity) {
        if (entity.getName().getString().equals("Agent P")) {
            return AGENT_P;
        }
        else if (entity.getName().getString().equals("Perry") || entity.getName().getString().equals("Perry the Platypus")) {
            return PERRY;
        }
        else {
            return TEXTURE;
        }
    }

    @Override
    protected void setupRotations(PlatypusEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        //matrixStackIn.translate((double)0.0F, (double)-1.1F, (double)0.0F);
    }
}
