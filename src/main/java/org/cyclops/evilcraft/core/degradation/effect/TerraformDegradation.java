package org.cyclops.evilcraft.core.degradation.effect;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.cyclops.cyclopscore.config.extendedconfig.ExtendedConfig;
import org.cyclops.cyclopscore.helper.LocationHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.evilcraft.Configs;
import org.cyclops.evilcraft.api.degradation.IDegradable;
import org.cyclops.evilcraft.block.NetherfishSpawn;
import org.cyclops.evilcraft.block.NetherfishSpawnConfig;
import org.cyclops.evilcraft.core.config.extendedconfig.DegradationEffectConfig;
import org.cyclops.evilcraft.core.degradation.StochasticDegradationEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Degradation effect that will terraform certain blocks into the area to
 * other blockState.
 * @author rubensworks
 *
 */
public class TerraformDegradation extends StochasticDegradationEffect {
    
private static TerraformDegradation _instance = null;
    
    /**
     * Get the unique instance.
     * @return The instance.
     */
    public static TerraformDegradation getInstance() {
        return _instance;
    }
    
    private static Map<Block, Map<IBlockState, Integer>> TERRAFORMATIONS = Maps.newHashMap();
    private static final double CHANCE = 0.1D;
    
    private static Random random = new Random();
    
    static{
        // Default replacement
        putReplacement(null, Blocks.COBBLESTONE.getDefaultState(), 30);
        
        putReplacement(Blocks.STONE, Blocks.COBBLESTONE.getDefaultState());
        
        putReplacement(Blocks.COBBLESTONE, Blocks.DIRT.getDefaultState(), 10);
        putReplacement(Blocks.COBBLESTONE, Blocks.LAVA.getDefaultState(), 30);
        
        putReplacement(Blocks.COAL_BLOCK, Blocks.DIAMOND_BLOCK.getDefaultState(), 10000);
        
        putReplacement(Blocks.DIRT, Blocks.NETHERRACK.getDefaultState(), 30);
        putReplacement(Blocks.GRASS, Blocks.NETHERRACK.getDefaultState(), 20);
        putReplacement(Blocks.MYCELIUM, Blocks.NETHERRACK.getDefaultState(), 5);
        putReplacement(Blocks.DIRT, Blocks.SAND.getDefaultState());
        putReplacement(Blocks.GRASS, Blocks.SAND.getDefaultState());
        putReplacement(Blocks.MYCELIUM, Blocks.SAND.getDefaultState());
        putReplacement(Blocks.DIRT, Blocks.CLAY.getDefaultState(), 20);
        putReplacement(Blocks.GRASS, Blocks.SAND.getDefaultState(), 20);
        putReplacement(Blocks.MYCELIUM, Blocks.SAND.getDefaultState(), 20);
        
        if(Configs.isEnabled(NetherfishSpawnConfig.class)) {
            putReplacement(Blocks.NETHERRACK,
                    NetherfishSpawn.getInstance().getStateFromMeta(NetherfishSpawn.getInstance().
                            getMetadataFromBlock(Blocks.NETHERRACK)), 50);
        }
        
        putReplacement(Blocks.SAND, null);
        
        putReplacement(Blocks.WATER, null);
    }
    
    private static final void putReplacement(Block key, IBlockState value) {
        putReplacement(key, value, 0);
    }
    
    private static final void putReplacement(Block key, IBlockState value, int chance) {
        Map<IBlockState, Integer> mapValue = TERRAFORMATIONS.get(key);
        if(mapValue == null) {
            mapValue = new HashMap<IBlockState, Integer>();
            TERRAFORMATIONS.put(key, mapValue);
        }
        mapValue.put(value, chance);
    }
    
    public TerraformDegradation(ExtendedConfig<DegradationEffectConfig> eConfig) {
        super(eConfig, CHANCE);
    }

    @Override
    public void runClientSide(IDegradable degradable) {
        
    }
    
    protected IBlockState getReplacement(Block block) {
        Map<IBlockState, Integer> mapValue = TERRAFORMATIONS.get(block);
        if(mapValue == null) { // Fetch the default replacement.
            mapValue = TERRAFORMATIONS.get(null);
        }
        if(mapValue != null) {
            Object[] keys = mapValue.keySet().toArray();
            IBlockState holder = (IBlockState) keys[random.nextInt(keys.length)];
            Integer chance = mapValue.get(holder);
            if(chance == null || chance == 0 || random.nextInt(chance) == 0) {
                return holder;
            }
        }
        return null;
    }

    @Override
    public void runServerSide(IDegradable degradable) {
        World world = degradable.getDegradationWorld();
        
        BlockPos blockPos = LocationHelpers.getRandomPointInSphere(
                degradable.getLocation(), degradable.getRadius());
        
        Block block = world.getBlockState(blockPos).getBlock();
        IBlockState replace = getReplacement(block);
        
        if(replace != null
                && !degradable.getLocation().equals(blockPos)
                && world.getTileEntity(blockPos) == null) {
            if(replace.getBlock() == null) {
                world.setBlockToAir(blockPos);
            } else if(replace.getBlockHardness(world, blockPos) > 0) {
                world.setBlockState(blockPos, replace, MinecraftHelpers.BLOCK_NOTIFY_CLIENT | MinecraftHelpers.BLOCK_NOTIFY);
            }
        }
    }

}
