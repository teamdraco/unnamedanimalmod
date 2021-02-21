package mod.coda.unnamedanimalmod.data;

import mod.coda.unnamedanimalmod.init.UAMBlocks;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.tags.BlockTags.*;
import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    protected void registerTags()
    {
        getOrCreateBuilder(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        getOrCreateBuilder(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        getOrCreateBuilder(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        getOrCreateBuilder(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        getOrCreateBuilder(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        getOrCreateBuilder(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        getOrCreateBuilder(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        getOrCreateBuilder(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        getOrCreateBuilder(BUTTONS).add(getModBlocks(b -> b instanceof AbstractButtonBlock));
        getOrCreateBuilder(WOODEN_BUTTONS).add(getModBlocks(b -> b instanceof WoodButtonBlock));
        getOrCreateBuilder(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof AbstractPressurePlateBlock));
        //getOrCreateBuilder(LOGS).add();
        getOrCreateBuilder(DIRT).add(getModBlocks(b -> b instanceof GrassBlock));
        getOrCreateBuilder(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));
        //        getOrCreateBuilder(PLANKS).add();
        //        getOrCreateBuilder(WOODEN_FENCES).add();
        //        getOrCreateBuilder(WOODEN_DOORS).add();
        //        getOrCreateBuilder(WOODEN_STAIRS).add();
        //        getOrCreateBuilder(WOODEN_SLABS).add();
        //        getOrCreateBuilder(WOODEN_TRAPDOORS).add();
        //        getOrCreateBuilder(WOODEN_PRESSURE_PLATES).add();
    }
    
    @Override
    public String getName()
    {
        return "Block Tags";
    }
    
    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate)
    {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        UAMBlocks.REGISTRY.getEntries().stream().filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}