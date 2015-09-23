package tbsctech.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileHeater extends TileEntity implements IInventory {

    // Inventory + name fields
    private ItemStack[] inv;
    private String name;

    // Constructor + inventory creation

    public TileHeater() {
        this.inv = new ItemStack[this.getSizeInventory()];
    }

    // Tile entity ticking

    @Override
    public void updateEntity() {

    }

    // NBT

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);

        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.getCustomName());
        }
    }

    // Custom name handling

    public String getCustomName() {
        return this.name;
    }

    public void setCustomName(String name) {
        this.name = name;
    }

    public boolean hasCustomName() {
        return this.name != null && !this.name.equals("");
    }

    // Basic implemented inventory methods

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < 0 || slot >= this.getSizeInventory())
            return null;
        return this.inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.getStackInSlot(slot) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(slot).stackSize <= amount) {
                itemstack = this.getStackInSlot(slot);
                this.setInventorySlotContents(slot, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(slot).splitStack(amount);

                if (this.getStackInSlot(slot).stackSize <= 0) {
                    this.setInventorySlotContents(slot, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(slot, this.getStackInSlot(slot));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        this.setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < 0 || slot >= this.getSizeInventory())
            return;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();

        if (stack != null && stack.stackSize == 0)
            stack = null;

        this.inv[slot] = stack;
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomName() ? this.name : "container.tbsctech_heater";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        /*
        if (slot == 1) return false;
        if (slot == 0 && FurnaceRecipes.smelting().getSmeltingList().containsKey(item.getItem())) return true;
        else return false;
        */
        return true;
    }

    public int getField(int id) {
        return 0;
    }

    public void setField(int id, int value) {

    }

    public int getFieldCount() {
        return 0;
    }

}
