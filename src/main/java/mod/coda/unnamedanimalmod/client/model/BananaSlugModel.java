package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BananaSlugModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer eyes;
    public ModelRenderer feelers;

    public BananaSlugModel() {
        this.textureWidth = 24;
        this.textureHeight = 12;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body.addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.eyes = new ModelRenderer(this, 0, 3);
        this.eyes.setRotationPoint(0.0F, -1.5F, -3.5F);
        this.eyes.addBox(-1.5F, -2.0F, -0.5F, 3.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.feelers = new ModelRenderer(this, 0, 0);
        this.feelers.setRotationPoint(0.0F, 1.0F, -4.0F);
        this.feelers.addBox(-1.5F, -0.5F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.eyes);
        this.body.addChild(this.feelers);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 4.0f;
        if (entityIn.isAlive()) {
            this.body.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.eyes.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.3F * limbSwingAmount + 0.2F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
