package mod.coda.unnamedanimalmod.client.render;

import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.PigNosedTurtleEntityModel;
import mod.coda.unnamedanimalmod.entity.PigNosedTurtleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PigNosedTurtleEntityRenderer extends MobRenderer<PigNosedTurtleEntity, PigNosedTurtleEntityModel<PigNosedTurtleEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/pig_nosed_turtle.png");

    public PigNosedTurtleEntityRenderer(EntityRendererManager manager) {
        super(manager, new PigNosedTurtleEntityModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(PigNosedTurtleEntity entity) {
        return TEXTURE;
    }
}
