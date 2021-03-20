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
public class CapybaraModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer rightBackLeg;
    public ModelRenderer leftBackLeg;
    public ModelRenderer rightFrontLeg;
    public ModelRenderer leftFrontLeg;
    public ModelRenderer head;
    public ModelRenderer chestLeft;
    public ModelRenderer chestRight;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;

    public CapybaraModel() {
        this.textureWidth = 80;
        this.textureHeight = 64;
        this.earLeft = new ModelRenderer(this, 0, 16);
        this.earLeft.mirror = true;
        this.earLeft.setRotationPoint(3.5F, -5.0F, 1.5F);
        this.earLeft.addBox(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earLeft, -0.39269908169872414F, 0.39269908169872414F, 0.0F);
        this.leftBackLeg = new ModelRenderer(this, 0, 0);
        this.leftBackLeg.setRotationPoint(3.5F, 13.3F, 10.0F);
        this.leftBackLeg.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelRenderer(this, 50, 0);
        this.leftFrontLeg.setRotationPoint(4.0F, 13.3F, -8.0F);
        this.leftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 0, 16);
        this.earRight.setRotationPoint(-3.5F, -5.0F, 1.5F);
        this.earRight.addBox(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earRight, -0.39269908169872414F, -0.39269908169872414F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.body.addBox(-7.0F, -7.0F, -11.0F, 14.0F, 14.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 36);
        this.head.setRotationPoint(0.0F, 4.5F, -10.0F);
        this.head.addBox(-4.0F, -5.5F, -11.0F, 8.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg = new ModelRenderer(this, 0, 0);
        this.rightBackLeg.setRotationPoint(-3.5F, 13.3F, 10.0F);
        this.rightBackLeg.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightFrontLeg = new ModelRenderer(this, 50, 0);
        this.rightFrontLeg.setRotationPoint(-4.0F, 13.3F, -8.0F);
        this.rightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.chestRight = new ModelRenderer(this, 44, 44);
        this.chestRight.setRotationPoint(-7.0F, -3.0F, 4.0F);
        this.chestRight.addBox(-2.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.chestLeft = new ModelRenderer(this, 44, 44);
        this.chestLeft.mirror = true;
        this.chestLeft.setRotationPoint(7.0F, -3.0F, 4.0F);
        this.chestLeft.addBox(0.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.earLeft);
        this.head.addChild(this.earRight);
        this.body.addChild(this.chestRight);
        this.body.addChild(this.chestLeft);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.leftBackLeg, this.leftFrontLeg, this.body, this.head, this.rightBackLeg, this.rightFrontLeg).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isInWater()) {
            float speed = 1.0f;
            float degree = 1.0f;
            this.body.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount;
            this.leftBackLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 0.45F;
            this.rightBackLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount + 0.45F;
            this.rightFrontLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.45F;
            this.leftFrontLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.45F;
            this.head.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 0.25F;
        }
        float speed = 1.0f;
        float degree = 1.0f;
        this.body.rotateAngleZ = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.15F * limbSwingAmount;
        this.leftBackLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.1F;
        this.rightBackLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.1F;
        this.rightFrontLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.1F;
        this.leftFrontLeg.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.1F;
//        this.head.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
