package mod.coda.unnamedanimalmod.client.renderer;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.VineSnakeModel;
import mod.coda.unnamedanimalmod.entity.VineSnakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VineSnakeRenderer extends MobRenderer<VineSnakeEntity, VineSnakeModel<VineSnakeEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/vine_snake.png");

    public VineSnakeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VineSnakeModel<>(), 0.25F);
    }

    public ResourceLocation getEntityTexture(VineSnakeEntity entity) {
        return TEXTURE;
    }
}