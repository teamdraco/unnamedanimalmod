package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.SouthernRightWhaleEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class SouthernRightWhaleModel<T extends Entity> extends AgeableModel<SouthernRightWhaleEntity> {
    public ModelPart body;
    public ModelPart head;
    public ModelPart tail_stock1;
    public ModelPart right_fin;
    public ModelPart left_fin;
    public ModelPart jaw;
    public ModelPart tail_stock2;
    public ModelPart flukes;

    public SouthernRightWhaleModel() {
        this.texWidth = 512;
        this.texHeight = 304;
        this.tail_stock1 = new ModelPart(this, 256, 200);
        this.tail_stock1.setPos(0.0F, -14.5F, 46.0F);
        this.tail_stock1.addBox(-22.0F, -22.5F, -2.0F, 44.0F, 45.0F, 58.0F, 0.0F, 0.0F, 0.0F);
        this.right_fin = new ModelPart(this, 238, 0);
        this.right_fin.setPos(38.0F, 15.0F, -25.0F);
        this.right_fin.addBox(0.0F, -2.0F, -11.0F, 40.0F, 4.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.flukes = new ModelPart(this, 0, 154);
        this.flukes.setPos(0.0F, -3.0F, 26.0F);
        this.flukes.addBox(-36.0F, -2.0F, 0.0F, 72.0F, 4.0F, 28.0F, 0.0F, 0.0F, 0.0F);
        this.jaw = new ModelPart(this, 0, 186);
        this.jaw.setPos(-2.0F, 16.0F, -3.0F);
        this.jaw.addBox(-29.0F, -24.0F, -61.0F, 62.0F, 33.0F, 66.0F, 0.0F, 0.0F, 0.0F);
        this.left_fin = new ModelPart(this, 238, 0);
        this.left_fin.mirror = true;
        this.left_fin.setPos(-36.0F, 15.0F, -25.0F);
        this.left_fin.addBox(-40.0F, -2.0F, -11.0F, 40.0F, 4.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.tail_stock2 = new ModelPart(this, 238, 26);
        this.tail_stock2.setPos(0.0F, -6.5F, 55.0F);
        this.tail_stock2.addBox(-11.0F, -13.0F, 0.0F, 22.0F, 26.0F, 38.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 266, 92);
        this.head.setPos(1.0F, -8.0F, -44.0F);
        this.head.addBox(-29.0F, -24.0F, -62.0F, 58.0F, 46.0F, 62.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 0.0F, 0.0F);
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
    protected Iterable<ModelPart> headParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setupAnim(SouthernRightWhaleEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float motionY = (float) entityIn.getDeltaMovement().y;

        this.body.xRot = headPitch * ((float)Math.PI / 180F);
        this.body.yRot = netHeadYaw * ((float)Math.PI / 180F);

        if (Entity.distanceToSqr(entityIn.getDeltaMovement()) > 1.0E-7D) {
            if(!entityIn.isInWater() && !entityIn.isOnGround()) {
                this.body.zRot = (float) Math.toRadians(-motionY * 180) * 0.8f;
            }
            else {
                float speed = 0.6f;
                float degree = 0.6f;
                this.body.zRot = 0;
                this.body.xRot += Mth.cos(f * speed * 0.4F) * degree * 0.25F * f1 + 0.01F;
                this.right_fin.zRot = Mth.cos(1.0F + f * speed * 0.4F) * degree * 0.6F * f1 + 0.2F;
                this.left_fin.zRot = Mth.cos(1.0F + f * speed * 0.4F) * degree * -0.6F * f1 - 0.2F;
                this.tail_stock1.xRot = Mth.cos(-1.0F + f * speed * 0.4F) * degree * 0.5F * f1;
                this.tail_stock2.xRot = Mth.cos(-2.0F + f * speed * 0.4F) * degree * 0.5F * f1;
                this.flukes.xRot = Mth.cos(-2.0F + f * speed * 0.4F) * degree * 0.8F * f1;
                this.head.xRot = Mth.cos(1.0F + f * speed * 0.4F) * degree * 0.1F * f1;
                this.jaw.xRot = Mth.cos(1.0F + f * speed * 0.4F) * degree * 0.1F * f1 + 0.05F;
            }
        }
        else {
            this.body.zRot = 0;
            this.left_fin.yRot = 0;
            this.left_fin.zRot = 0;
            this.right_fin.yRot = 0;
            this.right_fin.zRot = 0;
            this.tail_stock1.xRot = 0;
            this.tail_stock2 .xRot = 0;
            this.flukes.xRot = 0;
            this.body.xRot = 0;
            this.jaw.xRot = 0;
            this.head.xRot = 0;
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
