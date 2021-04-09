package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.SouthernRightWhaleEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class SouthernRightWhaleModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer tail_stock1;
    public ModelRenderer right_fin;
    public ModelRenderer left_fin;
    public ModelRenderer jaw;
    public ModelRenderer tail_stock2;
    public ModelRenderer flukes;

    public SouthernRightWhaleModel() {
        this.textureWidth = 512;
        this.textureHeight = 304;
        this.tail_stock1 = new ModelRenderer(this, 256, 200);
        this.tail_stock1.setRotationPoint(0.0F, -14.5F, 46.0F);
        this.tail_stock1.addBox(-22.0F, -22.5F, -2.0F, 44.0F, 45.0F, 58.0F, 0.0F, 0.0F, 0.0F);
        this.right_fin = new ModelRenderer(this, 238, 0);
        this.right_fin.setRotationPoint(38.0F, 15.0F, -25.0F);
        this.right_fin.addBox(0.0F, -2.0F, -11.0F, 40.0F, 4.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.flukes = new ModelRenderer(this, 0, 154);
        this.flukes.setRotationPoint(0.0F, -3.0F, 26.0F);
        this.flukes.addBox(-36.0F, -2.0F, 0.0F, 72.0F, 4.0F, 28.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelRenderer(this, 0, 186);
        this.jaw.setRotationPoint(-2.0F, 16.0F, -3.0F);
        this.jaw.addBox(-29.0F, -24.0F, -61.0F, 62.0F, 33.0F, 66.0F, 0.0F, 0.0F, 0.0F);
        this.left_fin = new ModelRenderer(this, 238, 0);
        this.left_fin.mirror = true;
        this.left_fin.setRotationPoint(-36.0F, 15.0F, -25.0F);
        this.left_fin.addBox(-40.0F, -2.0F, -11.0F, 40.0F, 4.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.tail_stock2 = new ModelRenderer(this, 238, 26);
        this.tail_stock2.setRotationPoint(0.0F, -6.5F, 55.0F);
        this.tail_stock2.addBox(-11.0F, -13.0F, 0.0F, 22.0F, 26.0F, 38.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 266, 92);
        this.head.setRotationPoint(1.0F, -8.0F, -44.0F);
        this.head.addBox(-29.0F, -24.0F, -62.0F, 58.0F, 46.0F, 62.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-36.0F, -40.0F, -45.0F, 74.0F, 64.0F, 90.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.tail_stock1);
        this.body.addChild(this.right_fin);
        this.tail_stock2.addChild(this.flukes);
        this.head.addChild(this.jaw);
        this.body.addChild(this.left_fin);
        this.tail_stock1.addChild(this.tail_stock2);
        this.body.addChild(this.head);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> {
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float motionY = (float) entityIn.getMotion().y;

        if (Entity.horizontalMag(entityIn.getMotion()) > 1.0E-7D) {
            //this.body.rotateAngleX += -0.05F + -0.05F * MathHelper.cos(motionY * 0.3F);
            if(!entityIn.isInWater()) {
                this.body.rotateAngleZ = (float) Math.toRadians(-motionY * 180) * 0.8f;
            }
            else {
                float speed = 0.6f;
                float degree = 0.6f;
                this.body.rotateAngleZ = 0;
                this.body.rotateAngleX = MathHelper.cos(f * speed * 0.4F) * degree * 0.25F * f1 + 0.01F;
                this.right_fin.rotateAngleZ = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.6F * f1 + 0.2F;
                this.left_fin.rotateAngleZ = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * -0.6F * f1 - 0.2F;
                this.tail_stock1.rotateAngleX = MathHelper.cos(-1.0F + f * speed * 0.4F) * degree * 0.5F * f1;
                this.tail_stock2.rotateAngleX = MathHelper.cos(-2.0F + f * speed * 0.4F) * degree * 0.5F * f1;
                this.flukes.rotateAngleX = MathHelper.cos(-2.0F + f * speed * 0.4F) * degree * 0.8F * f1;
                this.head.rotateAngleX = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.1F * f1;
                this.jaw.rotateAngleX = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.1F * f1 + 0.05F;
            }
        }
        else {
            this.body.rotateAngleZ = 0;
            this.left_fin.rotateAngleY = 0;
            this.left_fin.rotateAngleZ = 0;
            this.right_fin.rotateAngleY = 0;
            this.right_fin.rotateAngleZ = 0;
            this.tail_stock1.rotateAngleX = 0;
            this.flukes.rotateAngleX = 0;
            this.body.rotateAngleX = 0;
            this.jaw.rotateAngleX = 0;
            this.head.rotateAngleX = 0;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
