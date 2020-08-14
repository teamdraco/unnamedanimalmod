package mod.coda.unnamedanimalmod.client.model;

import com.google.common.collect.ImmutableList;
import mod.coda.unnamedanimalmod.entity.PacmanFrogEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class PacmanFrogEntityModel<T extends Entity> extends SegmentedModel<PacmanFrogEntity> {
    public ModelRenderer body;
    public ModelRenderer head;
    public ModelRenderer backlegright;
    public ModelRenderer backlegleft;
    public ModelRenderer frontlegright;
    public ModelRenderer frontlegleft;
    public ModelRenderer eyeright;
    public ModelRenderer eyeleft;
    public ModelRenderer tailTadpole;
    public ModelRenderer bodyTadpole;
    private float jumpRotation;

    @Override
    public Iterable<ModelRenderer> getParts() {
        if (this.isChild) {
            return ImmutableList.of(bodyTadpole);
        } else {
            return ImmutableList.of(body);
        }
    }

    protected abstract void setAngles();

    public void setRotationAngles(PacmanFrogEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float)entityIn.ticksExisted;

        if(this.isChild) {

        } else {
            this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
            this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
            this.backlegleft.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
            this.backlegright.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
            this.frontlegleft.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
            this.frontlegright.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public PacmanFrogEntityModel() {
        setAngles();
    }

   public static class Adult extends PacmanFrogEntityModel {
       @Override
       protected void setAngles() {
           this.textureWidth = 26;
           this.textureHeight = 10;
           this.head = new ModelRenderer(this, 16, 0);
           this.head.setRotationPoint(0.0F, -0.5F, -2.0F);
           this.head.addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(head, 0.17453292519943295F, 0.0F, 0.0F);
           this.backlegright = new ModelRenderer(this, 0, 7);
           this.backlegright.setRotationPoint(-2.0F, 1.5F, 1.5F);
           this.backlegright.addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(backlegright, 0.17453292519943295F, 0.0F, 0.0F);
           this.frontlegleft = new ModelRenderer(this, 0, 0);
           this.frontlegleft.setRotationPoint(2.0F, 1.0F, -1.5F);
           this.frontlegleft.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(frontlegleft, 0.17453292519943295F, 0.0F, 0.0F);
           this.frontlegright = new ModelRenderer(this, 0, 0);
           this.frontlegright.setRotationPoint(-2.0F, 1.0F, -1.5F);
           this.frontlegright.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(frontlegright, 0.17453292519943295F, 0.0F, 0.0F);
           this.backlegleft = new ModelRenderer(this, 0, 7);
           this.backlegleft.setRotationPoint(2.0F, 1.5F, 1.5F);
           this.backlegleft.addBox(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(backlegleft, 0.17453292519943295F, 0.0F, 0.0F);
           this.eyeright = new ModelRenderer(this, 14, 0);
           this.eyeright.mirror = true;
           this.eyeright.setRotationPoint(-0.9F, -0.5F, -1.0F);
           this.eyeright.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
           this.body = new ModelRenderer(this, 0, 0);
           this.body.setRotationPoint(0.0F, 21.4F, 0.0F);
           this.body.addBox(-2.5F, -1.5F, -2.0F, 5.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
           this.setRotateAngle(body, -0.17453292519943295F, 0.0F, 0.0F);
           this.eyeleft = new ModelRenderer(this, 14, 0);
           this.eyeleft.setRotationPoint(0.9F, -0.5F, -1.0F);
           this.eyeleft.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
           this.body.addChild(this.head);
           this.body.addChild(this.backlegright);
           this.body.addChild(this.frontlegleft);
           this.body.addChild(this.frontlegright);
           this.body.addChild(this.backlegleft);
           this.head.addChild(this.eyeright);
           this.head.addChild(this.eyeleft);
       }
   }

    public static class Child extends PacmanFrogEntityModel {
        @Override
        protected void setAngles() {
            this.textureWidth = 10;
            this.textureHeight = 7;
            this.tailTadpole = new ModelRenderer(this, 1, 2);
            this.tailTadpole.setRotationPoint(0.0F, 0.0F, 1.5F);
            this.tailTadpole.addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.bodyTadpole = new ModelRenderer(this, 0, 0);
            this.bodyTadpole.setRotationPoint(0.0F, 23.0F, -1.0F);
            this.bodyTadpole.addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
            this.bodyTadpole.addChild(this.tailTadpole);
        }
    }
}
