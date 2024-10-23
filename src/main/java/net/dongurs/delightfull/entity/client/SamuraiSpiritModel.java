package net.dongurs.delightfull.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dongurs.delightfull.entity.custom.SamuraiSpiritEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.FlyingMob;


public class SamuraiSpiritModel<S extends FlyingMob> extends HierarchicalModel<SamuraiSpiritEntity> {


    private final ModelPart wholeEntity;
    private final ModelPart spirit;
    private final ModelPart onlyHead;

     final ModelPart handRight;
     final ModelPart handLeft;

    public SamuraiSpiritModel(ModelPart root) {
        this.wholeEntity = root.getChild("wholeEntity");
        this.spirit = wholeEntity.getChild("spirit");
        this.onlyHead = wholeEntity.getChild("spirit").getChild("onlyHead");


        this.handRight = wholeEntity.getChild("spirit").getChild("handRight");
        this.handLeft = wholeEntity.getChild("spirit").getChild("handLeft");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wholeEntity = partdefinition.addOrReplaceChild("wholeEntity", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 1.36F));

        PartDefinition spirit = wholeEntity.addOrReplaceChild("spirit", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -1.36F));

        PartDefinition onlyHead = spirit.addOrReplaceChild("onlyHead", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -6.0F, -2.36F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -6.5F, -2.86F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(2.0F, -5.0F, -2.46F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-5.0F, -3.0F, -2.46F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-6.0F, -16.0F, 1.64F, 12.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.64F));

        PartDefinition hornR = onlyHead.addOrReplaceChild("hornR", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(4.0F, -4.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, -1.36F));

        PartDefinition hornL = onlyHead.addOrReplaceChild("hornL", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 6).addBox(-5.0F, -4.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, -1.36F));

        PartDefinition handRight = spirit.addOrReplaceChild("handRight", CubeListBuilder.create().texOffs(33, 7).addBox(0.0F, -2.0F, -1.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 28).addBox(0.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -1.0F, -2.5F));

        PartDefinition handLeft = spirit.addOrReplaceChild("handLeft", CubeListBuilder.create().texOffs(27, 7).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(27, 0).addBox(-3.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -1.0F, -2.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }



    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int colour) {
        wholeEntity.render(poseStack, vertexConsumer, packedLight, packedOverlay, colour);
    }


    private void applyHeadRotation(float headYaw, float headPitch){
        headYaw = Mth.clamp(headYaw, -30f,30f);
        headPitch = Mth.clamp(headPitch, -25f,25f);

        this.onlyHead.yRot = headYaw * ((float)Math.PI / 180f);
        this.onlyHead.xRot = headPitch * ((float)Math.PI / 180f);


    }




    @Override
    public ModelPart root() {
        return spirit;
    }

    @Override
    public void setupAnim(SamuraiSpiritEntity spirit, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animate(spirit.idleAnimationState, SamuraiSpiritAnimations.IDLE,ageInTicks,1f);
        if (spirit.isCharging()){
            this.animateWalk(SamuraiSpiritAnimations.FLYING,limbSwing,limbSwingAmount,2f,2.5f);

        }


    }
}
