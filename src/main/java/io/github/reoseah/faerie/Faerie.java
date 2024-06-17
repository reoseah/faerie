package io.github.reoseah.faerie;

import io.github.reoseah.faerie.client.EnergyNodeBeRenderer;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnvironmentInterface(value = EnvType.CLIENT, itf = ClientModInitializer.class)
public class Faerie implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("faerie");

    public static final Item ENERGY = new Item(new Item.Settings().rarity(Rarity.RARE)) {
        @Override
        public boolean hasGlint(ItemStack stack) {
            return true;
        }
    };

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Faerie.ENERGY))
            .displayName(Text.translatable("itemGroup.faerie"))
            .entries((context, entries) -> {
                entries.add(new ItemStack(Faerie.ENERGY));
                entries.add(new ItemStack(EnergyNodeBlock.ITEM));
            })
            .build();

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, "faerie:energy", EnergyNodeBlock.INSTANCE);

        Registry.register(Registries.BLOCK_ENTITY_TYPE, "faerie:energy", EnergyNodeBlockEntity.TYPE);

        Registry.register(Registries.ITEM, "faerie:energy", ENERGY);
        Registry.register(Registries.ITEM, "faerie:energy_placer", EnergyNodeBlock.ITEM);

        Registry.register(Registries.ITEM_GROUP, "faerie:main", ITEM_GROUP);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(EnergyNodeBlockEntity.TYPE, EnergyNodeBeRenderer::new);
    }
}