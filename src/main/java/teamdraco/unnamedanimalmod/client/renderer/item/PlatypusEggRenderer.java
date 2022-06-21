package teamdraco.unnamedanimalmod.client.renderer.item;

import teamdraco.unnamedanimalmod.common.entity.item.PlatypusEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlatypusEggRenderer extends ThrownItemRenderer<PlatypusEggEntity> {

    public PlatypusEggRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}
