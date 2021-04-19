package mod.coda.unnamedanimalmod.client.renderer;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.BananaSlugModel;
import mod.coda.unnamedanimalmod.entity.BananaSlugEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BananaSlugRenderer extends MobRenderer<BananaSlugEntity, BananaSlugModel<BananaSlugEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/banana_slug.png");

    public BananaSlugRenderer(EntityRendererManager manager) {
        super(manager, new BananaSlugModel<>(), 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(BananaSlugEntity entity) {
        return TEXTURE;
    }
}
