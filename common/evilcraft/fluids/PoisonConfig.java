package evilcraft.fluids;

import evilcraft.api.config.FluidConfig;

/**
 * Config for {@link Poison}.
 * @author rubensworks
 *
 */
public class PoisonConfig extends FluidConfig {
    
    /**
     * The unique instance.
     */
    public static PoisonConfig _instance;

    /**
     * Make a new instance.
     */
    public PoisonConfig() {
        super(
            1,
            "Poison",
            "poison",
            null,
            Poison.class
        );
    }
    
}
