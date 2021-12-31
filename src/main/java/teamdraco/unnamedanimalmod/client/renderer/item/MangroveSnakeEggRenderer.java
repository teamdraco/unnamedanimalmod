package teamdraco.unnamedanimalmod.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.item.MangroveSnakeEggEntity;

@OnlyIn(Dist.CLIENT)
public class MangroveSnakeEggRenderer extends SpriteRe<MangroveSnakeEggEntity> {

    public MangroveSnakeEggRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}

