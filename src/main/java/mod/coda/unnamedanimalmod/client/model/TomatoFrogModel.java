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
    private float jumpRotation;

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
            this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(f) * (float) Math.PI);
            this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            this.backLeftLeg.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
            this.backRightLeg.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
            this.frontRightLeg.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
            this.frontLeftLeg.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
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
            this.textureWidth = 18;
            this.textureHeight = 16;
            this.backLeftLeg = new ModelRenderer(this, 0, 7);
            this.backLeftLeg.setRotationPoint(1.5F, 2.0F, 1.0F);
            this.backLeftLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backLeftLeg, 0.0F, -0.4363323129985824F, 0.0F);
            this.frontRightLeg = new ModelRenderer(this, 0, 0);
            this.frontRightLeg.setRotationPoint(-1.2F, 0.5F, -1.5F);
            this.frontRightLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontRightLeg, -0.2617993877991494F, 0.17453292519943295F, 0.0F);
            this.backRightLeg = new ModelRenderer(this, 0, 7);
            this.backRightLeg.setRotationPoint(-1.5F, 2.0F, 1.0F);
            this.backRightLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backRightLeg, 0.0F, 0.4363323129985824F, 0.0F);
            this.head = new ModelRenderer(this, 10, 0);
            this.head.setRotationPoint(0.0F, -0.5F, -2.0F);
            this.head.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setRotationPoint(0.0F, 21.5F, 0.0F);
            this.body.addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.frontLeftLeg = new ModelRenderer(this, 0, 0);
            this.frontLeftLeg.setRotationPoint(1.2F, 0.5F, -1.5F);
            this.frontLeftLeg.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontLeftLeg, -0.2617993877991494F, -0.17453292519943295F, 0.0F);
            this.body.addChild(this.backLeftLeg);
            this.body.addChild(this.frontRightLeg);
            this.body.addChild(this.backRightLeg);
            this.body.addChild(this.head);
            this.body.addChild(this.frontLeftLeg);
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