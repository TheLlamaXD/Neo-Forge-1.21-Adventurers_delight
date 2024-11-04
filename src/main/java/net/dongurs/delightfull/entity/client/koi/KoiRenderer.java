package net.dongurs.delightfull.entity.client.koi;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.client.ModModelLayers;
import net.dongurs.delightfull.entity.custom.KoiEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class KoiRenderer extends MobRenderer<KoiEntity,KoiModel>{

    public KoiRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new KoiModel(pContext.bakeLayer(ModModelLayers.KOI)), 0f);

    }



    @Override
    public ResourceLocation getTextureLocation(KoiEntity koiEntity) {
        return ResourceLocation.fromNamespaceAndPath(
                AdventurersDelight.MOD_ID,"textures/entity/koi/koi.png"
        );
    }

    public void setupRotations(KoiEntity koiEntity, PoseStack poseStack,float bob, float yBodyRot, float partialTick,float scale){
        super.setupRotations(koiEntity, poseStack, bob, yBodyRot, partialTick,scale);


        if (!koiEntity.isInWater()) {
            poseStack.translate(0.2F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

    }

}
