package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.coda.unnamedanimalmod.entity.SnowLeopardEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class SnowLeopardEntityModel<T extends Entity> extends AgeableModel<SnowLeopardEntity> {
    public ModelRenderer body;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer tail1;
    public ModelRenderer head;
    public ModelRenderer tail2;
    public ModelRenderer snout;
    public ModelRenderer earLeft;
    public ModelRenderer earRight;

    public SnowLeopardEntityModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.earRight = new ModelRenderer(this, 0, 0);
        this.earRight.mirror = true;
        this.earRight.setRotationPoint(-2.5F, -2.5F, -2.5F);
        this.earRight.addBox(-2.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.armRight = new ModelRenderer(this, 35, 0);
        this.armRight.mirror = true;
        this.armRight.setRotationPoint(-3.0F, 5.0F, -6.0F);
        this.armRight.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 35, 0);
        this.legRight.mirror = true;
        this.legRight.setRotationPoint(-3.0F, 5.0F, 5.0F);
        this.legRight.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tail1 = new ModelRenderer(this, 0, 28);
        this.tail1.setRotationPoint(0.0F, -4.0F, 8.0F);
        this.tail1.addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail1, -0.9599310885968813F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 0, 0);
        this.earLeft.setRotationPoint(2.5F, -2.5F, -2.5F);
        this.earLeft.addBox(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.body.addBox(-4.5F, -5.0F, -8.0F, 9.0F, 10.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 27, 27);
        this.head.setRotationPoint(0.0F, -4.0F, -6.0F);
        this.head.addBox(-3.5F, -3.5F, -6.0F, 7.0F, 7.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 35, 0);
        this.legLeft.setRotationPoint(3.0F, 5.0F, 5.0F);
        this.legLeft.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 0, 42);
        this.tail2.setRotationPoint(0.0F, 1.5F, 10.0F);
        this.tail2.addBox(-2.0F, -3.0F, 0.0F, 4.0F, 4.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail2, 0.7853981633974483F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 35, 0);
        this.armLeft.setRotationPoint(3.0F, 5.0F, -6.0F);
        this.armLeft.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.snout = new ModelRenderer(this, 0, 6);
        this.snout.setRotationPoint(0.0F, 1.5F, -6.0F);
        this.snout.addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.earRight);
        this.body.addChild(this.armRight);
        this.body.addChild(this.legRight);
        this.body.addChild(this.tail1);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.head);
        this.body.addChild(this.legLeft);
        this.tail1.addChild(this.tail2);
        this.body.addChild(this.armLeft);
        this.head.addChild(this.snout);
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
    public void setRotationAngles(SnowLeopardEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityIn.isSitting() && entityIn.isTamed()) {
            this.tail1.rotateAngleX = -0.9599310885968813F;
            this.tail1.rotateAngleY = -0.9773843811168246F;
            this.tail1.rotateAngleZ = 0.5082398928281348F;
            this.tail2.rotateAngleX = -0.4656538497584093F;
            this.tail2.rotateAngleY = -0.8600982340775168F;
            this.tail2.rotateAngleZ = 0.5473352640780661F;
            this.legLeft.rotateAngleX = -0.8726646259971648F;
            this.legLeft.rotateAngleY = -0.5235987755982988F;
            this.legLeft.rotateAngleZ = -0.3490658503988659F;
            this.legRight.rotateAngleX = -0.8726646259971648F;
            this.legRight.rotateAngleY = 0.5235987755982988F;
            this.legRight.rotateAngleZ = 0.3490658503988659F;
            this.body.rotateAngleX = -0.6108652381980153F;
            this.head.rotateAngleX = 0.6255260065779288F;
            this.head.rotateAngleY = 0;
            this.head.rotationPointY = -3.0F;
            this.head.rotationPointZ = -8.0F;
            this.armRight.rotateAngleX = 0.6108652381980153F;
            this.armRight.rotateAngleY = -0.08726646259971647F;
            this.armLeft.rotateAngleX = 0.6108652381980153F;
            this.armLeft.rotateAngleY = 0.08726646259971647F;
            this.armLeft.rotationPointY = 4.4F;
            this.armRight.rotationPointY = 4.4F;
        }
        else {
            this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
            this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
            this.legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.armRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.armLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tail1.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
            this.tail2.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
            this.head.rotationPointY = -4.0F;
            this.head.rotationPointZ = -6.0F;
            this.tail1.rotateAngleX = -0.9599310885968813F;
            this.tail1.rotateAngleZ = 0.0F;
            this.tail2.rotateAngleX = 0.7853981633974483F;
            this.tail2.rotateAngleZ = 0.0F;
            this.legLeft.rotateAngleY = 0.0F;
            this.legLeft.rotateAngleZ = 0.0F;
            this.legRight.rotateAngleY = 0.0F;
            this.legRight.rotateAngleZ = 0.0F;
            this.body.rotateAngleX = 0.0F;
            this.head.rotateAngleX = 0.0F;
            this.armRight.rotateAngleY = 0.0F;
            this.armLeft.rotateAngleY = 0.0F;
            this.armLeft.rotationPointY = 5.0F;
            this.armRight.rotationPointY = 5.0F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
