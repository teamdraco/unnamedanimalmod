package mod.coda.unnamedanimalmod.client.renderer;

import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.GreaterPrairieChickenModel;
import mod.coda.unnamedanimalmod.entity.GreaterPrairieChickenEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GreaterPrairieChickenRenderer extends MobRenderer<GreaterPrairieChickenEntity, GreaterPrairieChickenModel<GreaterPrairieChickenEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/greater_prairie_chicken.png");

    public GreaterPrairieChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GreaterPrairieChickenModel<>(), 0.45F);
    }

    public ResourceLocation getEntityTexture(GreaterPrairieChickenEntity entity) {
        return TEXTURE;
    }

    protected float handleRotationFloat(GreaterPrairieChickenEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}