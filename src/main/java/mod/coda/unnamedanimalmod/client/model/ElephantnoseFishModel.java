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
public class ElephantnoseFishModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer trunk;
    public ModelRenderer tail;
    public ModelRenderer dorsalfin;
    public ModelRenderer analfin;
    public ModelRenderer pectoralfinright;
    public ModelRenderer pectoralfinleft;

    public ElephantnoseFishModel() {
        this.textureWidth = 25;
        this.textureHeight = 22;
        this.dorsalfin = new ModelRenderer(this, 0, 7);
        this.dorsalfin.setRotationPoint(0.0F, -1.5F, 2.0F);
        this.dorsalfin.addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body.addBox(-1.0F, -1.5F, -3.5F, 2.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralfinright = new ModelRenderer(this, 0, 0);
        this.pectoralfinright.mirror = true;
        this.pectoralfinright.setRotationPoint(1.0F, 0.5F, -1.0F);
        this.pectoralfinright.addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.analfin = new ModelRenderer(this, 0, 11);
        this.analfin.setRotationPoint(0.0F, 1.5F, 2.0F);
        this.analfin.addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralfinleft = new ModelRenderer(this, 0, 0);
        this.pectoralfinleft.setRotationPoint(-1.0F, 0.5F, -1.0F);
        this.pectoralfinleft.addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.trunk = new ModelRenderer(this, 12, 0);
        this.trunk.setRotationPoint(0.0F, 0.5F, -3.5F);
        this.trunk.addBox(-0.5F, 0.0F, -2.9F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(trunk, 0.2617993877991494F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 9, 6);
        this.tail.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.tail.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.dorsalfin);
        this.body.addChild(this.pectoralfinright);
        this.body.addChild(this.analfin);
        this.body.addChild(this.pectoralfinleft);
        this.body.addChild(this.trunk);
        this.body.addChild(this.tail);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entityIn.isInWater()) {
            f = 1.5F;
        }
        this.tail.rotateAngleY = -f * 0.45F * MathHelper.sin(0.6F * ageInTicks);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
