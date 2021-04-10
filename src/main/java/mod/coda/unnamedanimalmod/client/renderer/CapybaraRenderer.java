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
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/capybara.png");
    private static final ResourceLocation SINGLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/single_chest.png");
    private static final ResourceLocation DOUBLE_CHEST = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara/double_chest.png");

    public CapybaraRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CapybaraModel<>(), 0.5F);
    }

    public ResourceLocation getEntityTexture(CapybaraEntity entity) {
        if (entity.inventory != null && entity.inventory.getSizeInventory() / 27 == 1) {
            return SINGLE_CHEST;
        }
        else if (entity.inventory != null && entity.inventory.getSizeInventory() / 27 == 2) {
            return DOUBLE_CHEST;
        }
        return TEXTURE;
    }

    @Override
    protected void applyRotations(CapybaraEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        matrixStackIn.scale(0.77f, 0.77f, 0.77f);
    }
}