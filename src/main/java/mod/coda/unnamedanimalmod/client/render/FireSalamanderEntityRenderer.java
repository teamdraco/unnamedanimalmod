package mod.coda.unnamedanimalmod.client.render;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.FireSalamanderEntityModel;
import mod.coda.unnamedanimalmod.entity.FireSalamanderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FireSalamanderEntityRenderer extends MobRenderer<FireSalamanderEntity, FireSalamanderEntityModel<FireSalamanderEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/fire_salamander.png");

    public FireSalamanderEntityRenderer(EntityRendererManager manager) {
        super(manager, new FireSalamanderEntityModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(FireSalamanderEntity entity) {
        return TEXTURE;
    }
}
