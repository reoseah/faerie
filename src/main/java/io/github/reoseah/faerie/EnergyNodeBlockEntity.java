package io.github.reoseah.faerie;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class EnergyNodeBlockEntity extends BlockEntity {
    public static final BlockEntityType<EnergyNodeBlockEntity> TYPE = BlockEntityType.Builder.create(EnergyNodeBlockEntity::new, EnergyNodeBlock.INSTANCE).build(null);

    public EnergyNodeBlockEntity(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }
}
