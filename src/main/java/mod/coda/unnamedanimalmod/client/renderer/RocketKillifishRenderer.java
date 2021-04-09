package mod.coda.unnamedanimalmod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.UnnamedAnimalMod;
import mod.coda.unnamedanimalmod.client.model.RocketKillifishModel;
import mod.coda.unnamedanimalmod.entity.RocketKillifishEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RocketKillifishRenderer extends MobRenderer<RocketKillifishEntity, RocketKillifishModel<RocketKillifishEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(UnnamedAnimalMod.MOD_ID, "textures/entity/rocket_killifish.png");

    public RocketKillifishRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RocketKillifishModel<>(), 0.1F);
    }

    public ResourceLocation getEntityTexture(RocketKillifishEntity entity) {
        return TEXTURE;
    }
}