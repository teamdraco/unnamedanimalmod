package teamdraco.unnamedanimalmod.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.BlackDiamondStingrayModel;
import teamdraco.unnamedanimalmod.common.entity.BlackDiamondStingrayEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlackDiamondStingrayRenderer extends MobRenderer<BlackDiamondStingrayEntity, BlackDiamondStingrayModel<BlackDiamondStingrayEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/black_diamond_stingray.png");

    public BlackDiamondStingrayRenderer(EntityRendererProvider.Context manager) {
        super(manager, new BlackDiamondStingrayModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackDiamondStingrayEntity entity) {
        return TEXTURE;
    }
}
