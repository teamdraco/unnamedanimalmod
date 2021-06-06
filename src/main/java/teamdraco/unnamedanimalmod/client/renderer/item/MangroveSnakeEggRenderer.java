package teamdraco.unnamedanimalmod.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.item.MangroveSnakeEggEntity;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeEggRenderer extends SpriteRenderer<MangroveSnakeEggEntity> {

    public MangroveSnakeEggRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}

