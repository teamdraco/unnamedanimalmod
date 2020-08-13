package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.PigNosedTurtleEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;

@OnlyIn(Dist.CLIENT)
public class PigNosedTurtleEntityModel<T extends Entity> extends AgeableModel<PigNosedTurtleEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer armLeft;
    public ModelRenderer armRight;
    public ModelRenderer legLeft;
    public ModelRenderer legRight;
    public ModelRenderer nose;

    public PigNosedTurtleEntityModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.head = new ModelRenderer(this, 0, 17);
        this.head.setRotationPoint(0.0F, 0.5F, -5.0F);
        this.head.addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 0, 14);
        this.nose.setRotationPoint(0.0F, 0.5F, -4.0F);
        this.nose.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.legLeft = new ModelRenderer(this, 0, 24);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(2.5F, 1.5F, 3.5F);
        this.legLeft.addBox(0.0F, -0.5F, -2.0F, 4.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legLeft, 0.0F, -0.8726646259971648F, 0.0F);
        this.armRight = new ModelRenderer(this, 0, 28);
        this.armRight.setRotationPoint(-1.5F, 1.5F, -4.5F);
        this.armRight.addBox(-5.0F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(armRight, 0.0F, -0.3490658503988659F, 0.0F);
        this.armLeft = new ModelRenderer(this, 0, 28);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(1.5F, 1.5F, -4.5F);
        this.armLeft.addBox(0.0F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(armLeft, 0.0F, 0.3490658503988659F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.body.addBox(-4.0F, -2.0F, -5.0F, 8.0F, 4.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 24);
        this.legRight.setRotationPoint(-2.5F, 1.5F, 3.5F);
        this.legRight.addBox(-4.0F, -0.5F, -2.0F, 4.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(legRight, 0.0F, 0.8726646259971648F, 0.0F);
        this.body.addChild(this.head);
        this.head.addChild(this.nose);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.armRight);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.legRight);
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
    public void setRotationAngles(PigNosedTurtleEntity entityIn, float f, float f1, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
//        f1 = 1.5f;

        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        if(entityIn.getMotion().getX() != 0 && entityIn.getMotion().getY() != 0 && entityIn.getMotion().getZ() != 0) {
            this.body.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.2F * f1;
            this.armLeft.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1 + 0.2F;
            this.armRight.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 0.2F;
            this.legLeft.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * 0.4F * f1 - 0.7F;
            this.legRight.rotateAngleY = MathHelper.cos(f * speed * 0.4F) * degree * -0.4F * f1 + 0.7F;
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
