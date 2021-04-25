package mod.coda.unnamedanimalmod.events;

import mod.coda.unnamedanimalmod.block.RichFarmlandBlock;
import mod.coda.unnamedanimalmod.init.UAMEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class RuntimeEvents
{
    @SubscribeEvent
    public void fruitRageAIGoal(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof MobEntity)
        {
            MobEntity entity = (MobEntity) event.getEntity();
            entity.goalSelector.addGoal(3, new NearestAttackableTargetGoal<LivingEntity>(entity, LivingEntity.class,true){
                @Override
                protected void findNearestTarget()
                {
                    nearestTarget = goalOwner.world.getClosestEntity(
                            goalOwner.world.getEntitiesWithinAABB(
                                    LivingEntity.class,
                                    getTargetableArea(getTargetDistance()),
                                    e -> e.getType().equals(goalOwner.getType())),
                            EntityPredicate.DEFAULT,goalOwner,
                            goalOwner.getPosX(),goalOwner.getPosY(),goalOwner.getPosZ());
                }
            });
        }
    }
    @SubscribeEvent
    public static void harvest(BlockEvent.BreakEvent event)
    {
        if (event.getWorld() instanceof ServerWorld)
        {
            BlockState cropState = event.getState();
            if (cropState.getBlock() instanceof CropsBlock)
            {
                CropsBlock block = (CropsBlock) cropState.getBlock();
                if (block.isMaxAge(cropState))
                {
                    ServerWorld world = (ServerWorld) event.getWorld();
                    BlockState farmlandState = world.getBlockState(event.getPos().down());
                    if (farmlandState.getBlock() instanceof RichFarmlandBlock)
                    {
                        List<ItemStack> drops = Block.getDrops(cropState, world, event.getPos(), null);
                        List<ItemStack> uniqueDrops = new ArrayList<>();
                        List<Item> addedItems = new ArrayList<>();
                        drops.forEach(s -> {
                            if (!addedItems.contains(s.getItem()))
                            {
                                addedItems.add(s.getItem());
                                uniqueDrops.add(s);
                            }
                        });
                        if (uniqueDrops.size() > 1)
                        {
                            uniqueDrops.forEach(s -> {
                                if (!(s.getItem() instanceof BlockItem && ((BlockItem) s.getItem()).getBlock() instanceof CropsBlock))
                                {
                                    ItemEntity entity = new ItemEntity(world, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5f, event.getPos().getZ() + 0.5f, s);
                                    world.addEntity(entity);
                                }
                            });
                        }
                        else
                        {
                            ItemEntity entity = new ItemEntity(world, event.getPos().getX() + 0.5f, event.getPos().getY() + 0.5f, event.getPos().getZ() + 0.5f, uniqueDrops.get(0));
                            world.addEntity(entity);
                        }
                    }
                }
            }
        }
    }
}
