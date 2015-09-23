package tbsctech.util;

import tbsctech.reference.Reference;

public class NameUtil {

    public static String generateUnlocaizedName(String name) {
        return Reference.MODID + "_" + name;
    }

}
