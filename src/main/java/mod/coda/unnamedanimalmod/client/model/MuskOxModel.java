package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.MuskOxEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class MuskOxModel<T extends Entity> extends AgeableModel<MuskOxEntity> {
    public ModelRenderer body;
    public ModelRenderer hips;
    public ModelRenderer armRight;
    public ModelRenderer armLeft;
    public ModelRenderer head;
    public ModelRenderer shagBody;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer shagBody_1;
    public ModelRenderer tail;
    public ModelRenderer nose;
    public ModelRenderer hornBase;
    public ModelRenderer hornRight;
    public ModelRenderer hornLeft;

    public MuskOxModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.tail = new ModelRenderer(this, 0, 0);
        this.tail.setRotationPoint(0.0F, -6.0F, 14.0F);
        this.tail.addBox(-1.0F, 0.0F, -3.0F, 2.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.5235987755982988F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 44, 0);
        this.nose.setRotationPoint(0.0F, 2.0F, -6.0F);
        this.nose.addBox(-3.0F, -2.5F, -3.0F, 6.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.hips = new ModelRenderer(this, 60, 0);
        this.hips.setRotationPoint(0.0F, 2.0F, 8.0F);
        this.hips.addBox(-5.0F, -6.0F, 0.0F, 10.0F, 12.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 54, 26);
        this.head.setRotationPoint(0.0F, 1.0F, -8.0F);
        this.head.addBox(-4.5F, -4.5F, -6.0F, 9.0F, 9.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.shagBody_1 = new ModelRenderer(this, 46, 41);
        this.shagBody_1.setRotationPoint(0.0F, 6.0F, 7.0F);
        this.shagBody_1.addBox(-5.0F, 0.0F, -7.0F, 10.0F, 4.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.hornRight = new ModelRenderer(this, 45, 9);
        this.hornRight.mirror = true;
        this.hornRight.setRotationPoint(-4.0F, 1.5F, 0.0F);
        this.hornRight.addBox(-4.5F, 0.0F, -1.5F, 5.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 108, 0);
        this.armLeft.setRotationPoint(3.5F, 8.0F, -3.5F);
        this.armLeft.addBox(-2.0F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 108, 0);
        this.legLeft.setRotationPoint(2.5F, 6.0F, 10.5F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 108, 0);
        this.armRight.setRotationPoint(-3.5F, 8.0F, -3.5F);
        this.armRight.addBox(-2.0F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.hornLeft = new ModelRenderer(this, 45, 9);
        this.hornLeft.setRotationPoint(4.0F, 1.5F, 0.0F);
        this.hornLeft.addBox(-0.5F, 0.0F, -1.5F, 5.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 108, 0);
        this.legRight.setRotationPoint(-2.5F, 6.0F, 10.5F);
        this.legRight.addBox(-2.0F, 0.0F, -2.5F, 4.0F, 10.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 6.0F, -7.0F);
        this.body.addBox(-7.0F, -8.0F, -8.0F, 14.0F, 16.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.shagBody = new ModelRenderer(this, 0, 32);
        this.shagBody.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.shagBody.addBox(-7.0F, 0.0F, -8.0F, 14.0F, 4.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.hornBase = new ModelRenderer(this, 0, 53);
        this.hornBase.setRotationPoint(0.0F, -5.0F, -2.0F);
        this.hornBase.addBox(-5.5F, -1.5F, -1.5F, 11.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.hips.addChild(this.tail);
        this.head.addChild(this.nose);
        this.body.addChild(this.hips);
        this.body.addChild(this.head);
        this.hips.addChild(this.shagBody_1);
        this.hornBase.addChild(this.hornRight);
        this.body.addChild(this.armLeft);
        this.hips.addChild(this.legLeft);
        this.body.addChild(this.armRight);
        this.hornBase.addChild(this.hornLeft);
        this.hips.addChild(this.legRight);
        this.body.addChild(this.shagBody);
        this.head.addChild(this.hornBase);
    }

    @Override
    public void setRotationAngles(MuskOxEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.tail.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
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
