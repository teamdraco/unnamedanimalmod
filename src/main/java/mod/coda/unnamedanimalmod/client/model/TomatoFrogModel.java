package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.TomatoFrogEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class TomatoFrogModel<T extends Entity> extends SegmentedModel<TomatoFrogEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer frontRightLeg;
    public ModelRenderer frontLeftLeg;
    public ModelRenderer backRightLeg;
    public ModelRenderer backLeftLeg;
    public ModelRenderer tailTadpole;
    public ModelRenderer bodyTadpole;

    @Override
    public Iterable<ModelRenderer> getParts() {
        if (this.isChild) {
            return ImmutableList.of(bodyTadpole);
        } else {
            return ImmutableList.of(body);
        }
    }

    protected abstract void setAngles();

    @Override
    public void setRotationAngles(TomatoFrogEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.isChild) {
            this.tailTadpole.rotateAngleY = MathHelper.cos(2.0F + f * 1.0f * 0.3F) * 1.0f * 0.3F * f1;
        }
        else {
            float speed = 1.0f;
            float degree = 1.0f;
            this.body.rotateAngleY = MathHelper.cos(1.5F + f * speed * 0.4F) * degree * 0.4F * f1;
            this.frontRightLeg.rotateAngleX = MathHelper.cos(f * speed * 0.4F) * degree * 0.8F * f1 - 0.2F;
            this.frontLeftLeg.rotateAngleX = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1 - 0.2F;
            this.backLeftLeg.rotateAngleX = MathHelper.cos(f * speed * 0.4F) * degree * 0.8F * f1 + 0.2F;
            this.backRightLeg.rotateAngleX = MathHelper.cos(f * speed * 0.4F) * degree * -0.8F * f1 + 0.2F;
        }
    }

    public TomatoFrogModel() {
        setAngles();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public static class Adult extends TomatoFrogModel {
        @Override
        protected void setAngles() {
            this.textureWidth = 32;
            this.textureHeight = 32;
            this.head = new ModelRenderer(this, 0, 11);
            this.head.setRotationPoint(0.0F, -0.5F, -3.0F);
            this.head.addBox(-2.5F, -1.0F, -2.0F, 5.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.backLeftLeg = new ModelRenderer(this, 20, 0);
            this.backLeftLeg.setRotationPoint(3.0F, 3.0F, 2.5F);
            this.backLeftLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backLeftLeg, 0.0F, -0.4363323129985824F, 0.0F);
            this.backRightLeg = new ModelRenderer(this, 20, 0);
            this.backRightLeg.mirror = true;
            this.backRightLeg.setRotationPoint(-3.0F, 3.0F, 2.5F);
            this.backRightLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backRightLeg, 0.0F, 0.4363323129985824F, 0.0F);
            this.frontLeftLeg = new ModelRenderer(this, 0, 0);
            this.frontLeftLeg.setRotationPoint(1.2F, 1.5F, -1.5F);
            this.frontLeftLeg.addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontLeftLeg, -0.2617993877991494F, 0.0F, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setRotationPoint(0.0F, 19.5F, 0.0F);
            this.body.addBox(-3.0F, -1.5F, -2.0F, 6.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
            this.frontRightLeg = new ModelRenderer(this, 0, 0);
            this.frontRightLeg.mirror = true;
            this.frontRightLeg.setRotationPoint(-2.2F, 1.5F, -1.5F);
            this.frontRightLeg.addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontRightLeg, -0.2617993877991494F, 0.17453292519943295F, 0.0F);
            this.body.addChild(this.head);
            this.body.addChild(this.backLeftLeg);
            this.body.addChild(this.backRightLeg);
            this.body.addChild(this.frontLeftLeg);
            this.body.addChild(this.frontRightLeg);
        }
    }

    public static class Child extends TomatoFrogModel {
        @Override
        protected void setAngles() {
            this.textureWidth = 10;
            this.textureHeight = 7;
            this.tailTadpole = new ModelRenderer(this, 1, 2);
            this.tailTadpole.setRotationPoint(0.0F, 0.0F, 1.5F);
            this.tailTadpole.addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.bodyTadpole = new ModelRenderer(this, 0, 0);
            this.bodyTadpole.setRotationPoint(0.0F, 23.0F, -1.0F);
            this.bodyTadpole.addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.bodyTadpole.addChild(this.tailTadpole);
        }
    }
}