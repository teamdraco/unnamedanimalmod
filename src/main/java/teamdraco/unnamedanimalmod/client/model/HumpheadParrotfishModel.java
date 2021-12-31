package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.HumpheadParrotfishEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class HumpheadParrotfishModel<T extends Entity> extends EntityModel<HumpheadParrotfishEntity> {
    public ModelPart body;
    public ModelPart hump;
    public ModelPart tail;
    public ModelPart finDorsal;
    public ModelPart finAnal;
    public ModelPart finBottomLeft;
    public ModelPart finBottomRight;
    public ModelPart finPectoralLeft;
    public ModelPart finPectoralRight;
    public ModelPart finTail;
    public ModelPart bodyBaby;
    public ModelPart tailBaby;
    public ModelPart dorsalfinBaby;
    public ModelPart analfinBaby;
    public ModelPart pelvicfinleftBaby;
    public ModelPart pelvicfinrightBaby;

    public HumpheadParrotfishModel() {
        setAngles();
    }

    protected abstract void setAngles();

    @Override
    public Iterable<ModelPart> parts() {
        if (this.young) {
            return ImmutableList.of(bodyBaby);
        } else {
            return ImmutableList.of(body);
        }
    }

    @Override
    public void setupAnim(HumpheadParrotfishEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.young) {
            float speed = 0.6f;
            float degree = 0.4f;
            this.bodyBaby.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.tailBaby.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount;
            this.pelvicfinleftBaby.zRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount - 0.5f;
            this.pelvicfinrightBaby.zRot = Mth.cos(limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.5f;
        }
        else {
            float speed = 0.8f;
            float degree = 0.7f;
            this.body.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.tail.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount;
            this.finTail.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount;
            this.finBottomLeft.zRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount - 0.5f;
            this.finBottomRight.zRot = Mth.cos(limbSwing * speed * 0.4F) * degree * -0.4F * limbSwingAmount + 0.5f;
            this.finPectoralLeft.zRot = Mth.cos(2.0F + 20 * speed * 0.4F) * 1.2F * 0.4F * limbSwingAmount + 0.4F;
            this.finPectoralRight.zRot = Mth.cos(2.0F + 20 * speed * 0.4F) * degree * -0.4F * limbSwingAmount - 0.4F;
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public static class Adult extends HumpheadParrotfishModel {
        @Override
        protected void setAngles() {
            this.texWidth = 104;
            this.texHeight = 52;
            this.finAnal = new ModelPart(this, 0, 23);
            this.finAnal.setPos(0.0F, 4.5F, 6.0F);
            this.finAnal.addBox(0.0F, -1.0F, -4.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
            this.finBottomRight = new ModelPart(this, 0, 37);
            this.finBottomRight.setPos(-1.0F, 5.5F, -3.0F);
            this.finBottomRight.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.body = new ModelPart(this, 0, 0);
            this.body.setPos(0.0F, 18.5F, 0.0F);
            this.body.addBox(-3.0F, -5.5F, -11.0F, 6.0F, 11.0F, 22.0F, 0.0F, 0.0F, 0.0F);
            this.finDorsal = new ModelPart(this, 38, 17);
            this.finDorsal.setPos(0.0F, -3.5F, 1.5F);
            this.finDorsal.addBox(0.0F, -5.0F, -6.5F, 0.0F, 6.0F, 18.0F, 0.0F, 0.0F, 0.0F);
            this.finPectoralRight = new ModelPart(this, 0, 15);
            this.finPectoralRight.setPos(-3.0F, 3.0F, -1.0F);
            this.finPectoralRight.addBox(-5.0F, 0.0F, -3.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
            this.finBottomLeft = new ModelPart(this, 0, 37);
            this.finBottomLeft.mirror = true;
            this.finBottomLeft.setPos(1.0F, 5.5F, -3.0F);
            this.finBottomLeft.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.finPectoralLeft = new ModelPart(this, 0, 15);
            this.finPectoralLeft.mirror = true;
            this.finPectoralLeft.setPos(3.0F, 3.0F, -1.0F);
            this.finPectoralLeft.addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
            this.hump = new ModelPart(this, 0, 0);
            this.hump.setPos(0.0F, -2.5F, -8.0F);
            this.hump.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
            this.tail = new ModelPart(this, 38, 0);
            this.tail.setPos(0.0F, 0.0F, 11.0F);
            this.tail.addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.finTail = new ModelPart(this, 21, 26);
            this.finTail.setPos(0.0F, 1.0F, 3.0F);
            this.finTail.addBox(0.0F, -6.5F, 0.0F, 0.0F, 11.0F, 8.0F, 0.0F, 0.0F, 0.0F);
            this.body.addChild(this.finAnal);
            this.body.addChild(this.finBottomRight);
            this.body.addChild(this.finDorsal);
            this.body.addChild(this.finPectoralRight);
            this.body.addChild(this.finBottomLeft);
            this.body.addChild(this.finPectoralLeft);
            this.body.addChild(this.hump);
            this.body.addChild(this.tail);
            this.tail.addChild(this.finTail);
        }
    }

    public static class Child extends HumpheadParrotfishModel {
        @Override
        protected void setAngles() {
            this.texWidth = 34;
            this.texHeight = 12;
            this.dorsalfinBaby = new ModelPart(this, 18, -4);
            this.dorsalfinBaby.setPos(0.0F, -1.5F, 1.0F);
            this.dorsalfinBaby.addBox(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.pelvicfinrightBaby = new ModelPart(this, 27, -2);
            this.pelvicfinrightBaby.mirror = true;
            this.pelvicfinrightBaby.setPos(-0.5F, 1.5F, -1.5F);
            this.pelvicfinrightBaby.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(pelvicfinrightBaby, 0.0F, 0.0F, 0.5235987755982988F);
            this.pelvicfinleftBaby = new ModelPart(this, 27, -2);
            this.pelvicfinleftBaby.setPos(0.5F, 1.5F, -1.5F);
            this.pelvicfinleftBaby.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(pelvicfinleftBaby, 0.0F, 0.0F, -0.5235987755982988F);
            this.bodyBaby = new ModelPart(this, 0, 0);
            this.bodyBaby.setPos(0.0F, 22.5F, 0.0F);
            this.bodyBaby.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
            this.analfinBaby = new ModelPart(this, 18, 0);
            this.analfinBaby.setPos(0.0F, 1.5F, 1.5F);
            this.analfinBaby.addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.tailBaby = new ModelPart(this, 11, -3);
            this.tailBaby.setPos(0.0F, 0.0F, 3.0F);
            this.tailBaby.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.bodyBaby.addChild(this.dorsalfinBaby);
            this.bodyBaby.addChild(this.pelvicfinrightBaby);
            this.bodyBaby.addChild(this.pelvicfinleftBaby);
            this.bodyBaby.addChild(this.analfinBaby);
            this.bodyBaby.addChild(this.tailBaby);
        }
    }
}
