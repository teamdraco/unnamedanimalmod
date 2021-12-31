package teamdraco.unnamedanimalmod.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teamdraco.unnamedanimalmod.common.entity.BananaSlugEntity;
import teamdraco.unnamedanimalmod.init.UAMBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.IWorld;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import java.util.Map;

public class SaltPowderBlock extends Block {
    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
    public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
    private static final VoxelShape BARRIER_SHAPE = VoxelShapes.box(0, -4, 0, 1, 5, 1);
    private static final VoxelShape BASE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> SIDE_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
    private static final Map<Direction, VoxelShape> SIDE_TO_ASCENDING_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
    private final Map<BlockState, VoxelShape> stateToShapeMap = Maps.newHashMap();
    private final BlockState sideBaseState;

    public SaltPowderBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE));
        this.sideBaseState = this.defaultBlockState().setValue(NORTH, RedstoneSide.SIDE).setValue(EAST, RedstoneSide.SIDE).setValue(SOUTH, RedstoneSide.SIDE).setValue(WEST, RedstoneSide.SIDE);

        for (BlockState blockstate : this.getStateDefinition().getPossibleStates()) {
            this.stateToShapeMap.put(blockstate, this.getShapeForState(blockstate));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static int getColor() {
        return Mth.color(224, 190, 183);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) context.getEntity();
            if (entity.isInvertedHealAndHarm()) {
                return BARRIER_SHAPE;
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entityIn;
            if (entityIn instanceof BananaSlugEntity || livingEntity.getMobType() == MobType.UNDEAD) {
                if (entityIn.level.getGameTime() % 20L == 0) {
                    entityIn.hurt(DamageSource.DRY_OUT, 1);
                }
            }
        }

        super.entityInside(state, worldIn, pos, entityIn);
    }

    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = BASE_SHAPE;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = state.getValue(FACING_PROPERTY_MAP.get(direction));
            if (redstoneside == RedstoneSide.SIDE) {
                voxelshape = VoxelShapes.or(voxelshape, SIDE_TO_SHAPE.get(direction));
            }
            else if (redstoneside == RedstoneSide.UP) {
                voxelshape = VoxelShapes.or(voxelshape, SIDE_TO_ASCENDING_SHAPE.get(direction));
            }
        }

        return voxelshape;
    }

    private static boolean areAllSidesInvalid(BlockState state) {
        return !state.getValue(NORTH).isConnected() && !state.getValue(SOUTH).isConnected() && !state.getValue(EAST).isConnected() && !state.getValue(WEST).isConnected();
    }

    private static boolean areAllSidesValid(BlockState state) {
        return state.getValue(NORTH).isConnected() && state.getValue(SOUTH).isConnected() && state.getValue(EAST).isConnected() && state.getValue(WEST).isConnected();
    }

    private BlockState recalculateFacingState(BlockGetter reader, BlockState state, BlockPos pos) {
        boolean flag = !reader.getBlockState(pos.above()).isRedstoneConductor(reader, pos);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!state.getValue(FACING_PROPERTY_MAP.get(direction)).isConnected()) {
                RedstoneSide redstoneside = this.recalculateSide(reader, pos, direction, flag);
                state = state.setValue(FACING_PROPERTY_MAP.get(direction), redstoneside);
            }
        }

        return state;
    }

    private RedstoneSide getSide(BlockGetter worldIn, BlockPos pos, Direction face) {
        return this.recalculateSide(worldIn, pos, face, !worldIn.getBlockState(pos.above()).isRedstoneConductor(worldIn, pos));
    }

    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor worldIn, BlockPos pos, int flags, int recursionLeft) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            RedstoneSide redstoneside = state.getValue(FACING_PROPERTY_MAP.get(direction));
            if (redstoneside != RedstoneSide.NONE && !worldIn.getBlockState(blockpos$mutable.setWithOffset(pos, direction)).is(this)) {
                blockpos$mutable.move(Direction.DOWN);
                BlockState blockstate = worldIn.getBlockState(blockpos$mutable);
                if (!blockstate.is(Blocks.OBSERVER)) {
                    BlockPos blockpos = blockpos$mutable.relative(direction.getOpposite());
                    BlockState blockstate1 = blockstate.updateShape(direction.getOpposite(), worldIn.getBlockState(blockpos), worldIn, blockpos$mutable, blockpos);
                    updateOrDestroy(blockstate, blockstate1, worldIn, blockpos$mutable, flags, recursionLeft);
                }

                blockpos$mutable.setWithOffset(pos, direction).move(Direction.UP);
                BlockState blockstate3 = worldIn.getBlockState(blockpos$mutable);
                if (!blockstate3.is(Blocks.OBSERVER)) {
                    BlockPos blockpos1 = blockpos$mutable.relative(direction.getOpposite());
                    BlockState blockstate2 = blockstate3.updateShape(direction.getOpposite(), worldIn.getBlockState(blockpos1), worldIn, blockpos$mutable, blockpos1);
                    updateOrDestroy(blockstate3, blockstate2, worldIn, blockpos$mutable, flags, recursionLeft);
                }
            }
        }

    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            return stateIn;
        }
        else if (facing == Direction.UP) {
            return this.getUpdatedState(worldIn, stateIn, currentPos);
        }
        else {
            RedstoneSide redstoneside = this.getSide(worldIn, currentPos, facing);
            return redstoneside.isConnected() == stateIn.getValue(FACING_PROPERTY_MAP.get(facing)).isConnected() && !areAllSidesValid(stateIn) ? stateIn.setValue(FACING_PROPERTY_MAP.get(facing), redstoneside) : this.getUpdatedState(worldIn, this.sideBaseState.setValue(FACING_PROPERTY_MAP.get(facing), redstoneside), currentPos);
        }
    }

    private RedstoneSide recalculateSide(BlockGetter reader, BlockPos pos, Direction direction, boolean nonNormalCubeAbove) {
        BlockPos blockpos = pos.relative(direction);
        BlockState blockstate = reader.getBlockState(blockpos);
        if (nonNormalCubeAbove) {
            boolean flag = this.canPlaceOnTopOf(reader, blockpos, blockstate);
            if (flag && canConnectTo(reader.getBlockState(blockpos.above()), reader, blockpos.above(), null)) {
                if (blockstate.isFaceSturdy(reader, blockpos, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        return !canConnectTo(blockstate, reader, blockpos, direction) && (blockstate.isRedstoneConductor(reader, blockpos) || !canConnectTo(reader.getBlockState(blockpos.below()), reader, blockpos.below(), null)) ? RedstoneSide.NONE : RedstoneSide.SIDE;
    }

    private boolean canPlaceOnTopOf(BlockGetter reader, BlockPos pos, BlockState state) {
        return state.isFaceSturdy(reader, pos, Direction.UP);
    }

    private BlockState getUpdatedState(BlockGetter reader, BlockState state, BlockPos pos) {
        boolean flag = areAllSidesInvalid(state);
        state = this.recalculateFacingState(reader, this.defaultBlockState(), pos);
        if (flag && areAllSidesInvalid(state)) {
            return state;
        }
        else {
            boolean flag1 = state.getValue(NORTH).isConnected();
            boolean flag2 = state.getValue(SOUTH).isConnected();
            boolean flag3 = state.getValue(EAST).isConnected();
            boolean flag4 = state.getValue(WEST).isConnected();
            boolean flag5 = !flag1 && !flag2;
            boolean flag6 = !flag3 && !flag4;
            if (!flag4 && flag5) {
                state = state.setValue(WEST, RedstoneSide.SIDE);
            }

            if (!flag3 && flag5) {
                state = state.setValue(EAST, RedstoneSide.SIDE);
            }

            if (!flag1 && flag6) {
                state = state.setValue(NORTH, RedstoneSide.SIDE);
            }

            if (!flag2 && flag6) {
                state = state.setValue(SOUTH, RedstoneSide.SIDE);
            }

            return state;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.stateToShapeMap.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getUpdatedState(context.getLevel(), this.sideBaseState, context.getClickedPos());
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !worldIn.isClientSide) {
            for (Direction direction : Direction.Plane.VERTICAL) {
                worldIn.updateNeighborsAt(pos.relative(direction), this);
            }

            this.updateNeighboursStateChange(worldIn, pos);
        }
    }

    @Override
    public InteractionResult use(BlockState p_225533_1_, Level p_225533_2_, BlockPos p_225533_3_, Player p_225533_4_, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
        if (!p_225533_4_.abilities.mayBuild) {
            return InteractionResult.PASS;
        } else {
            if (isCross(p_225533_1_) || isDot(p_225533_1_)) {
                BlockState blockstate = isCross(p_225533_1_) ? this.defaultBlockState() : this.sideBaseState;
                blockstate = this.getConnectionState(p_225533_2_, blockstate, p_225533_3_);
                if (blockstate != p_225533_1_) {
                    p_225533_2_.setBlock(p_225533_3_, blockstate, 3);
                    this.updatesOnShapeChange(p_225533_2_, p_225533_3_, p_225533_1_, blockstate);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }
    }

    private void updatesOnShapeChange(Level p_235548_1_, BlockPos p_235548_2_, BlockState p_235548_3_, BlockState p_235548_4_) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = p_235548_2_.relative(direction);
            if (p_235548_3_.getValue(FACING_PROPERTY_MAP.get(direction)).isConnected() != p_235548_4_.getValue(FACING_PROPERTY_MAP.get(direction)).isConnected() && p_235548_1_.getBlockState(blockpos).isRedstoneConductor(p_235548_1_, blockpos)) {
                p_235548_1_.updateNeighborsAtExceptFromFacing(blockpos, p_235548_4_.getBlock(), direction.getOpposite());
            }
        }
    }

    private BlockState getMissingConnections(BlockGetter p_235551_1_, BlockState p_235551_2_, BlockPos p_235551_3_) {
        boolean flag = !p_235551_1_.getBlockState(p_235551_3_.above()).isRedstoneConductor(p_235551_1_, p_235551_3_);

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!p_235551_2_.getValue(FACING_PROPERTY_MAP.get(direction)).isConnected()) {
                RedstoneSide redstoneside = this.recalculateSide(p_235551_1_, p_235551_3_, direction, flag);
                p_235551_2_ = p_235551_2_.setValue(FACING_PROPERTY_MAP.get(direction), redstoneside);
            }
        }

        return p_235551_2_;
    }

    private BlockState getConnectionState(BlockGetter p_235544_1_, BlockState p_235544_2_, BlockPos p_235544_3_) {
        boolean flag = isDot(p_235544_2_);
        p_235544_2_ = this.getMissingConnections(p_235544_1_, this.defaultBlockState(), p_235544_3_);
        if (flag && isDot(p_235544_2_)) {
            return p_235544_2_;
        } else {
            boolean flag1 = p_235544_2_.getValue(NORTH).isConnected();
            boolean flag2 = p_235544_2_.getValue(SOUTH).isConnected();
            boolean flag3 = p_235544_2_.getValue(EAST).isConnected();
            boolean flag4 = p_235544_2_.getValue(WEST).isConnected();
            boolean flag5 = !flag1 && !flag2;
            boolean flag6 = !flag3 && !flag4;
            if (!flag4 && flag5) {
                p_235544_2_ = p_235544_2_.setValue(WEST, RedstoneSide.SIDE);
            }

            if (!flag3 && flag5) {
                p_235544_2_ = p_235544_2_.setValue(EAST, RedstoneSide.SIDE);
            }

            if (!flag1 && flag6) {
                p_235544_2_ = p_235544_2_.setValue(NORTH, RedstoneSide.SIDE);
            }

            if (!flag2 && flag6) {
                p_235544_2_ = p_235544_2_.setValue(SOUTH, RedstoneSide.SIDE);
            }

            return p_235544_2_;
        }
    }

    private static boolean isCross(BlockState p_235555_0_) {
        return p_235555_0_.getValue(NORTH).isConnected() && p_235555_0_.getValue(SOUTH).isConnected() && p_235555_0_.getValue(EAST).isConnected() && p_235555_0_.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState p_235556_0_) {
        return !p_235556_0_.getValue(NORTH).isConnected() && !p_235556_0_.getValue(SOUTH).isConnected() && !p_235556_0_.getValue(EAST).isConnected() && !p_235556_0_.getValue(WEST).isConnected();
    }

    @Override
    public boolean collisionExtendsVertically(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return entity instanceof LivingEntity && ((LivingEntity) entity).isInvertedHealAndHarm();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return this.canPlaceOnTopOf(worldIn, blockpos, blockstate);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            super.onRemove(state, worldIn, pos, newState, isMoving);
            if (!worldIn.isClientSide) {
                for (Direction direction : Direction.values()) {
                    worldIn.updateNeighborsAt(pos.relative(direction), this);
                }

                this.updateNeighboursStateChange(worldIn, pos);
            }
        }
    }

    private void notifyWireNeighborsOfStateChange(Level worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).is(this)) {
            worldIn.updateNeighborsAt(pos, this);

            for (Direction direction : Direction.values()) {
                worldIn.updateNeighborsAt(pos.relative(direction), this);
            }
        }
    }

    private void updateNeighboursStateChange(Level world, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL){
            this.notifyWireNeighborsOfStateChange(world, pos.relative(direction));
        }

        for (Direction direction1 : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.relative(direction1);
            if (world.getBlockState(blockpos).isRedstoneConductor(world, blockpos)) {
                this.notifyWireNeighborsOfStateChange(world, blockpos.above());
            }
            else {
                this.notifyWireNeighborsOfStateChange(world, blockpos.below());
            }
        }

    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            if (!state.canSurvive(worldIn, pos)) {
                dropResources(state, worldIn, pos);
                worldIn.removeBlock(pos, false);
            }
        }
    }

    protected static boolean canConnectTo(BlockState blockState, BlockGetter world, BlockPos pos, Direction side) {
        return blockState.is(UAMBlocks.SALT.get());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }
}
