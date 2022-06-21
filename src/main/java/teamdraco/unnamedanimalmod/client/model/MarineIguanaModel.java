package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class MarineIguanaModel<T extends Entity> extends AgeableListModel<T> {
    public ModelPart body;
    public ModelPart tailBase;
    public ModelPart armLeft;
    public ModelPart spinesBody;
    public ModelPart head;
    public ModelPart armRight;
    public ModelPart legLeft;
    public ModelPart legRight;
    public ModelPart tail;
    public ModelPart spinesHead;

    public MarineIguanaModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.tailBase = new ModelPart(this, 34, 0);
        this.tailBase.setPos(0.0F, 0.0F, 3.5F);
        this.tailBase.addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tailBase, -0.19198621771937624F, 0.0F, 0.0F);
        this.legLeft = new ModelPart(this, 54, 0);
        this.legLeft.setPos(1.5F, 0.5F, 2.0F);
        this.legLeft.addBox(-1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legLeft, 0.6981317007977318F, 0.0F, -0.2617993877991494F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 21.0F, 0.0F);
        this.body.addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, -0.03490658503988659F, 0.0F, 0.0F);
        this.spinesHead = new ModelPart(this, 24, 16);
        this.spinesHead.setPos(0.0F, -1.5F, 0.0F);
        this.spinesHead.addBox(0.0F, -2.0F, -3.0F, 0.02F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 11);
        this.head.setPos(0.0F, 0.0F, -4.0F);
        this.head.addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.08726646259971647F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 30, 10);
        this.tail.setPos(0.0F, -0.5F, 6.5F);
        this.tail.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.13962634015954636F, 0.0F, 0.0F);
        this.spinesBody = new ModelPart(this, 45, 7);
        this.spinesBody.setPos(0.02F, -1.5F, 0.0F);
        this.spinesBody.addBox(0.0F, -1.0F, -3.5F, 0.0F, 1.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.armLeft = new ModelPart(this, 46, 0);
        this.armLeft.setPos(2.0F, 0.0F, -2.0F);
        this.armLeft.addBox(-1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(armLeft, -0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.legRight = new ModelPart(this, 54, 0);
        this.legRight.mirror = true;
        this.legRight.setPos(-1.5F, 0.5F, 2.0F);
        this.legRight.addBox(-0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legRight, 0.6981317007977318F, 0.0F, 0.2617993877991494F);
        this.armRight = new ModelPart(this, 46, 0);
        this.armRight.mirror = true;
        this.armRight.setPos(-2.0F, 0.0F, -2.0F);
        this.armRight.addBox(-0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(armRight, -0.17453292519943295F, 0.0F, 0.17453292519943295F);
        this.body.addChild(this.tailBase);
        this.body.addChild(this.legLeft);
        this.head.addChild(this.spinesHead);
        this.body.addChild(this.head);
        this.tailBase.addChild(this.tail);
        this.body.addChild(this.spinesBody);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.legRight);
        this.body.addChild(this.armRight);
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
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isInWater()) {
            float speed = 1.0f;
            float degree = 0.8f;
            this.armLeft.zRot = Mth.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 1.0F;
            this.armLeft.xRot = Mth.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.5F * limbSwingAmount + 0.8F;
            this.armRight.zRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 1.0F;
            this.armRight.xRot = Mth.cos(0.5F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 0.8F;
            this.body.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.head.yRot = Mth.cos(1.8F + limbSwing * speed * 0.4F) * degree * 0.4F * limbSwingAmount;
            this.body.xRot = 0.0F;
            this.head.xRot = -0.1F;
            this.legLeft.zRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount - 1.0F;
            this.legLeft.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.5F * limbSwingAmount + 1.2F;
            this.legRight.zRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount + 1.0F;
            this.legRight.xRot = Mth.cos(1.5F + limbSwing * speed * 0.4F) * degree * -0.5F * limbSwingAmount + 1.2F;
            this.tailBase.xRot = 0.05F;
            this.tailBase.yRot = Mth.cos(1.0F + limbSwing * speed * 0.4F) * degree * 0.5F * limbSwingAmount;
            this.tail.xRot = 0.0F;
            this.tail.yRot = Mth.cos(-0.5F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount;
        }

        else {
            limbSwingAmount = Mth.clamp(limbSwingAmount, -0.35f, 0.35f);
            float speed = 5.0f;
            float degree = 3.0f;
            this.body.yRot = Mth.cos(limbSwing * speed * 0.4F) * degree * 0.2F * limbSwingAmount;
            this.armRight.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount - 0.2F;
            this.armLeft.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount - 0.2F;
            this.legRight.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * -0.8F * limbSwingAmount + 0.3F;
            this.legLeft.xRot = Mth.cos(2.0F + limbSwing * speed * 0.4F) * degree * 0.8F * limbSwingAmount + 0.3F;
            this.head.yRot = Mth.cos(3.0F + limbSwing * speed * 0.2F) * 1.0F * 0.3F * limbSwingAmount;
            this.head.xRot = Mth.cos(3.0F + limbSwing * speed * 0.4F) * 1.0F * 0.4F * limbSwingAmount;
            this.tailBase.yRot = Mth.cos(limbSwing * speed * 0.2F) * 1.0F * 0.8F * limbSwingAmount;
            this.tailBase.xRot = Mth.cos(limbSwing * speed * 0.4F) * 1.0F * 0.4F * limbSwingAmount - 0.15F;
            this.tail.yRot = Mth.cos(limbSwing * speed * 0.2F) * 1.0F * 0.8F * limbSwingAmount;
            this.tail.xRot = Mth.cos(limbSwing * speed * 0.4F) * 1.0F * 0.4F * limbSwingAmount + 0.2F;
            this.armLeft.zRot = -0.17453292519943295F;
            this.legLeft.zRot = -0.2617993877991494F;
            this.armRight.zRot = 0.17453292519943295F;
            this.legRight.zRot = 0.2617993877991494F;
            this.body.y = 20.0F;
        }
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
