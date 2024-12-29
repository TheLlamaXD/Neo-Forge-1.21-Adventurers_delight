package net.dongurs.delightfull.block.ink_blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class MourningInkBlockClump extends Block {



    public MourningInkBlockClump(Properties properties) {
        super(properties
                .mapColor(MapColor.COLOR_BLACK)
                .sound(SoundType.HONEY_BLOCK)
                .strength(0.5f, 2f)
                .speedFactor(0.8f)
                .jumpFactor(0.8f)
                );
    }



}
