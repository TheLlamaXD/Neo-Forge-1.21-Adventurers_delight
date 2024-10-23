package net.dongurs.delightfull.entity.client;

import net.dongurs.delightfull.AdventurersDelight;
import net.dongurs.delightfull.entity.custom.SamuraiSpiritEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.FlyingMob;

public class SamuraiSpiritRenderer extends MobRenderer<SamuraiSpiritEntity, SamuraiSpiritModel<FlyingMob>> {
    // Define how many frames the animation has
    private static final int TOTAL_FRAMES = 7;  // for example, 4 frames
    // Define the duration for each frame in ticks
    private static final int FRAME_DURATION = 2; // 10 ticks per frame (0.5 seconds per frame at 20 ticks per second)
    // Total duration for one full animation cycle
    private static final int ANIMATION_DURATION = TOTAL_FRAMES * FRAME_DURATION;


    public SamuraiSpiritRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SamuraiSpiritModel(pContext.bakeLayer(ModModelLayers.SPIRIT)), 0.5f);
        //this.addLayer(new SpiritPowerLayer(this, pContext.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SamuraiSpiritEntity samuraiSpiritEntity) {
        // Calculate the current frame based on the game time
        int tickCount = samuraiSpiritEntity.tickCount % ANIMATION_DURATION;  // Use modulo to prevent extra ticks on the last frame
        int currentFrame = tickCount / FRAME_DURATION;

        // Return the texture for the current frame
        return ResourceLocation.fromNamespaceAndPath(
                AdventurersDelight.MOD_ID,
                "textures/entity/spirit/samurai_spirit_frame_" + currentFrame + ".png"
        );
    }
}
