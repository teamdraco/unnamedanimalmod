package mod.coda.unnamedanimalmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.SnowLeopardEntityModel;
import mod.coda.unnamedanimalmod.entity.HumpheadParrotfishEntity;
import mod.coda.unnamedanimalmod.entity.SnowLeopardEntity;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SnowLeopardRenderer extends MobRenderer<SnowLeopardEntity, SnowLeopardEntityModel<SnowLeopardEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/snow_leopard.png");

    public SnowLeopardRenderer(EntityRendererManager manager) {
        super(manager, new SnowLeopardEntityModel<>(), 0.6f);
    }

    @Override
    public ResourceLocation getEntityTexture(SnowLeopardEntity entity) {
        return TEXTURE;
    }

    protected void applyRotations(SnowLeopardEntity entity, MatrixStack matrix, float var1, float var2, float var3) {
        super.applyRotations(entity, matrix, var1, var2, var3);
        if (entity.isSitting()) {
            matrix.translate(0.0D, -0.3D, 0.0D);
        }
    }
}
