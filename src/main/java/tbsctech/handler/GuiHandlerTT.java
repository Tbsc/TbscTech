package tbsctech.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tbsctech.machines.heater.ContainerHeater;
import tbsctech.machines.heater.GuiTileHeater;
import tbsctech.machines.heater.TileHeater;

public class GuiHandlerTT implements IGuiHandler {

    public static final int TBSCTECH_HEATER_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == TBSCTECH_HEATER_GUI)
            return new ContainerHeater(player.inventory, (TileHeater) world.getTileEntity(x, y, z));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == TBSCTECH_HEATER_GUI)
            return new GuiTileHeater(new ContainerHeater(player.inventory, (TileHeater) world.getTileEntity(x, y, z)));
        return null;
    }

}
