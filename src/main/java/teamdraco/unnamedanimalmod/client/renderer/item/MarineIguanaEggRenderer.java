package teamdraco.unnamedanimalmod.client.renderer.item;

import teamdraco.unnamedanimalmod.common.entity.item.MarineIguanaEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarineIguanaEggRenderer extends SpriteRenderer<MarineIguanaEggEntity> {

    public MarineIguanaEggRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}
