package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.PacmanFrogEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class PacmanFrogModel<T extends Entity> extends EntityModel<PacmanFrogEntity> {
    public ModelPart body;
    public ModelPart head;
    public ModelPart frontRightLeg;
    public ModelPart frontLeftLeg;
    public ModelPart backRightLeg;
    public ModelPart backLeftLeg;
    public ModelPart horns;
    public ModelPart tailTadpole;
    public ModelPart bodyTadpole;

    @Override
    public Iterable<ModelPart> parts() {
        if (this.young) {
            return ImmutableList.of(bodyTadpole);
        } else {
            return ImmutableList.of(body);
        }
    }

    public PacmanFrogModel() {
        setAngles();
    }

    protected abstract void setAngles();

    @Override
    public void setupAnim(PacmanFrogEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.young) {
            this.tailTadpole.yRot = Mth.cos(2.0F + f * 1.0f * 0.3F) * 1.5f * 0.65F * f1;
        }
        else {
            float speed = 1.0f;
            float degree = 1.0f;
            this.body.yRot = Mth.cos(1.5F + f * speed * 0.4F) * degree * 0.4F * f1;
            this.frontRightLeg.xRot = Mth.cos(f * speed * 0.4F) * degree * 0.8F * f1 - 0.2F;
            this.frontLeftLeg.xRot = Mth.cos(f * speed * 0.4F) * degree * -0.8F * f1 - 0.2F;
            this.backLeftLeg.xRot = Mth.cos(f * speed * 0.4F) * degree * 0.8F * f1 + 0.2F;
            this.backRightLeg.xRot = Mth.cos(f * speed * 0.4F) * degree * -0.8F * f1 + 0.2F;
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public static class Adult extends PacmanFrogModel {
        @Override
        protected void setAngles() {
            this.texWidth = 32;
            this.texHeight = 32;
            this.frontRightLeg = new ModelPart(this, 0, 0);
            this.frontRightLeg.mirror = true;
            this.frontRightLeg.setPos(-2.2F, 2.5F, -1.5F);
            this.frontRightLeg.addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontRightLeg, -1.0471975511965976F, 0.17453292519943295F, 0.0F);
            this.backLeftLeg = new ModelPart(this, 18, 13);
            this.backLeftLeg.setPos(5.5F, 3.0F, 2.5F);
            this.backLeftLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backLeftLeg, 0.0F, -0.08726646259971647F, 0.0F);
            this.horns = new ModelPart(this, 0, 22);
            this.horns.setPos(1.0F, 0.0F, 0.0F);
            this.horns.addBox(-2.0F, -3.0F, -1.0F, 5.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.head = new ModelPart(this, 0, 13);
            this.head.setPos(0.0F, -0.5F, -3.0F);
            this.head.addBox(-1.0F, -1.0F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.frontLeftLeg = new ModelPart(this, 0, 0);
            this.frontLeftLeg.setPos(4.2F, 2.5F, -1.5F);
            this.frontLeftLeg.addBox(-0.5F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(frontLeftLeg, -1.0471975511965976F, 0.0F, 0.0F);
            this.body = new ModelPart(this, 0, 0);
            this.body.setPos(-2.0F, 19.5F, 0.0F);
            this.body.addBox(-2.5F, -2.5F, -2.0F, 8.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
            this.backRightLeg = new ModelPart(this, 18, 13);
            this.backRightLeg.mirror = true;
            this.backRightLeg.setPos(-2.5F, 3.0F, 2.5F);
            this.backRightLeg.addBox(-0.5F, -0.5F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(backRightLeg, 0.0F, 0.08726646259971647F, 0.0F);
            this.body.addChild(this.frontRightLeg);
            this.body.addChild(this.backLeftLeg);
            this.head.addChild(this.horns);
            this.body.addChild(this.head);
            this.body.addChild(this.frontLeftLeg);
            this.body.addChild(this.backRightLeg);
        }
    }

    public static class Child extends PacmanFrogModel {
        @Override
        protected void setAngles() {
            this.texWidth = 16;
            this.texHeight = 16;
            this.bodyTadpole = new ModelPart(this, 0, 0);
            this.bodyTadpole.setPos(0.0F, 22.5F, -2.0F);
            this.bodyTadpole.addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.tailTadpole = new ModelPart(this, 0, 2);
            this.tailTadpole.setPos(0.0F, 0.0F, 2.0F);
            this.tailTadpole.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
            this.bodyTadpole.addChild(this.tailTadpole);
        }
    }
}
