package mod.coda.unnamedanimalmod.entity;

import mod.coda.unnamedanimalmod.entity.goals.HumpbackBreachGoal;
import mod.coda.unnamedanimalmod.init.ItemInit;
import mod.coda.unnamedanimalmod.init.ModEntityTypes;
import mod.coda.unnamedanimalmod.init.SoundInit;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HumpbackWhaleEntity extends AnimalEntity {
    protected boolean noBlow = false;

    public HumpbackWhaleEntity(EntityType<? extends HumpbackWhaleEntity> type, World world) {
        super(type, world);
        this.moveController = new HumpbackWhaleEntity.MoveHelperController(this);
        this.lookController = new DolphinLookController(this, 10);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreatheAirGoal(this));
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(5, new HumpbackBreachGoal(this, 10));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
        this.goalSelector.addGoal(9, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.2F);
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setAir(this.getMaxAir());
        this.rotationPitch = 0.0F;
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        HumpbackWhaleEntity entity = new HumpbackWhaleEntity(ModEntityTypes.HUMPBACK_WHALE, this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
        return entity;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.SALMON;
    }

    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isChild() ? size.height * 0.75F : size.height * 0.85F;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (item instanceof SpawnEggItem && ((SpawnEggItem) item).hasType(heldItem.getTag(), this.getType())) {
            HumpbackWhaleEntity child = ModEntityTypes.HUMPBACK_WHALE.create(world);
            if (child != null) {
                child.setGrowingAge(-24000);
                child.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0.0F, 0.0F);
                this.world.addEntity(child);
                if (heldItem.hasDisplayName()) {
                    child.setCustomName(heldItem.getDisplayName());
                }
                this.onChildSpawnFromEgg(player, child);
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
            }
            return true;
        }
        if (this.isBreedingItem(heldItem)) {
            if (this.getGrowingAge() == 0 && this.canBreed()) {
                this.consumeItemFromStack(player, heldItem);
                this.setInLove(player);
                player.swing(hand, true);
            }
            return true;
        }
        if (this.isChild()) {
            this.consumeItemFromStack(player, new ItemStack(Items.SALMON));
            this.ageUp((int) (this.getGrowingAge() / -20.0 * 0.1), true);
            return true;
        }
        return false;
    }

    public void tick() {
        super.tick();
        BlockPos pos = getPosition().up();

        if (!noBlow && world.getBlockState(pos).getBlock() == Blocks.AIR && inWater){
            playBlowAnimation();
            noBlow = true;
        }
        if (inWater && world.getBlockState(pos).getBlock() != Blocks.AIR){
            noBlow = false;
        }
    }

    protected void playBlowAnimation(){
        int blowHeight = 10;
        int intensity = 40;
        if (this.world.isRemote) {
            double d0 = 0;
            double d1 = 0;
            double d2 = 0;

            for (int i = 0; i < blowHeight + 3; i++) {
                for(int b = 0; b < intensity; ++b) {
                    this.world.addParticle(ParticleTypes.FALLING_WATER,
                            this.getPosX() - MathHelper.sin(-renderYawOffset * 0.017453292F)
                                    + (MathHelper.sin(-renderYawOffset * 0.017453292F) * 2.8F),
                            this.getPosY() + 1.4F + (i * 0.4F), this.getPosZ() - MathHelper.cos(renderYawOffset * 0.017453292F)
                                    + (MathHelper.cos(renderYawOffset * 0.017453292F) * 2.8F),
                            d0, d1, d2);
                }
            }

            for (int i = 0; i < 3; i++) {
                this.world.addParticle(ParticleTypes.CLOUD,
                        this.getPosX() - MathHelper.sin(-renderYawOffset * 0.017453292F)
                                + (MathHelper.sin(-renderYawOffset * 0.017453292F) * 2.8F),
                        this.getPosY() + 1.4F + (i * 0.4F), this.getPosZ() - MathHelper.cos(renderYawOffset * 0.017453292F)
                                + (MathHelper.cos(renderYawOffset * 0.017453292F) * 2.8F),
                        d0, d1, d2);
            }
        }
        playSound(SoundInit.HUMPBACK_WHALE_SONG.get(), 5.0f, 1.0f);
    }

    public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(p_213352_1_);
        }
    }

    static class MoveHelperController extends MovementController {
        private final HumpbackWhaleEntity whale;

        public MoveHelperController(HumpbackWhaleEntity whaleIn) {
            super(whaleIn);
            this.whale = whaleIn;
        }

        public void tick() {
            if (this.whale.isInWater()) {
                this.whale.setMotion(this.whale.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.whale.getNavigator().noPath()) {
                double d0 = this.posX - this.whale.getPosX();
                double d1 = this.posY - this.whale.getPosY();
                double d2 = this.posZ - this.whale.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setMoveForward(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.whale.rotationYaw = this.limitAngle(this.whale.rotationYaw, f, 10.0F);
                    this.whale.renderYawOffset = this.whale.rotationYaw;
                    this.whale.rotationYawHead = this.whale.rotationYaw;
                    float f1 = (float)(this.speed * this.whale.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                    if (this.whale.isInWater()) {
                        this.whale.setAIMoveSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.whale.rotationPitch = this.limitAngle(this.whale.rotationPitch, f2, 5.0F);
                        float f3 = MathHelper.cos(this.whale.rotationPitch * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.whale.rotationPitch * ((float)Math.PI / 180F));
                        this.whale.moveForward = f3 * f1;
                        this.whale.moveVertical = -f4 * f1;
                    } else {
                        this.whale.setAIMoveSpeed(f1 * 0.1F);
                    }
                }
            } else {
                this.whale.setAIMoveSpeed(0.0F);
                this.whale.setMoveStrafing(0.0F);
                this.whale.setMoveVertical(0.0F);
                this.whale.setMoveForward(0.0F);
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundInit.HUMPBACK_WHALE_AMBIENT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundInit.HUMPBACK_WHALE_DEATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundInit.HUMPBACK_WHALE_HURT.get();
    }
}