package mod.coda.unnamedanimalmod.client.renderer;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.BlackDiamondStingrayModel;
import mod.coda.unnamedanimalmod.entity.BlackDiamondStingrayEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BlackDiamondStingrayRenderer extends MobRenderer<BlackDiamondStingrayEntity, BlackDiamondStingrayModel<BlackDiamondStingrayEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/black_diamond_stingray.png");

    public BlackDiamondStingrayRenderer(EntityRendererManager manager) {
        super(manager, new BlackDiamondStingrayModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(BlackDiamondStingrayEntity entity) {
        return TEXTURE;
    }
}
