package io.github.reoseah.faerie;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EnergyNodeBlockEntity extends BlockEntity {
    public static final BlockEntityType<EnergyNodeBlockEntity> TYPE = BlockEntityType.Builder.create(EnergyNodeBlockEntity::new, EnergyNodeBlock.INSTANCE).build(null);

    public int ticks = new Random().nextInt(100);

    public EnergyNodeBlockEntity(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, EnergyNodeBlockEntity blockEntity) {
        blockEntity.ticks++;
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, EnergyNodeBlockEntity blockEntity) {

    }
}
