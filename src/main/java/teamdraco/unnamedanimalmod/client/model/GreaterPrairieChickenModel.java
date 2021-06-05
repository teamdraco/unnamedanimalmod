package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.GreaterPrairieChickenEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class GreaterPrairieChickenModel<T extends Entity> extends AgeableModel<GreaterPrairieChickenEntity> {
    public ModelRenderer body;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer wingLeft;
    public ModelRenderer wingRight;
    public ModelRenderer head;
    public ModelRenderer tail;
    public ModelRenderer beak;
    public ModelRenderer earLeft;
    public ModelRenderer cheeks;
    public ModelRenderer earRight;

    public GreaterPrairieChickenModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.legRight = new ModelRenderer(this, 37, 0);
        this.legRight.setPos(-2.0F, 3.0F, 1.0F);
        this.legRight.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 21, 1);
        this.head.setPos(0.0F, 1.0F, -4.0F);
        this.head.addBox(-1.5F, -7.0F, -1.0F, 3.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.5235987755982988F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 0, 11);
        this.beak.setPos(0.0F, -4.5F, -1.0F);
        this.beak.addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(beak, -0.4363323129985824F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 0, 23);
        this.tail.setPos(0.0F, -2.0F, 3.6F);
        this.tail.addBox(-2.0F, -0.5F, 0.0F, 4.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 1.1344640137963142F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.setPos(0.0F, 17.0F, 0.0F);
        this.body.addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelRenderer(this, 14, -2);
        this.earLeft.setPos(0.5F, -7.0F, 1.5F);
        this.earLeft.addBox(0.5F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earLeft, 0.0F, -0.6108652381980153F, 0.17453292519943295F);
        this.legLeft = new ModelRenderer(this, 37, 0);
        this.legLeft.mirror = true;
        this.legLeft.setPos(1.0F, 3.0F, 1.0F);
        this.legLeft.addBox(-1.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.wingLeft = new ModelRenderer(this, 28, 13);
        this.wingLeft.setPos(3.0F, -3.0F, 0.0F);
        this.wingLeft.addBox(0.0F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.wingRight = new ModelRenderer(this, 28, 13);
        this.wingRight.setPos(-3.0F, -3.0F, 0.0F);
        this.wingRight.addBox(-1.0F, 0.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.cheeks = new ModelRenderer(this, 0, 0);
        this.cheeks.setPos(1.0F, -2.5F, 1.0F);
        this.cheeks.addBox(-3.5F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(cheeks, 0.4363323129985824F, 0.0F, 0.0F);
        this.earRight = new ModelRenderer(this, 14, -2);
        this.earRight.setPos(-0.5F, -7.0F, 1.5F);
        this.earRight.addBox(-0.5F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(earRight, 0.0F, 0.6108652381980153F, -0.17453292519943295F);
        this.body.addChild(this.legRight);
        this.body.addChild(this.head);
        this.head.addChild(this.beak);
        this.body.addChild(this.tail);
        this.head.addChild(this.earLeft);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.wingLeft);
        this.body.addChild(this.wingRight);
        this.head.addChild(this.cheeks);
        this.head.addChild(this.earRight);
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
    public void setupAnim(GreaterPrairieChickenEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        this.body.xRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.2F * f1;
        this.head.xRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.2F * f1 + 0.5F;
        this.legRight.xRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 1.0F * f1;
        this.legLeft.xRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * -1.0F * f1;
        this.tail.xRot = MathHelper.cos(1.0F + f * speed * 0.4F) * degree * 0.4F * f1 + 1.1F;
        this.wingRight.zRot = ageInTicks;
        this.wingLeft.zRot = -ageInTicks;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
