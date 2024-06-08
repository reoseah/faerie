package io.github.reoseah.faerie;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnergyNodeBlock extends BlockWithEntity {
    public static final Block INSTANCE = new EnergyNodeBlock(AbstractBlock.Settings.create().strength(-1, 3600000F).dropsNothing().nonOpaque().noCollision());
    public static final Item ITEM = new BlockItem(INSTANCE, new Item.Settings().rarity(Rarity.EPIC).maxCount(1));

    public static final MapCodec<EnergyNodeBlock> CODEC = createCodec(EnergyNodeBlock::new);

    public EnergyNodeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyNodeBlockEntity(pos, state);
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.isCreative()) {
            return VoxelShapes.fullCube();
        }
        return VoxelShapes.empty();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

    }
}
