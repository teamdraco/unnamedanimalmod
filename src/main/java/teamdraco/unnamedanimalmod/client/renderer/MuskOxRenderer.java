package teamdraco.unnamedanimalmod.client.renderer;

import teamdraco.unnamedanimalmod.UnnamedAnimalMod;
import teamdraco.unnamedanimalmod.client.model.MuskOxModel;
import teamdraco.unnamedanimalmod.common.entity.MuskOxEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MuskOxRenderer extends MobRenderer<MuskOxEntity, MuskOxModel<MuskOxEntity>> {
    protected static final ResourceLocation ADULT = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/musk_ox/adult.png");
    protected static final ResourceLocation CHILD = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/musk_ox/child.png");

    public MuskOxRenderer(EntityRendererProvider.Context manager) {
        super(manager, new MuskOxModel<>(), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(MuskOxEntity entity) {
        return entity.isBaby() ? CHILD : ADULT;
    }
}
