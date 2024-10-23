package net.dongurs.delightfull.entity.client.shuriken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dongurs.delightfull.entity.custom.ShurikenProjectileEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


public class ShurikenProjectileModel<T extends Entity> extends HierarchicalModel<ShurikenProjectileEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("delightfull", "model_shuriken_item"), "main");
    public final ModelPart whole_item;

    public ShurikenProjectileModel(ModelPart root) {
        this.whole_item = root.getChild("WholeItem");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition WholeItem = partdefinition.addOrReplaceChild("WholeItem",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-11, 17)
                        .addBox(-6.5F, -0.5F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                // Adjust the offset and rotation
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, -45, -180.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }



    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        whole_item.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    @Override
    public ModelPart root() {
        return whole_item;
    }

    public void setupAnim(ShurikenProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.flyState, ShurikenFlyAnimations.FLY , ageInTicks, 1f);
        //this.animateWalk(ShurikenFlyAnimations.FLY,limbSwing,limbSwingAmount,2f,2.5f);
        if (!entity.inGround()){
            this.animateWalk(ShurikenFlyAnimations.FLY,limbSwing,limbSwingAmount,2f,2.5f);
        }
    }


}
