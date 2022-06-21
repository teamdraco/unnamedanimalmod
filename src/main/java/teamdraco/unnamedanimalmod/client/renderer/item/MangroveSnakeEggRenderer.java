package teamdraco.unnamedanimalmod.client.renderer.item;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.item.MangroveSnakeEggEntity;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeEggRenderer extends ThrownItemRenderer<MangroveSnakeEggEntity> {
    public MangroveSnakeEggRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}

