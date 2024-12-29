package net.dongurs.delightfull.entity.client.spirit;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dongurs.delightfull.entity.custom.SamuraiSpiritEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.FlyingMob;


public class SamuraiSpiritModel extends HierarchicalModel<SamuraiSpiritEntity> {

    private final ModelPart wholeEntity;
    private final ModelPart spirit;
    private final ModelPart onlyHead;
    private final ModelPart hornR;
    private final ModelPart hornL;
    private final ModelPart handRight;
    private final ModelPart handLeft;

    public SamuraiSpiritModel(ModelPart root) {
        this.wholeEntity = root.getChild("wholeEntity");
        this.spirit = this.wholeEntity.getChild("spirit");
        this.onlyHead = this.spirit.getChild("onlyHead");
        this.hornR = this.onlyHead.getChild("hornR");
        this.hornL = this.onlyHead.getChild("hornL");
        this.handRight = this.spirit.getChild("handRight");
        this.handLeft = this.spirit.getChild("handLeft");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wholeEntity = partdefinition.addOrReplaceChild("wholeEntity", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 1.36F));

        PartDefinition spirit = wholeEntity.addOrReplaceChild("spirit", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -1.36F));

        PartDefinition onlyHead = spirit.addOrReplaceChild("onlyHead", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -6.0F, -2.36F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(36, 17).addBox(-6.0F, -3.0F, -2.46F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -6.5F, -2.86F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(2.0F, -5.0F, -2.46F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-5.0F, -3.0F, -2.46F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-6.0F, -16.0F, 1.64F, 12.0F, 20.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 32).addBox(-5.0F, -4.0F, 6.64F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(47, 26).addBox(-5.0F, -4.0F, 6.64F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(47, 26).addBox(5.0F, -4.0F, 6.64F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 50).addBox(-5.0F, -7.0F, -3.36F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -1.64F));

        PartDefinition hornR = onlyHead.addOrReplaceChild("hornR", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(4.0F, -4.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, -1.36F));

        PartDefinition hornL = onlyHead.addOrReplaceChild("hornL", CubeListBuilder.create().texOffs(0, 28).addBox(-7.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 6).addBox(-5.0F, -4.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, -1.36F));

        PartDefinition handRight = spirit.addOrReplaceChild("handRight", CubeListBuilder.create().texOffs(27, 7).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(27, 0).addBox(-3.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(51, 7).addBox(-3.0F, 4.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F))
                .texOffs(32, 37).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -1.0F, -0.5F));

        PartDefinition handLeft = spirit.addOrReplaceChild("handLeft", CubeListBuilder.create().texOffs(27, 7).addBox(-3.0F, -2.0F, -1.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(27, 0).mirror().addBox(-3.0F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(51, 7).addBox(-3.0F, 4.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F))
                .texOffs(32, 37).mirror().addBox(-3.0F, -2.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -1.0F, -0.5F));

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
