package net.dongurs.delightfull.block;


import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;



public class JapaneseLanternBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty HANGING;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape AABB;
    protected static final VoxelShape HANGING_AABB;
    public static final VoxelShape SHAPE_COLLISION;

    public JapaneseLanternBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HANGING, false)
                .setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Direction[] directions = context.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis() == Axis.Y) {
                boolean hanging = direction == Direction.UP;
                BlockState blockState = this.defaultBlockState().setValue(HANGING, hanging);

                if (blockState.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getConnectedDirection(state).getOpposite();
        BlockPos supportPos = pos.relative(direction);

        // Check if the lantern can hang from either a solid block or another hanging lantern
        BlockState supportState = level.getBlockState(supportPos);
        return Block.canSupportCenter(level, supportPos, direction.getOpposite()) ||
                (supportState.getBlock() instanceof JapaneseLanternBlock && supportState.getValue(HANGING));
    }

    protected static Direction getConnectedDirection(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        Direction oppositeDirection = getConnectedDirection(state).getOpposite();
        return oppositeDirection == direction && !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_COLLISION;
    }

    static {
        HANGING = BlockStateProperties.HANGING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE_COLLISION = Shapes.or(Block.box(3.0, 1.0, 3.0, 13.0, 12.0, 13.0), Block.box(5.0, 1.0, 5.0, 11.0, 14.0, 11.0));
        AABB = Shapes.or(Block.box(3.0, 1.0, 3.0, 13.0, 12.0, 13.0), Block.box(5.0, 1.0, 5.0, 11.0, 14.0, 11.0));
        HANGING_AABB = Shapes.or(Block.box(3.0, 1.0, 3.0, 13.0, 12.0, 13.0), Block.box(5.0, 1.0, 5.0, 11.0, 16.0, 11.0));
    }
}
