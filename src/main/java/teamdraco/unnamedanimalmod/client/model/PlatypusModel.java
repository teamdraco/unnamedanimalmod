package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.PlatypusEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class PlatypusModel<T extends Entity> extends AgeableModel<PlatypusEntity> {
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer bill;
    public ModelRenderer right_arm;
    public ModelRenderer left_arm;
    public ModelRenderer right_leg;
    public ModelRenderer left_leg;
    public ModelRenderer hat;
    public ModelRenderer shape9;

    public PlatypusModel() {
        this.texWidth = 48;
        this.texHeight = 32;
        this.bill = new ModelRenderer(this, 15, 17);
        this.bill.setPos(0.0F, -0.5F, -6.0F);
        this.bill.addBox(-2.0F, 0.0F, -5.0F, 4.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bill, 0.2617993877991494F, 0.0F, 0.0F);
        this.right_arm = new ModelRenderer(this, 0, 4);
        this.right_arm.mirror = true;
        this.right_arm.setPos(3.0F, 1.5F, -2.0F);
        this.right_arm.addBox(0.0F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.left_arm = new ModelRenderer(this, 0, 4);
        this.left_arm.setPos(-3.0F, 1.5F, -2.0F);
        this.left_arm.addBox(-3.0F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.shape9 = new ModelRenderer(this, 18, 0);
        this.shape9.setPos(0.0F, 0.0F, 0.0F);
        this.shape9.addBox(-4.0F, 0.0F, -3.5F, 8.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 22.0F, 0.0F);
        this.body.addBox(-3.0F, -3.0F, -6.0F, 6.0F, 5.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.right_leg = new ModelRenderer(this, 0, 0);
        this.right_leg.mirror = true;
        this.right_leg.setPos(3.0F, 1.5F, 5.0F);
        this.right_leg.addBox(0.0F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.hat = new ModelRenderer(this, 26, 25);
        this.hat.setPos(0.0F, -3.01F, -3.0F);
        this.hat.addBox(-3.0F, -2.0F, -2.5F, 6.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(hat, 0.0F, -0.3883357585687383F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 17);
        this.tail.setPos(0.0F, 0.0F, 6.0F);
        this.tail.addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.left_leg = new ModelRenderer(this, 0, 0);
        this.left_leg.setPos(-3.0F, 1.5F, 5.0F);
        this.left_leg.addBox(-3.0F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.bill);
        this.body.addChild(this.right_arm);
        this.body.addChild(this.left_arm);
        this.hat.addChild(this.shape9);
        this.body.addChild(this.right_leg);
        this.body.addChild(this.hat);
        this.body.addChild(this.tail);
        this.body.addChild(this.left_leg);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(PlatypusEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isInWater()) {
            float speed = 1.0f;
            float degree = 1.0f;
            this.body.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.3F * limbSwingAmount;
            this.right_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -1.8F * limbSwingAmount - 0.1F;
            this.right_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount - 0.1F;
            this.left_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.8F * limbSwingAmount - 0.1F;
            this.left_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.5F * limbSwingAmount + 0.1F;
            this.right_leg.yRot = -0.54033285F * limbSwingAmount - 1.3F;
            this.right_leg.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 1.5F;
            this.left_leg.yRot = 0.54033285F * limbSwingAmount + 1.3F;
            this.left_leg.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount - 1.5F;
            this.tail.yRot = MathHelper.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.6F * limbSwingAmount;
            this.right_arm.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 4.0F;
            this.left_arm.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 4.0F;
            this.bill.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.25F;
            this.body.xRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.1F * limbSwingAmount;
        }
        else if (entityIn.isBaby() && !entityIn.isInWater()) {
            float speed = 5.0f;
            float degree = 3.0f;
            limbSwingAmount = MathHelper.clamp(limbSwingAmount, -0.05f, 0.05f);
            this.body.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
            this.right_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 0.1F;
            this.right_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 0.1F;
            this.left_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount - 0.1F;
            this.left_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount - 0.1F;
            this.right_leg.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount - 0.5F;
            this.right_leg.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 0.1F;
            this.left_leg.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount + 0.5F;
            this.left_leg.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount - 0.1F;
            this.tail.yRot = MathHelper.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.right_arm.xRot = 0.0F;
            this.left_arm.xRot = 0.0F;
            this.bill.xRot = 0.2617993877991494F;
            this.body.xRot = 0.0F;
        }
        else if (!entityIn.isBaby() && !entityIn.isInWater()) {
            float speed = 5.0f;
            float degree = 3.0f;
            limbSwingAmount = MathHelper.clamp(limbSwingAmount, -0.35f, 0.35f);
            this.body.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
            this.right_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount + 0.1F;
            this.right_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 0.1F;
            this.left_arm.yRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 1.2F * limbSwingAmount - 0.1F;
            this.left_arm.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount - 0.1F;
            this.right_leg.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount - 0.5F;
            this.right_leg.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 0.1F;
            this.left_leg.yRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -1.2F * limbSwingAmount + 0.5F;
            this.left_leg.zRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount - 0.1F;
            this.tail.yRot = MathHelper.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.right_arm.xRot = 0.0F;
            this.left_arm.xRot = 0.0F;
            this.bill.xRot = 0.2617993877991494F;
            this.body.xRot = 0.0F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
