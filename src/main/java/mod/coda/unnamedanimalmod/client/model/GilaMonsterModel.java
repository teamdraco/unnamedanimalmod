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
public class GilaMonsterModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer leftLeg;
    public ModelRenderer rightLeg;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ModelRenderer tail;
    public ModelRenderer neck;
    public ModelRenderer jaw;
    public ModelRenderer head;

    public GilaMonsterModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.leftArm = new ModelRenderer(this, 0, 0);
        this.leftArm.setRotationPoint(1.5F, 1.0F, -3.0F);
        this.leftArm.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, -1.0471975511965976F, -0.6981317007977318F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.body.addBox(-2.0F, -1.5F, -3.5F, 4.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 0);
        this.rightLeg.setRotationPoint(-1.5F, 1.0F, 3.0F);
        this.rightLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, 1.0471975511965976F, -1.3962634015954636F, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.rightArm.setRotationPoint(-1.5F, 1.0F, -3.0F);
        this.rightArm.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, -1.0471975511965976F, 0.6981317007977318F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 0);
        this.leftLeg.setRotationPoint(1.5F, 1.0F, 3.0F);
        this.leftLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, 1.0471975511965976F, 1.3962634015954636F, 0.0F);
        this.head = new ModelRenderer(this, 0, 11);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-1.5F, -2.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelRenderer(this, 0, 0);
        this.neck.setRotationPoint(0.0F, 0.5F, -3.5F);
        this.neck.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 13, 11);
        this.jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.jaw.addBox(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 17);
        this.tail.setRotationPoint(0.0F, -0.5F, 3.5F);
        this.tail.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -0.1308996938995747F, 0.0F, 0.0F);
        this.body.addChild(this.leftArm);
        this.body.addChild(this.rightLeg);
        this.body.addChild(this.rightArm);
        this.body.addChild(this.leftLeg);
        this.neck.addChild(this.head);
        this.body.addChild(this.neck);
        this.neck.addChild(this.jaw);
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
        float speed = 1.0f;
        float degree = 1.0f;
        this.body.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
        this.tail.rotateAngleY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
        this.neck.rotateAngleY = MathHelper.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
        this.rightArm.rotateAngleY = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 1.0F;
        this.leftArm.rotateAngleY = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 1.0F;
        this.rightLeg.rotateAngleY = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 1.1F;
        this.leftLeg.rotateAngleY = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 1.1F;

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
