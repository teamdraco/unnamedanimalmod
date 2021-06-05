package teamdraco.unnamedanimalmod.common.entity.item;

import teamdraco.unnamedanimalmod.common.entity.GreaterPrairieChickenEntity;
import teamdraco.unnamedanimalmod.init.UAMEntities;
import teamdraco.unnamedanimalmod.init.UAMItems;
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

public class GreaterPrairieChickenEggEntity extends ProjectileItemEntity {
    public GreaterPrairieChickenEggEntity(EntityType<? extends GreaterPrairieChickenEggEntity> p_i50154_1_, World p_i50154_2_) {
        super(p_i50154_1_, p_i50154_2_);
    }

    public GreaterPrairieChickenEggEntity(World worldIn, LivingEntity throwerIn) {
        super(UAMEntities.GREATER_PRAIRIE_CHICKEN_EGG.get(), throwerIn, worldIn);
    }

    public GreaterPrairieChickenEggEntity(World worldIn, double x, double y, double z) {
        super(UAMEntities.GREATER_PRAIRIE_CHICKEN_EGG.get(), x, y, z, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for(int i = 0; i < 6; ++i) {
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    GreaterPrairieChickenEntity chickenentity = UAMEntities.GREATER_PRAIRIE_CHICKEN.get().create(this.level);
                    chickenentity.setAge(-24000);
                    chickenentity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                    this.level.addFreshEntity(chickenentity);
                }
            }

            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }

    }

    protected Item getDefaultItem() {
        return UAMItems.GREATER_PRAIRIE_CHICKEN_EGG.get();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(UAMItems.GREATER_PRAIRIE_CHICKEN_EGG.get());
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
