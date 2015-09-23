package tbsctech.machines.heater;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHeater extends Container {

    private TileHeater tileHeater;
    // private EntityPlayer player;

    public ContainerHeater(IInventory playerInv, TileHeater heater) {
        this.tileHeater = heater;
        // this.player = player;

        // Tile entity slots, 0-1
        addSlotToContainer(new Slot(tileHeater, 0, 56, 74));
        addSlotToContainer(new Slot(tileHeater, 1, 104, 74));

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 107 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 167));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileHeater.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            // [...] Custom behaviour
            if (fromSlot < 2) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 2, 36, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, 1, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }

}
