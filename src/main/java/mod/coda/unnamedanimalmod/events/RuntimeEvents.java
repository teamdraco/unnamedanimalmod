package mod.coda.unnamedanimalmod.events;

import mod.coda.unnamedanimalmod.block.RichFarmlandBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RuntimeEvents
{
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
