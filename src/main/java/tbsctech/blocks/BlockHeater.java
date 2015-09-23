package tbsctech.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tbsctech.init.TabInit;
import tbsctech.main.TbscTech;
import tbsctech.reference.Reference;
import tbsctech.tile.TileHeater;

public class BlockHeater extends BlockBase implements ITileEntityProvider {

    public BlockHeater(String unlocalizedName) {
        super(Material.iron);
        this.setBlockName(unlocalizedName);
        this.setHardness(2.5f);
        this.setResistance(12.5f);
        this.setCreativeTab(TabInit.tbscTechTab);
        this.setHarvestLevel("pickaxe", 2);
        this.isBlockContainer = true;
    }

    // TileEntity shenanigans

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileHeater();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
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
