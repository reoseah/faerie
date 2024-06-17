package io.github.reoseah.faerie.client;

import io.github.reoseah.faerie.EnergyNodeBlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Quaternionf;

public class EnergyNodeBeRenderer implements BlockEntityRenderer<EnergyNodeBlockEntity> {
    public static final SpriteIdentifier BASE_TEXTURE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("faerie", "block/base"));
    public static final SpriteIdentifier WIND_TEXTURE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("faerie", "block/wind"));
    public static final SpriteIdentifier WIND_VERTICAL_TEXTURE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("faerie", "block/wind_vertical"));

    //    private final BlockEntityRenderDispatcher dispatcher;
    private final ModelPart conduit;
    private final ModelPart conduitWind;

    public EnergyNodeBeRenderer(BlockEntityRendererFactory.Context ctx) {
//        this.dispatcher = ctx.getRenderDispatcher();
        this.conduit = ctx.getLayerModelPart(EntityModelLayers.CONDUIT_SHELL);
        this.conduitWind = ctx.getLayerModelPart(EntityModelLayers.CONDUIT_WIND);
    }

    @Override
    public void render(EnergyNodeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        light = 0xFF;

        float time = entity.getWorld().getTime() + tickDelta;

        matrices.push();
        matrices.translate(0.0F, .25F + MathHelper.sin(time / 20) / 4, 0.0F);
        {
            VertexConsumer vertexConsumer = BASE_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityTranslucentCull);
            matrices.push();
            matrices.translate(0.5F, 0.5F, 0.5F);
            matrices.multiply(new Quaternionf().rotationY(time * 0.017453292F));
            this.conduit.render(matrices, vertexConsumer, light, overlay);
            matrices.pop();
        }
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        int l = entity.ticks / 66 % 3;
        if (l == 1) {
            matrices.multiply(new Quaternionf().rotationX(1.5707964F));
        } else if (l == 2) {
            matrices.multiply(new Quaternionf().rotationZ(1.5707964F));
        }
        SpriteIdentifier sprite = overlay == 1 ? WIND_VERTICAL_TEXTURE : WIND_TEXTURE;
        VertexConsumer vertexConsumer = sprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutoutNoCull);

        this.conduitWind.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.scale(0.875F, 0.875F, 0.875F);
        matrices.multiply(new Quaternionf().rotationXYZ(3.1415927F, 0.0F, 3.1415927F));
        this.conduitWind.render(matrices, vertexConsumer, 0xFF, overlay);
        matrices.pop();
        matrices.pop();
    }
}
