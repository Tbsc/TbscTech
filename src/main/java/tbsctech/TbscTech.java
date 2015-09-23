package tbsctech;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import tbsctech.handler.GuiHandlerTT;
import tbsctech.init.BlockInit;
import tbsctech.init.ItemInit;
import tbsctech.init.RecipeInit;
import tbsctech.init.TileInit;
import tbsctech.proxy.IProxy;
import tbsctech.reference.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class TbscTech {

    @Mod.Instance(Reference.MODID)
    public static TbscTech tbscTech;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    public static Minecraft mc;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        TileInit.init();
        mc = Minecraft.getMinecraft();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RecipeInit.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this.tbscTech, new GuiHandlerTT());
    }

}
