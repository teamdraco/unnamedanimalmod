package mod.coda.unnamedanimalmod.client.render;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.MuskOxEntityModel;
import mod.coda.unnamedanimalmod.entity.MuskOxEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MuskOxEntityRenderer extends MobRenderer<MuskOxEntity, MuskOxEntityModel<MuskOxEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/musk_ox.png");

    public MuskOxEntityRenderer(EntityRendererManager manager) {
        super(manager, new MuskOxEntityModel<>(), 0.8f);
    }

    @Override
    public ResourceLocation getEntityTexture(MuskOxEntity entity) {
        return TEXTURE;
    }
}
