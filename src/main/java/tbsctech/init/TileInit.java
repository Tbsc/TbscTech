package tbsctech.init;

import cpw.mods.fml.common.registry.GameRegistry;
import tbsctech.machines.heater.TileHeater;

public class TileInit {

    public static void init() {
        GameRegistry.registerTileEntity(TileHeater.class, "tbsctech_heater");
    }

}
