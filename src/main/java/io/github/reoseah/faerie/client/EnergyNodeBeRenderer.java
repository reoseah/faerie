package io.github.reoseah.faerie.client;

import io.github.reoseah.faerie.EnergyNodeBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class EnergyNodeBeRenderer implements BlockEntityRenderer<EnergyNodeBlockEntity> {
    public static final SpriteIdentifier WIND_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("entity/conduit/wind"));
    public static final SpriteIdentifier WIND_VERTICAL_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("entity/conduit/wind_vertical"));

    private final BlockEntityRenderDispatcher dispatcher;
    private final ModelPart conduitWind;

    public EnergyNodeBeRenderer(BlockEntityRendererFactory.Context ctx) {
        this.dispatcher = ctx.getRenderDispatcher();
        this.conduitWind = ctx.getLayerModelPart(EntityModelLayers.CONDUIT_WIND);
    }

    public static TexturedModelData getWindTexturedModelData() {
        ModelData model = new ModelData();
        ModelPartData root = model.getRoot();
        root.addChild("wind", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), ModelTransform.NONE);
        return TexturedModelData.of(model, 64, 32);
    }

    @Override
    public void render(EnergyNodeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        float time = entity.ticks + tickDelta;
        float time = MinecraftClient.getInstance().world.getTime() + tickDelta;


//        int l = entity.ticks / 66 % 3;
        int l = (int) (MinecraftClient.getInstance().world.getTime() / 66 % 3);
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        if (l == 1) {
            matrices.multiply((new Quaternionf()).rotationX(1.5707964F));
        } else if (l == 2) {
            matrices.multiply((new Quaternionf()).rotationZ(1.5707964F));
        }
        VertexConsumer vertexConsumer2 = (overlay == 1 ? WIND_VERTICAL_TEXTURE : WIND_TEXTURE).getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutoutNoCull);

        this.conduitWind.render(matrices, vertexConsumer2, light, overlay);
        matrices.pop();
        matrices.push();
        matrices.translate(0.5F, 0.5F, 0.5F);
        matrices.scale(0.875F, 0.875F, 0.875F);
        matrices.multiply((new Quaternionf()).rotationXYZ(3.1415927F, 0.0F, 3.1415927F));
        this.conduitWind.render(matrices, vertexConsumer2, light, overlay);
        matrices.pop();
    }
}
