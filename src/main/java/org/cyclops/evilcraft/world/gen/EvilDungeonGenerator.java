package org.cyclops.evilcraft.world.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import org.cyclops.evilcraft.world.gen.structure.EvilDungeonStructure;

import java.util.Random;

/**
 * WorldGenerator for Evil Dungeons.
 * @author rubensworks
 *
 */
public class EvilDungeonGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.getDimension() == 0) generateSurface(world, random, chunkX * 16, chunkZ * 16);
    }
    
    private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
        // Offset to chunk center to avoid cascaded chunk loading: #539
        (new EvilDungeonStructure()).generate(world, random,
                new BlockPos(chunkX + random.nextInt(16) + 8, random.nextInt(60), chunkZ + random.nextInt(16) + 8));
    }
}
