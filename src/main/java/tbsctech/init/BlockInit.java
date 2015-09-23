package tbsctech.init;

import cpw.mods.fml.common.registry.GameRegistry;
import tbsctech.blocks.BlockBase;
import tbsctech.machines.heater.BlockHeater;
import tbsctech.util.NameUtil;

public class BlockInit {

    public static final BlockBase blockHeater = new BlockHeater(NameUtil.generateUnlocaizedName("blockHeater"));

    public static void init() {
        GameRegistry.registerBlock(blockHeater, "blockHeater");
    }

}
