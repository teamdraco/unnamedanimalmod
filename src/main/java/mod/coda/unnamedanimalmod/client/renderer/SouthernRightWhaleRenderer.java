package mod.coda.unnamedanimalmod.client.renderer;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.SouthernRightWhaleModel;
import mod.coda.unnamedanimalmod.entity.SouthernRightWhaleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SouthernRightWhaleRenderer extends MobRenderer<SouthernRightWhaleEntity, SouthernRightWhaleModel<SouthernRightWhaleEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/southern_right_whale.png");

    public SouthernRightWhaleRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SouthernRightWhaleModel<>(), 2.25F);
    }

    public ResourceLocation getEntityTexture(SouthernRightWhaleEntity entity) {
        return TEXTURE;
    }
}