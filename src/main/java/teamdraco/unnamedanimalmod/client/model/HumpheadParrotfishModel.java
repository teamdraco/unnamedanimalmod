package teamdraco.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import teamdraco.unnamedanimalmod.common.entity.HumpheadParrotfishEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class HumpheadParrotfishModel<T extends Entity> extends SegmentedModel<HumpheadParrotfishEntity> {
    public ModelRenderer body;
    public ModelRenderer hump;
    public ModelRenderer tail;
    public ModelRenderer finDorsal;
    public ModelRenderer finAnal;
    public ModelRenderer finBottomLeft;
    public ModelRenderer finBottomRight;
    public ModelRenderer finPectoralLeft;
    public ModelRenderer finPectoralRight;
    public ModelRenderer finTail;
    public ModelRenderer bodyBaby;
    public ModelRenderer tailBaby;
    public ModelRenderer dorsalfinBaby;
    public ModelRenderer analfinBaby;
    public ModelRenderer pelvicfinleftBaby;
    public ModelRenderer pelvicfinrightBaby;

    public HumpheadParrotfishModel() {
        setAngles();
    }

    protected abstract void setAngles();

    @Override
    public Iterable<ModelRenderer> parts() {
        if (this.young) {
            return ImmutableList.of(bodyBaby);
        } else {
            return ImmutableList.of(body);
        }
    }

    @Override
    public void setupAnim(HumpheadParrotfishEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.young) {
            float speed = 0.6f;
            float degree = 0.4f;
            this.bodyBaby.yRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.2F * f1;
            this.tailBaby.yRot = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1;
            this.pelvicfinleftBaby.zRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 0.5f;
            this.pelvicfinrightBaby.zRot = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1 + 0.5f;
        }
        else {
            float speed = 0.8f;
            float degree = 0.7f;
            this.body.yRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.2F * f1;
            this.tail.yRot = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1;
            this.finTail.yRot = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1;
            this.finBottomLeft.zRot = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 0.5f;
            this.finBottomRight.zRot = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1 + 0.5f;
            this.finPectoralLeft.zRot = MathHelper.cos(2.0F + 20 * speed * 0.4F) * 1.2F * 0.4F * f1 + 0.4F;
            this.finPectoralRight.zRot = MathHelper.cos(2.0F + 20 * speed * 0.4F) * degree * -0.4F * f1 - 0.4F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public static class Adult extends HumpheadParrotfishModel {
        @Override
        protected void setAngles() {
            this.texWidth = 104;
            this.texHeight = 52;
            this.finAnal = new ModelRenderer(this, 0, 23);
            this.finAnal.setPos(0.0F, 4.5F, 6.0F);
            this.finAnal.addBox(0.0F, -1.0F, -4.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
            this.finBottomRight = new ModelRenderer(this, 0, 37);
            this.finBottomRight.setPos(-1.0F, 5.5F, -3.0F);
            this.finBottomRight.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.body = new ModelRenderer(this, 0, 0);
            this.body.setPos(0.0F, 18.5F, 0.0F);
            this.body.addBox(-3.0F, -5.5F, -11.0F, 6.0F, 11.0F, 22.0F, 0.0F, 0.0F, 0.0F);
            this.finDorsal = new ModelRenderer(this, 38, 17);
            this.finDorsal.setPos(0.0F, -3.5F, 1.5F);
            this.finDorsal.addBox(0.0F, -5.0F, -6.5F, 0.0F, 6.0F, 18.0F, 0.0F, 0.0F, 0.0F);
            this.finPectoralRight = new ModelRenderer(this, 0, 15);
            this.finPectoralRight.setPos(-3.0F, 3.0F, -1.0F);
            this.finPectoralRight.addBox(-5.0F, 0.0F, -3.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
            this.finBottomLeft = new ModelRenderer(this, 0, 37);
            this.finBottomLeft.mirror = true;
            this.finBottomLeft.setPos(1.0F, 5.5F, -3.0F);
            this.finBottomLeft.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.finPectoralLeft = new ModelRenderer(this, 0, 15);
            this.finPectoralLeft.mirror = true;
            this.finPectoralLeft.setPos(3.0F, 3.0F, -1.0F);
            this.finPectoralLeft.addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
            this.hump = new ModelRenderer(this, 0, 0);
            this.hump.setPos(0.0F, -2.5F, -8.0F);
            this.hump.addBox(-2.0F, -6.0F, -2.0F, 4.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
            this.tail = new ModelRenderer(this, 38, 0);
            this.tail.setPos(0.0F, 0.0F, 11.0F);
            this.tail.addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.finTail = new ModelRenderer(this, 21, 26);
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
            this.dorsalfinBaby = new ModelRenderer(this, 18, -4);
            this.dorsalfinBaby.setPos(0.0F, -1.5F, 1.0F);
            this.dorsalfinBaby.addBox(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
            this.pelvicfinrightBaby = new ModelRenderer(this, 27, -2);
            this.pelvicfinrightBaby.mirror = true;
            this.pelvicfinrightBaby.setPos(-0.5F, 1.5F, -1.5F);
            this.pelvicfinrightBaby.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(pelvicfinrightBaby, 0.0F, 0.0F, 0.5235987755982988F);
            this.pelvicfinleftBaby = new ModelRenderer(this, 27, -2);
            this.pelvicfinleftBaby.setPos(0.5F, 1.5F, -1.5F);
            this.pelvicfinleftBaby.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            this.setRotateAngle(pelvicfinleftBaby, 0.0F, 0.0F, -0.5235987755982988F);
            this.bodyBaby = new ModelRenderer(this, 0, 0);
            this.bodyBaby.setPos(0.0F, 22.5F, 0.0F);
            this.bodyBaby.addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
            this.analfinBaby = new ModelRenderer(this, 18, 0);
            this.analfinBaby.setPos(0.0F, 1.5F, 1.5F);
            this.analfinBaby.addBox(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.tailBaby = new ModelRenderer(this, 11, -3);
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
