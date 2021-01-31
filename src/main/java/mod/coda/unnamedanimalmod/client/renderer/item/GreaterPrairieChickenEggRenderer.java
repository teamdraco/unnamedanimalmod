package mod.coda.unnamedanimalmod.client.renderer.item;

import mod.coda.unnamedanimalmod.entity.item.GreaterPrairieChickenEggEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class GreaterPrairieChickenEggRenderer extends SpriteRenderer<GreaterPrairieChickenEggEntity> {

    public GreaterPrairieChickenEggRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}
