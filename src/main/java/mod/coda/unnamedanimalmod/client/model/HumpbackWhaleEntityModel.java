package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.HumpbackWhaleEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class HumpbackWhaleEntityModel<T extends Entity> extends AgeableModel<HumpbackWhaleEntity> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer finLeft;
    public ModelRenderer finRight;
    public ModelRenderer finDorsal;
    public ModelRenderer neckJoint;
    public ModelRenderer fluke;
    public ModelRenderer head;
    public ModelRenderer jaw;

    public HumpbackWhaleEntityModel() {
        this.textureWidth = 300;
        this.textureHeight = 256;
        this.finDorsal = new ModelRenderer(this, 12, 93);
        this.finDorsal.setRotationPoint(0.0F, -12.0F, 24.0F);
        this.finDorsal.addBox(-0.5F, -5.0F, -5.0F, 1.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 94);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-12.0F, -12.0F, -40.0F, 24.0F, 14.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.body.addBox(-16.0F, -12.0F, -32.0F, 32.0F, 24.0F, 64.0F, 0.0F, 0.0F, 0.0F);
        this.neckJoint = new ModelRenderer(this, 0, 0);
        this.neckJoint.setRotationPoint(0.0F, 1.0F, -32.0F);
        this.neckJoint.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.finLeft = new ModelRenderer(this, 128, 0);
        this.finLeft.setRotationPoint(16.0F, 8.0F, -22.0F);
        this.finLeft.addBox(0.0F, -2.0F, -8.0F, 38.0F, 4.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 94, 114);
        this.jaw.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.jaw.addBox(-13.0F, -11.0F, -41.0F, 26.0F, 12.0F, 42.0F, 0.0F, 0.0F, 0.0F);
        this.finRight = new ModelRenderer(this, 128, 0);
        this.finRight.mirror = true;
        this.finRight.setRotationPoint(-16.0F, 8.0F, -22.0F);
        this.finRight.addBox(-38.0F, -2.0F, -8.0F, 38.0F, 4.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.fluke = new ModelRenderer(this, 0, 174);
        this.fluke.setRotationPoint(0.0F, 0.0F, 30.0F);
        this.fluke.addBox(-25.0F, -2.0F, 0.0F, 50.0F, 4.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 156, 56);
        this.tail.setRotationPoint(0.0F, 2.0F, 32.0F);
        this.tail.addBox(-10.0F, -9.0F, 0.0F, 20.0F, 18.0F, 36.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.finDorsal);
        this.neckJoint.addChild(this.head);
        this.body.addChild(this.neckJoint);
        this.body.addChild(this.finLeft);
        this.neckJoint.addChild(this.jaw);
        this.body.addChild(this.finRight);
        this.tail.addChild(this.fluke);
        this.body.addChild(this.tail);
    }

    @Override
    public void setRotationAngles(HumpbackWhaleEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 0.5f;

        this.neckJoint.rotateAngleX = headPitch * ((float)Math.PI / 280F);
        this.neckJoint.rotateAngleY = netHeadYaw * ((float)Math.PI / 280F);

        float motionY = (float) entityIn.getMotion().y;

        if (Entity.horizontalMag(entityIn.getMotion()) > 1.0E-7D) {
            this.body.rotateAngleX += -0.05F + -0.05F * MathHelper.cos(motionY * 0.3F);
            if(!entityIn.isInWater()) {
                System.out.println(motionY);
                this.body.rotateAngleZ = (float) Math.toRadians(-motionY * 180) * 0.8f;
            }
            else
                body.rotateAngleZ = 0;
            this.finLeft.rotateAngleY = MathHelper.cos(f * speed * 0.2F) * degree * 1.0F * f1 - 0.05F;
            this.finLeft.rotateAngleZ = MathHelper.cos(1.0F + f * speed * 0.2F) * degree * -1.0F * f1 + 0.5F;
            this.finRight.rotateAngleY = MathHelper.cos(f * speed * 0.2F) * degree * -1.0F * f1 + 0.05F;
            this.finRight.rotateAngleZ = MathHelper.cos(1.0F + f * speed * 0.2F) * degree * 1.0F * f1 - 0.5F;
            this.tail.rotateAngleX = MathHelper.cos(f * speed * 0.2F) * degree * 0.3F * f1;
            this.fluke.rotateAngleX = MathHelper.cos(f * speed * 0.2F) * degree * 1.0F * f1;
            this.body.rotateAngleX = MathHelper.cos(f * speed * 0.2F) * degree * 0.2F * f1;
            this.neckJoint.rotateAngleX = MathHelper.cos(f * speed * 0.2F) * degree * 0.2F * f1 - 0.04F;

        } else {
            this.body.rotateAngleX = 0;
        }
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
