package teamdraco.unnamedanimalmod.client.renderer.item;

import teamdraco.unnamedanimalmod.common.entity.item.PlatypusEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlatypusEggRenderer extends SpriteRenderer<PlatypusEggEntity> {

    public PlatypusEggRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}
