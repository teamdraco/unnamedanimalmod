package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.SouthernRightWhaleEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class SouthernRightWhaleModel<T extends Entity> extends AgeableModel<SouthernRightWhaleEntity> {
    public ModelRenderer body;
    public ModelRenderer pectoralFinLeft;
    public ModelRenderer pectoralFinRight;
    public ModelRenderer tail;
    public ModelRenderer headJoint;
    public ModelRenderer flukes;
    public ModelRenderer jaw;
    public ModelRenderer head;
    public ModelRenderer jawInside;
    public ModelRenderer headInside;

    public SouthernRightWhaleModel() {
        this.textureWidth = 310;
        this.textureHeight = 320;
        this.pectoralFinLeft = new ModelRenderer(this, 0, 0);
        this.pectoralFinLeft.mirror = true;
        this.pectoralFinLeft.setRotationPoint(22.5F, 18.5F, -22.0F);
        this.pectoralFinLeft.addBox(0.0F, -2.0F, -8.0F, 23.0F, 4.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.jawInside = new ModelRenderer(this, 127, 254);
        this.jawInside.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.jawInside.addBox(-19.0F, -11.0F, -40.0F, 38.0F, 22.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 0, 198);
        this.jaw.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.jaw.addBox(-19.5F, -12.0F, -41.0F, 39.0F, 23.0F, 41.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 119);
        this.tail.setRotationPoint(0.0F, 1.5F, 40.0F);
        this.tail.addBox(-12.5F, -9.0F, 0.0F, 25.0F, 26.0F, 53.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 156, 119);
        this.head.setRotationPoint(0.0F, 10.5F, 0.0F);
        this.head.addBox(-18.5F, -24.0F, -40.0F, 37.0F, 24.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.headInside = new ModelRenderer(this, 161, 186);
        this.headInside.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headInside.addBox(-17.5F, -23.0F, -39.0F, 35.0F, 23.0F, 39.0F, 0.0F, 0.0F, 0.0F);
        this.pectoralFinRight = new ModelRenderer(this, 0, 0);
        this.pectoralFinRight.setRotationPoint(-22.5F, 17.5F, -22.0F);
        this.pectoralFinRight.addBox(-23.0F, -2.0F, -8.0F, 23.0F, 4.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.headJoint = new ModelRenderer(this, 0, 0);
        this.headJoint.setRotationPoint(0.0F, 0.0F, -40.0F);
        this.headJoint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 4.5F, 10.0F);
        this.body.addBox(-22.5F, -19.5F, -40.0F, 45.0F, 39.0F, 80.0F, 0.0F, 0.0F, 0.0F);
        this.flukes = new ModelRenderer(this, 170, 0);
        this.flukes.setRotationPoint(0.0F, 5.0F, 51.0F);
        this.flukes.addBox(-21.0F, -2.0F, -2.0F, 42.0F, 4.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.pectoralFinLeft);
        this.jaw.addChild(this.jawInside);
        this.headJoint.addChild(this.jaw);
        this.body.addChild(this.tail);
        this.headJoint.addChild(this.head);
        this.head.addChild(this.headInside);
        this.body.addChild(this.pectoralFinRight);
        this.body.addChild(this.headJoint);
        this.tail.addChild(this.flukes);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setRotationAngles(SouthernRightWhaleEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void setLivingAnimations(SouthernRightWhaleEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        float speed = 1.0f;
        float degree = 0.7f;
        float motionY = (float) entityIn.getMotion().y;

        if (Entity.horizontalMag(entityIn.getMotion()) > 1.0E-7D) {
            //this.body.rotateAngleX += -0.05F + -0.05F * MathHelper.cos(motionY * 0.3F);
            if(!entityIn.isInWater()) {
                this.body.rotateAngleZ = (float) Math.toRadians(-motionY * 180) * 0.8f;
            }
            else {
                this.body.rotateAngleZ = 0;
                this.pectoralFinLeft.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 1.0F * limbSwingAmount - 0.05F;
                this.pectoralFinLeft.rotateAngleZ = MathHelper.cos(1.0F + limbSwing * speed * 0.2F) * degree * -1.0F * limbSwingAmount + 0.5F;
                this.pectoralFinRight.rotateAngleY = MathHelper.cos(limbSwing * speed * 0.2F) * degree * -1.0F * limbSwingAmount + 0.05F;
                this.pectoralFinRight.rotateAngleZ = MathHelper.cos(1.0F + limbSwing * speed * 0.2F) * degree * 1.0F * limbSwingAmount - 0.5F;
                this.tail.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.3F * limbSwingAmount;
                this.flukes.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.8F * limbSwingAmount;
                this.body.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.2F * limbSwingAmount;
                this.headJoint.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.2F * limbSwingAmount + 0.05F;
                this.jaw.rotateAngleX = MathHelper.cos(limbSwing * speed * 0.2F) * degree * 0.2F * limbSwingAmount + 0.05F;
            }
        }
        else {
            this.body.rotateAngleZ = 0;
            this.pectoralFinLeft.rotateAngleY = 0;
            this.pectoralFinLeft.rotateAngleZ = 0;
            this.pectoralFinRight.rotateAngleY = 0;
            this.pectoralFinRight.rotateAngleZ = 0;
            this.tail.rotateAngleX = 0;
            this.flukes.rotateAngleX = 0;
            this.body.rotateAngleX = 0;
            this.jaw.rotateAngleX = 0;
            this.headJoint.rotateAngleX = 0;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
