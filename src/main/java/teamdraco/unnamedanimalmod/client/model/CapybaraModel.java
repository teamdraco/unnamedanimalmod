package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.CapybaraEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class CapybaraModel<T extends Entity> extends AgeableModel<CapybaraEntity> {
    public ModelRenderer body;
    public ModelRenderer rightBackLeg;
    public ModelRenderer leftBackLeg;
    public ModelRenderer rightFrontLeg;
    public ModelRenderer leftFrontLeg;
    public ModelRenderer head;
    public ModelRenderer chestLeft;
    public ModelRenderer chestRight;
    public ModelRenderer earRight;
    public ModelRenderer earLeft;
    public ModelRenderer hat;
    public ModelRenderer hatBrim;

    public CapybaraModel() {
        this.texWidth = 80;
        this.texHeight = 74;
        this.chestRight = new ModelRenderer(this, 40, 58);
        this.chestRight.setPos(-7.0F, -3.0F, 4.0F);
        this.chestRight.addBox(-2.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 0, 16);
        this.earRight.setPos(-3.5F, -5.0F, 1.5F);
        this.earRight.addBox(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earRight, -0.39269908169872414F, -0.39269908169872414F, 0.0F);
        this.rightFrontLeg = new ModelRenderer(this, 50, 0);
        this.rightFrontLeg.setPos(-4.0F, 13.3F, -8.0F);
        this.rightFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.hatBrim = new ModelRenderer(this, 45, 17);
        this.hatBrim.setPos(0.0F, 0.0F, -3.5F);
        this.hatBrim.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setPos(0.0F, 11.0F, 0.0F);
        this.body.addBox(-7.0F, -7.0F, -11.0F, 14.0F, 14.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.08726646259971647F, 0.0F, 0.0F);
        this.hat = new ModelRenderer(this, 30, 36);
        this.hat.setPos(0.0F, -5.1F, -1.5F);
        this.hat.addBox(-3.5F, -4.0F, -1.5F, 7.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(hat, -0.3127630032889644F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 36);
        this.head.setPos(0.0F, 4.5F, -10.0F);
        this.head.addBox(-4.0F, -5.5F, -11.0F, 8.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.chestLeft = new ModelRenderer(this, 60, 58);
        this.chestLeft.mirror = true;
        this.chestLeft.setPos(7.0F, -3.0F, 4.0F);
        this.chestLeft.addBox(0.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.leftBackLeg = new ModelRenderer(this, 0, 0);
        this.leftBackLeg.setPos(3.5F, 13.3F, 10.0F);
        this.leftBackLeg.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelRenderer(this, 50, 0);
        this.leftFrontLeg.setPos(4.0F, 13.3F, -8.0F);
        this.leftFrontLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 0, 16);
        this.earLeft.mirror = true;
        this.earLeft.setPos(3.5F, -5.0F, 1.5F);
        this.earLeft.addBox(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earLeft, -0.39269908169872414F, 0.39269908169872414F, 0.0F);
        this.rightBackLeg = new ModelRenderer(this, 0, 0);
        this.rightBackLeg.setPos(-3.5F, 13.3F, 10.0F);
        this.rightBackLeg.addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.chestRight);
        this.head.addChild(this.earRight);
        this.hat.addChild(this.hatBrim);
        this.head.addChild(this.hat);
        this.body.addChild(this.chestLeft);
        this.head.addChild(this.earLeft);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body, leftBackLeg, leftFrontLeg, rightBackLeg, rightFrontLeg, head);
    }

    @Override
    public void setupAnim(CapybaraEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;

        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.yRot = 0;
        this.body.zRot = MathHelper.cos(limbSwing * speed * 0.4F) * degree * 0.15F * limbSwingAmount;
        if (entityIn.isInWater()) {
            this.body.yRot = MathHelper.cos(ageInTicks * speed * 0.4F) * degree * 0.05F * 1;
            this.body.zRot = 0;
            this.leftBackLeg.xRot = MathHelper.cos(1.0F + ageInTicks * speed * 0.4F) * degree * 1.2F * 0.2F + 0.45F;
            this.rightBackLeg.xRot = MathHelper.cos(1.0F + ageInTicks * speed * 0.4F) * degree * -1.2F * 0.2F + 0.45F;
            this.rightFrontLeg.xRot = MathHelper.cos(1.0F + ageInTicks * speed * 0.4F) * degree * 0.8F * 0.2F + 0.45F;
            this.leftFrontLeg.xRot = MathHelper.cos(1.0F + ageInTicks * speed * 0.4F) * degree * -0.8F * 0.2F + 0.45F;
            this.head.xRot += MathHelper.cos(ageInTicks * speed * 0.4F) * degree * 0.2F * 0.2F - 0.25F;
        } else {
            this.leftBackLeg.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
            this.rightBackLeg.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount;
            this.rightFrontLeg.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
            this.leftFrontLeg.xRot = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount;
            // this.head.rotateAngleX = MathHelper.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.2F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
