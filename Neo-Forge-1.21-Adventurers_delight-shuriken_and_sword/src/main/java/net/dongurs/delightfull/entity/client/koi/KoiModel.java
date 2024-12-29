package net.dongurs.delightfull.entity.client.koi;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dongurs.delightfull.entity.client.spirit.SamuraiSpiritAnimations;
import net.dongurs.delightfull.entity.custom.KoiEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class KoiModel extends HierarchicalModel<KoiEntity> {

    private final ModelPart koi_entity;
    private final ModelPart whole_front;
    private final ModelPart whole_back;
    private final ModelPart left_fin;
    private final ModelPart right_fin;
    private final ModelPart tail;

    public KoiModel(ModelPart root) {
        this.koi_entity = root.getChild("koi_entity");
        this.whole_front = this.koi_entity.getChild("whole_front");
        this.left_fin = this.whole_front.getChild("left_fin");
        this.right_fin = this.whole_front.getChild("right_fin");
        this.whole_back = this.koi_entity.getChild("whole_back");
        this.tail = this.whole_back.getChild("tail");
    }



    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition koi_entity = partdefinition.addOrReplaceChild("koi_entity", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition whole_front = koi_entity.addOrReplaceChild("whole_front", CubeListBuilder.create().texOffs(17, 0).addBox(-1.5F, -2.5F, -11.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(-1.5F, -2.5F, -11.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(0.0F, -5.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 1.0F));

        PartDefinition bottom_fin_left_r1 = whole_front.addOrReplaceChild("bottom_fin_left_r1", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, -3.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom_fin_right_r1 = whole_front.addOrReplaceChild("bottom_fin_right_r1", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -3.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition large_left_fin_r1 = whole_front.addOrReplaceChild("large_left_fin_r1", CubeListBuilder.create().texOffs(-6, 15).addBox(-6.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.5F, -10.0F, 0.0F, 0.0F, -0.9599F));

        PartDefinition large_right_fin_r1 = whole_front.addOrReplaceChild("large_right_fin_r1", CubeListBuilder.create().texOffs(-6, 9).addBox(0.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.5F, -10.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition left_fin = whole_front.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, -7.0F));

        PartDefinition left_fin_r1 = left_fin.addOrReplaceChild("left_fin_r1", CubeListBuilder.create().texOffs(0, 41).addBox(-4.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -1.8326F, 0.0F, -0.2618F));

        PartDefinition right_fin = whole_front.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, -7.0F));

        PartDefinition right_fin_r1 = right_fin.addOrReplaceChild("right_fin_r1", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -1.9199F, 0.0F, 0.2618F));

        PartDefinition whole_front2 = koi_entity.addOrReplaceChild("whole_front2", CubeListBuilder.create().texOffs(17, 0).addBox(-1.5F, -2.5F, -11.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(-1.5F, -2.5F, -11.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(0.0F, -5.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 1.0F));

        PartDefinition bottom_fin_left_r2 = whole_front2.addOrReplaceChild("bottom_fin_left_r2", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, -3.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom_fin_right_r2 = whole_front2.addOrReplaceChild("bottom_fin_right_r2", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -3.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition large_left_fin_r2 = whole_front2.addOrReplaceChild("large_left_fin_r2", CubeListBuilder.create().texOffs(-6, 15).addBox(-6.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.5F, -10.0F, 0.0F, 0.0F, -0.9599F));

        PartDefinition large_right_fin_r2 = whole_front2.addOrReplaceChild("large_right_fin_r2", CubeListBuilder.create().texOffs(-6, 9).addBox(0.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.5F, -10.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition left_fin2 = whole_front2.addOrReplaceChild("left_fin2", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, -7.0F));

        PartDefinition left_fin_r2 = left_fin2.addOrReplaceChild("left_fin_r2", CubeListBuilder.create().texOffs(0, 41).addBox(-4.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -1.8326F, 0.0F, -0.2618F));

        PartDefinition right_fin2 = whole_front2.addOrReplaceChild("right_fin2", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, -7.0F));

        PartDefinition right_fin_r2 = right_fin2.addOrReplaceChild("right_fin_r2", CubeListBuilder.create().texOffs(0, 38).addBox(0.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -1.9199F, 0.0F, 0.2618F));

        PartDefinition whole_back = koi_entity.addOrReplaceChild("whole_back", CubeListBuilder.create().texOffs(22, 16).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 30).addBox(0.0F, -5.5F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 27).addBox(0.0F, 2.5F, 1.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -3.0F, 1.0F));

        PartDefinition tail = whole_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(25, 27).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.01F))
                .texOffs(0, -9).addBox(0.0F, -5.0F, 0.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.5F, 6.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(KoiEntity koiEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float f = 1.0F;
        float f1 = 1.0F;
        if (!koiEntity.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        this.whole_back.yRot = -f * 0.25F * Mth.sin(f1 * 0.6F * ageInTicks);

        //this.animate(koiEntity.idleAnimationState, KoiAnimations.IDLE,ageInTicks,1f);

    }



    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int colour) {
        koi_entity.render(poseStack, vertexConsumer, packedLight, packedOverlay, colour);
    }

    @Override
    public ModelPart root() {
        return koi_entity;
    }


}
