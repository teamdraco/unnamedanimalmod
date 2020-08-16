package mod.coda.unnamedanimalmod.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.coda.unnamedanimalmod.Main;
import mod.coda.unnamedanimalmod.client.model.ElepehantnoseFishEntityModel;
import mod.coda.unnamedanimalmod.entity.ElephantnoseFishEntity;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ElephantnoseFishEntityRenderer extends MobRenderer<ElephantnoseFishEntity, ElepehantnoseFishEntityModel<ElephantnoseFishEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/entity/elephantnose_fish.png");

    public ElephantnoseFishEntityRenderer(EntityRendererManager manager) {
        super(manager, new ElepehantnoseFishEntityModel<>(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(ElephantnoseFishEntity entity) {
        return TEXTURE;
    }

    protected void applyRotations(ElephantnoseFishEntity entity, MatrixStack matrix, float var1, float var2, float var3) {
        super.applyRotations(entity, matrix, var1, var2, var3);
        float rotate = 4.3F * MathHelper.sin(0.6F * var1);
        matrix.rotate(Vector3f.YP.rotationDegrees(rotate));
        if (!entity.isInWater()) {
            matrix.translate(0.20000000298023224D, 0.10000000149011612D, 0.0D);
            matrix.rotate(Vector3f.ZP.rotationDegrees(90.0F));
        }
    }
}
