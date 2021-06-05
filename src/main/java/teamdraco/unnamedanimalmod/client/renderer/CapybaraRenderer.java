package teamdraco.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.CapybaraModel;
import teamdraco.unnamedanimalmod.common.entity.CapybaraEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CapybaraRenderer extends MobRenderer<CapybaraEntity, CapybaraModel<CapybaraEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/capybara.png");
    private static final ResourceLocation SINGLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/single_chest.png");
    private static final ResourceLocation DOUBLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/double_chest.png");
    private static final ResourceLocation MARIO = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/mario.png");
    private static final ResourceLocation MARIO_SINGLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/mario_single_chest.png");
    private static final ResourceLocation MARIO_DOUBLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/mario_double_chest.png");

    public CapybaraRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CapybaraModel<>(), 0.5F);
    }

    public ResourceLocation getTextureLocation(CapybaraEntity entity) {
        if (entity.inventory != null && entity.inventory.getContainerSize() / 27 == 1 && !entity.getName().getString().equals("Mario")) {
            return SINGLE_CHEST;
        }
        else if (entity.inventory != null && entity.inventory.getContainerSize() / 27 == 2 && !entity.getName().getString().equals("Mario")) {
            return DOUBLE_CHEST;
        }
        else if (entity.inventory != null && entity.inventory.getContainerSize() / 27 == 1 && entity.getName().getString().equals("Mario")) {
            return MARIO_SINGLE_CHEST;
        }
        else if (entity.inventory != null && entity.inventory.getContainerSize() / 27 == 2 && entity.getName().getString().equals("Mario")) {
            return MARIO_DOUBLE_CHEST;
        }
        else if (entity.getName().getString().equals("Mario")) {
            return MARIO;
        }
        return TEXTURE;
    }

    @Override
    protected void setupRotations(CapybaraEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.scale(0.77f, 0.77f, 0.77f);
        if (entityLiving.isInWater() && !entityLiving.isBaby()) {
            matrixStackIn.translate(0, -0.4, 0);
        }
    }
}