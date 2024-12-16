package net.dongurs.delightfull.entity.client.shuriken;

import net.dongurs.delightfull.entity.custom.ShurikenProjectileEntity;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;



import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ShurikenProjectileRenderer extends EntityRenderer<ShurikenProjectileEntity> {
    private static final ResourceLocation texture = ResourceLocation.parse("delightfull:textures/entity/shuriken_model_texture.png");
    private final ShurikenProjectileModel model;

    public ShurikenProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ShurikenProjectileModel(context.bakeLayer(ShurikenProjectileModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShurikenProjectileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())- 10.0F));
        if (!entityIn.isInGround(entityIn)){
            poseStack.mulPose(Axis.XP.rotationDegrees((entityIn.tickCount+partialTicks) * 1));
            poseStack.mulPose(Axis.YP.rotationDegrees((entityIn.tickCount+partialTicks) * 45));
        }
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ShurikenProjectileEntity entity) {
        return texture;
    }
}
