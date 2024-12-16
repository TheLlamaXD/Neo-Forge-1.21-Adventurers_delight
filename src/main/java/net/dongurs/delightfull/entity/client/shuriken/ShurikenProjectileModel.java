package net.dongurs.delightfull.entity.client.shuriken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


public class ShurikenProjectileModel<T extends Entity> extends EntityModel<T> {

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
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0, 0, 0)
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
        whole_item.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
    }

    @Override
    public void setupAnim(T entity, float v, float v1, float v2, float v3, float v4) {

    }
}
