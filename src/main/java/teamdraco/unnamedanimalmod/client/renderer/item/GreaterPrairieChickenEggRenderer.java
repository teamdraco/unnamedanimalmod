package teamdraco.unnamedanimalmod.client.renderer.item;

import teamdraco.unnamedanimalmod.common.entity.item.GreaterPrairieChickenEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GreaterPrairieChickenEggRenderer extends ThrownItemRenderer<GreaterPrairieChickenEggEntity> {

    public GreaterPrairieChickenEggRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }
}
