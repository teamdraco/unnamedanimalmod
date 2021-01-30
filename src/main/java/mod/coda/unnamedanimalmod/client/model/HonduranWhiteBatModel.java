package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.HonduranWhiteBatEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class HonduranWhiteBatModel<T extends Entity> extends AgeableModel<HonduranWhiteBatEntity> {
    public ModelRenderer body;
    public ModelRenderer nose;
    public ModelRenderer earLeft;
    public ModelRenderer feet;
    public ModelRenderer earRight;
    public ModelRenderer wingRight1;
    public ModelRenderer wingLeft1;
    public ModelRenderer wingRight2;
    public ModelRenderer wingLeft2;

    public HonduranWhiteBatModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.5F, 0.0F);
        this.body.addBox(-1.5F, -2.5F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 9, 0);
        this.earRight.setRotationPoint(-1.5F, -2.5F, 0.0F);
        this.earRight.addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.wingRight1 = new ModelRenderer(this, 0, 8);
        this.wingRight1.mirror = true;
        this.wingRight1.setRotationPoint(-1.5F, 0.5F, 0.0F);
        this.wingRight1.addBox(-4.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 0);
        this.nose.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.nose.addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(nose, 0.17453292519943295F, 0.0F, 0.0F);
        this.wingRight2 = new ModelRenderer(this, 0, 13);
        this.wingRight2.mirror = true;
        this.wingRight2.setRotationPoint(-4.0F, -1.0F, 0.0F);
        this.wingRight2.addBox(-5.0F, -2.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.wingLeft2 = new ModelRenderer(this, 0, 13);
        this.wingLeft2.setRotationPoint(4.0F, -1.0F, 0.0F);
        this.wingLeft2.addBox(0.0F, -2.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.feet = new ModelRenderer(this, 0, 18);
        this.feet.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.feet.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 9, 0);
        this.earLeft.mirror = true;
        this.earLeft.setRotationPoint(1.5F, -2.5F, 0.0F);
        this.earLeft.addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.wingLeft1 = new ModelRenderer(this, 0, 8);
        this.wingLeft1.setRotationPoint(1.5F, 0.5F, 0.0F);
        this.wingLeft1.addBox(0.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.earRight);
        this.body.addChild(this.wingRight1);
        this.body.addChild(this.nose);
        this.wingRight1.addChild(this.wingRight2);
        this.wingLeft1.addChild(this.wingLeft2);
        this.body.addChild(this.feet);
        this.body.addChild(this.earLeft);
        this.body.addChild(this.wingLeft1);
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
    public void setRotationAngles(HonduranWhiteBatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.getIsBatHanging()) {
            this.wingRight1.setRotationPoint(-1.6F, 0.0F, 1.0F);
            this.wingLeft1.setRotationPoint(1.6F, 0.0F, 1.0F);
            this.body.rotateAngleX = (float)Math.PI;
            this.wingRight1.rotateAngleX = -0.15707964F;
            this.wingRight1.rotateAngleY = -1.2566371F;
            this.wingRight2.rotateAngleY = -1.7278761F;
            this.wingLeft1.rotateAngleX = this.wingRight1.rotateAngleX;
            this.wingLeft1.rotateAngleY = -this.wingRight1.rotateAngleY;
            this.wingLeft2.rotateAngleY = -this.wingRight2.rotateAngleY;
        } else {
            this.body.rotateAngleX = ((float)Math.PI / 4F) + MathHelper.cos(ageInTicks * 0.1F) * 0.10F;
            this.body.rotateAngleY = 0.0F;
            this.wingRight1.rotateAngleY = MathHelper.cos(ageInTicks * 1.0F) * (float)Math.PI * 0.25F;
            this.wingLeft1.rotateAngleY = -this.wingRight1.rotateAngleY;
            this.wingRight2.rotateAngleY = this.wingRight1.rotateAngleY * 0.5F;
            this.wingLeft2.rotateAngleY = -this.wingRight1.rotateAngleY * 0.5F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
