package tbsctech.machines.heater;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;

public class GuiTileHeater extends GuiContainer {

    public GuiTileHeater(ContainerHeater containerHeater) {
        super(containerHeater);

        this.xSize = 176;
        this.ySize = 256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

}
