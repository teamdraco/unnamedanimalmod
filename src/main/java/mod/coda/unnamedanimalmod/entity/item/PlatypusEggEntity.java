package mod.coda.unnamedanimalmod.entity.item;

import mod.coda.unnamedanimalmod.entity.PlatypusEntity;
import mod.coda.unnamedanimalmod.init.UAMEntities;
import mod.coda.unnamedanimalmod.init.UAMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class PlatypusEggEntity extends ProjectileItemEntity {
    public PlatypusEggEntity(EntityType<? extends PlatypusEggEntity> p_i50154_1_, World p_i50154_2_) {
        super(p_i50154_1_, p_i50154_2_);
    }

    public PlatypusEggEntity(World worldIn, LivingEntity throwerIn) {
        super(UAMEntities.PLATYPUS_EGG.get(), throwerIn, worldIn);
    }

    public PlatypusEggEntity(World worldIn, double x, double y, double z) {
        super(UAMEntities.PLATYPUS_EGG.get(), x, y, z, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        p_213868_1_.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0F);
    }

    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            if (this.rand.nextInt(3) == 0) {
                int i = 1;

                for(int j = 0; j < i; ++j) {
                    PlatypusEntity platypusentity = UAMEntities.PLATYPUS.get().create(this.world);
                    platypusentity.setGrowingAge(-24000);
                    platypusentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                    this.world.addEntity(platypusentity);
                }
            }

            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }

    protected Item getDefaultItem() {
        return UAMItems.PLATYPUS_EGG.get();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(UAMItems.PLATYPUS_EGG.get());
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
