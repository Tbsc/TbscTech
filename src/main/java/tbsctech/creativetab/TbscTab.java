package tbsctech.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tbsctech.init.BlockInit;

public class TbscTab extends CreativeTabs {

    public TbscTab(String label) {
        super(label);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(BlockInit.blockHeater);
    }
}
