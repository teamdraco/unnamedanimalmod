package mod.coda.unnamedanimalmod.client.renderer.item;

import mod.coda.unnamedanimalmod.entity.item.MarineIguanaEggEntity;
import mod.coda.unnamedanimalmod.entity.item.PlatypusEggEntity;
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
