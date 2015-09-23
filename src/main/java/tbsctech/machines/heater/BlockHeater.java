package tbsctech.machines.heater;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tbsctech.TbscTech;
import tbsctech.blocks.BlockBase;
import tbsctech.creativetab.TabTbscTech;
import tbsctech.handler.GuiHandlerTT;
import tbsctech.reference.Reference;
import tbsctech.machines.heater.TileHeater;

import java.util.Random;

public class BlockHeater extends BlockBase implements ITileEntityProvider {

    public BlockHeater(String unlocalizedName) {
        super(Material.iron);
        this.setBlockName(unlocalizedName);
        this.setHardness(2.5f);
        this.setResistance(12.5f);
        this.setCreativeTab(TabTbscTech.tabTbscTech);
        this.setHarvestLevel("pickaxe", 2);
        this.isBlockContainer = true;
    }

    // TileEntity shenanigans

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileHeater();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(TbscTech.tbscTech, GuiHandlerTT.TBSCTECH_HEATER_GUI, world, x, y, z);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
        TileHeater te = (TileHeater) world.getTileEntity(x, y, z);
        if (te != null) {
            for (int i1 = 0; i1 < te.getSizeInventory(); ++i1) {
                ItemStack itemstack = te.getStackInSlot(i1);

                if (itemstack != null) {
                    Random random = new Random();
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    float f2 = random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float) random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float) random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float) random.nextGaussian() * f3);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }

            world.func_147453_f(x, y, z, p_149749_5_);
        }
        super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World worldIn, int x, int y, int z, int eventID, int eventParam) {
        super.onBlockEventReceived(worldIn, x, y, z, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(x, y, z);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

    // Rendering and textures and stuff like that

    @SideOnly(Side.CLIENT)
    protected IIcon blockIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconFront;
    @SideOnly(Side.CLIENT)
    protected IIcon blockIconFrontLit;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iIconRegister) {
        blockIcon = iIconRegister.registerIcon(Reference.MODID + ":" + this.getUnlocalizedName().substring(14));
        blockIconFront = iIconRegister.registerIcon(Reference.MODID + ":" + this.getUnlocalizedName().substring(14) + "Front");
        blockIconFrontLit = iIconRegister.registerIcon(Reference.MODID + ":" + this.getUnlocalizedName().substring(14) + "FrontLit");
    }

    //Used for rendering

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase elb, ItemStack itemStack) {
        if (itemStack.hasDisplayName())
            ((TileHeater) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
        int whichDirectionFacing = MathHelper.floor_double((double)(elb.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, whichDirectionFacing, 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {
        if (side == 1) return this.blockIcon;
        else if (side == 0) return this.blockIcon;
        else if (metadata == 2 && side == 2) return this.blockIconFront;
        else if (metadata == 3 && side == 5) return this.blockIconFront;
        else if (metadata == 0 && side == 3) return this.blockIconFront;
        else if (metadata == 1 && side == 4) return this.blockIconFront;
        else return this.blockIcon;
    }

}
