package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.HumpheadParrotfishEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class HumpheadParrotfishEntityModel<T extends Entity> extends AgeableModel<HumpheadParrotfishEntity> {
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

    public HumpheadParrotfishEntityModel() {
        this.textureWidth = 104;
        this.textureHeight = 52;
        this.finTail = new ModelRenderer(this, 21, 26);
        this.finTail.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.finTail.addBox(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 18.5F, 0.0F);
        this.body.addBox(-3.0F, -5.5F, -11.0F, 6.0F, 11.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.finBottomLeft = new ModelRenderer(this, 0, 37);
        this.finBottomLeft.mirror = true;
        this.finBottomLeft.setRotationPoint(1.0F, 5.5F, -3.0F);
        this.finBottomLeft.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelRenderer(this, 38, 0);
        this.tail.setRotationPoint(0.0F, 0.0F, 11.0F);
        this.tail.addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.finAnal = new ModelRenderer(this, 0, 24);
        this.finAnal.setRotationPoint(0.0F, 5.5F, 6.0F);
        this.finAnal.addBox(0.0F, -1.0F, -4.0F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.finDorsal = new ModelRenderer(this, 38, 18);
        this.finDorsal.setRotationPoint(0.0F, -3.5F, 1.5F);
        this.finDorsal.addBox(0.0F, -5.0F, -6.5F, 0.0F, 6.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.hump = new ModelRenderer(this, 0, 0);
        this.hump.setRotationPoint(0.0F, -2.5F, -8.0F);
        this.hump.addBox(-2.0F, -5.0F, -5.0F, 4.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.finBottomRight = new ModelRenderer(this, 0, 37);
        this.finBottomRight.setRotationPoint(-1.0F, 5.5F, -3.0F);
        this.finBottomRight.addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.finPectoralLeft = new ModelRenderer(this, 0, 15);
        this.finPectoralLeft.mirror = true;
        this.finPectoralLeft.setRotationPoint(3.0F, 3.0F, -4.0F);
        this.finPectoralLeft.addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.finPectoralRight = new ModelRenderer(this, 0, 15);
        this.finPectoralRight.setRotationPoint(-3.0F, 3.0F, -4.0F);
        this.finPectoralRight.addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.tail.addChild(this.finTail);
        this.body.addChild(this.finBottomLeft);
        this.body.addChild(this.tail);
        this.body.addChild(this.finAnal);
        this.body.addChild(this.finDorsal);
        this.body.addChild(this.hump);
        this.body.addChild(this.finBottomRight);
        this.body.addChild(this.finPectoralLeft);
        this.body.addChild(this.finPectoralRight);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setRotationAngles(HumpheadParrotfishEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 0.7f;

        this.body.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.2F * f1;
        this.tail.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1;
        this.finTail.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1;
        this.finBottomLeft.rotateAngleZ = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 0.5f;
        this.finBottomRight.rotateAngleZ = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1 + 0.5f;
        this.finPectoralLeft.rotateAngleZ = MathHelper.cos(2.0F + 20 * speed * 0.4F) * 1.2F * 0.4F * f1 + 0.4F;
        this.finPectoralRight.rotateAngleZ = MathHelper.cos(2.0F + 20 * speed * 0.4F) * degree * -0.4F * f1 - 0.4F;
    }
}
