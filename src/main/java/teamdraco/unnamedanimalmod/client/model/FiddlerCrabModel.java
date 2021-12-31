package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FiddlerCrabModel<T extends Entity> extends EntityModel<T> {
    public ModelPart body;
    public ModelPart leg_right_front;
    public ModelPart leg_right_mid1;
    public ModelPart leg_right_mid2;
    public ModelPart leg_right_back;
    public ModelPart leg_left_front;
    public ModelPart leg_left_mid1;
    public ModelPart leg_left_mid2;
    public ModelPart leg_left_back;
    public ModelPart claw_left;
    public ModelPart claw_right;
    public ModelPart eye_left;
    public ModelPart eye_right;
    public ModelPart shape17;

    public FiddlerCrabModel() {
        this.texWidth = 32;
        this.texHeight = 16;
        this.claw_right = new ModelPart(this, 12, 9);
        this.claw_right.setPos(3.0F, 21.5F, -3.0F);
        this.claw_right.addBox(-3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw_right, 0.0F, -0.3883357585687383F, 0.0F);
        this.leg_right_mid2 = new ModelPart(this, 0, 0);
        this.leg_right_mid2.setPos(3.0F, 22.0F, 0.5F);
        this.leg_right_mid2.addBox(-0.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_right_mid2, 0.0F, 0.0F, -0.7853981633974483F);
        this.leg_left_back = new ModelPart(this, 0, 0);
        this.leg_left_back.mirror = true;
        this.leg_left_back.setPos(-3.0F, 22.0F, 1.0F);
        this.leg_left_back.addBox(-2.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_left_back, 0.7853981633974483F, 0.0F, 0.7853981633974483F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 21.0F, 0.0F);
        this.body.addBox(-3.5F, -1.5F, -3.0F, 7.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.3883357585687383F, 0.0F, 0.0F);
        this.eye_right = new ModelPart(this, 0, 9);
        this.eye_right.setPos(1.0F, 0.0F, -3.0F);
        this.eye_right.addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye_right, 0.3883357585687383F, 0.0F, 0.0F);
        this.claw_left = new ModelPart(this, 0, 10);
        this.claw_left.setPos(-3.0F, 21.0F, -3.0F);
        this.claw_left.addBox(0.0F, -1.0F, -2.0F, 5.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(claw_left, 0.0F, 0.7853981633974483F, 0.0F);
        this.leg_left_mid2 = new ModelPart(this, 0, 0);
        this.leg_left_mid2.mirror = true;
        this.leg_left_mid2.setPos(-3.0F, 22.0F, 0.5F);
        this.leg_left_mid2.addBox(-2.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_left_mid2, 0.0F, 0.0F, 0.7853981633974483F);
        this.leg_right_mid1 = new ModelPart(this, 0, 0);
        this.leg_right_mid1.setPos(3.0F, 22.0F, 0.0F);
        this.leg_right_mid1.addBox(-0.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_right_mid1, -0.3883357585687383F, 0.0F, -0.7853981633974483F);
        this.leg_right_back = new ModelPart(this, 0, 0);
        this.leg_right_back.setPos(3.0F, 22.0F, 1.0F);
        this.leg_right_back.addBox(-0.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_right_back, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.shape17 = new ModelPart(this, 19, 9);
        this.shape17.setPos(0.0F, -1.5F, 0.0F);
        this.shape17.addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leg_right_front = new ModelPart(this, 0, 0);
        this.leg_right_front.setPos(3.0F, 22.0F, -0.5F);
        this.leg_right_front.addBox(-0.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_right_front, -0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.eye_left = new ModelPart(this, 0, 9);
        this.eye_left.setPos(-1.0F, 0.0F, -3.0F);
        this.eye_left.addBox(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(eye_left, 0.3883357585687383F, 0.0F, 0.0F);
        this.leg_left_front = new ModelPart(this, 0, 0);
        this.leg_left_front.mirror = true;
        this.leg_left_front.setPos(-3.0F, 22.0F, 0.0F);
        this.leg_left_front.addBox(-2.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_left_front, -0.3883357585687383F, 0.0F, 0.7853981633974483F);
        this.leg_left_mid1 = new ModelPart(this, 0, 0);
        this.leg_left_mid1.mirror = true;
        this.leg_left_mid1.setPos(-3.0F, 22.0F, -0.5F);
        this.leg_left_mid1.addBox(-2.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leg_left_mid1, -0.7853981633974483F, 0.0F, 0.7853981633974483F);
        this.body.addChild(this.eye_right);
        this.body.addChild(this.shape17);
        this.body.addChild(this.eye_left);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.leg_left_front, this.leg_right_back, this.claw_right, this.body, this.leg_right_mid2, this.leg_right_mid1, this.leg_right_front, this.leg_left_mid1, this.claw_left, this.leg_left_back, this.leg_left_mid2).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isInWater()) {
            float speed = 2.5f;
            float degree = 3.0f;
            limbSwingAmount = Mth.clamp(limbSwingAmount, -0.35F, 0.35F);
            this.claw_right.y = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 21.475F;
            this.claw_left.y = Mth.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 20.975F;
            this.eye_left.xRot = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.4F;
            this.eye_right.xRot = Mth.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.4F;
            this.body.y = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 21.0F;
            this.leg_right_front.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_front.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.8F;
            this.leg_right_mid1.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_mid1.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.8F;
            this.leg_right_mid2.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_mid2.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.8F;
            this.leg_right_back.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_back.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.4F;
            this.leg_left_front.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_front.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.8F;
            this.leg_left_mid1.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_mid1.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.8F;
            this.leg_left_mid2.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_mid2.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.8F;
            this.leg_left_back.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_back.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.4F;
        }
        else {
            float speed = 1.5f;
            float degree = 1.0f;
            this.claw_right.y = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 21.475F;
            this.claw_left.y = Mth.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 20.975F;
            this.eye_left.xRot = Mth.cos(-1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.4F;
            this.eye_right.xRot = Mth.cos(-1.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 0.4F;
            this.body.y = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.05F * limbSwingAmount + 21.0F;
            this.leg_right_front.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_front.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.8F;
            this.leg_right_mid1.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_mid1.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.8F;
            this.leg_right_mid2.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_mid2.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.8F;
            this.leg_right_back.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount + 0.1F;
            this.leg_right_back.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.4F;
            this.leg_left_front.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_front.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.8F;
            this.leg_left_mid1.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_mid1.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.8F;
            this.leg_left_mid2.yRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_mid2.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.8F;
            this.leg_left_back.yRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.1F;
            this.leg_left_back.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.4F;
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
