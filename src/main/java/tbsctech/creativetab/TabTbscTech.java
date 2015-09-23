package tbsctech.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tbsctech.init.BlockInit;
import tbsctech.reference.Reference;

public class TabTbscTech {

    public static final CreativeTabs tabTbscTech = new CreativeTabs(Reference.MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockInit.blockHeater);
        }

        @Override
        public String getTranslatedTabLabel() {
            return "TbscTech";
        }
    };

}
