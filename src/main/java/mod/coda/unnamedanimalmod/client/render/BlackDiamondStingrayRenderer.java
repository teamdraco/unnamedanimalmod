package mod.coda.unnamedanimalmod.client.render;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.BlackDiamondStingrayEntityModel;
import mod.coda.unnamedanimalmod.entity.BlackDiamondStingrayEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BlackDiamondStingrayRenderer extends MobRenderer<BlackDiamondStingrayEntity, BlackDiamondStingrayEntityModel<BlackDiamondStingrayEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/black_diamond_stingray.png");

    public BlackDiamondStingrayRenderer(EntityRendererManager manager) {
        super(manager, new BlackDiamondStingrayEntityModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(BlackDiamondStingrayEntity entity) {
        return TEXTURE;
    }
}
