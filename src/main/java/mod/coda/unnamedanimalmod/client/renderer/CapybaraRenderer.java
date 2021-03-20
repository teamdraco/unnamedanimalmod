package mod.coda.unnamedanimalmod.client.renderer;

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
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/capybara.png");

    public CapybaraRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CapybaraModel<>(), 0.85F);
    }

    public ResourceLocation getEntityTexture(CapybaraEntity entity) {
        return TEXTURE;
    }
}