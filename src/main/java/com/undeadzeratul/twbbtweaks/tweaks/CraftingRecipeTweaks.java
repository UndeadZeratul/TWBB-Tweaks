package com.undeadzeratul.twbbtweaks.tweaks;

import com.nincraft.nincraftlib.utility.LogHelper;
import com.undeadzeratul.twbbtweaks.handler.CraftingRecipeHandler;
import com.undeadzeratul.twbbtweaks.reference.Settings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CraftingRecipeTweaks
{
    private static final String DELIMITER       = "|";
    private static final String DELIMITER_REGEX = "\\|";

    public static void init ()
    {
        for (final String disabledItem : Settings.Tweaks.disabledItems)
        {
            String[] disabledItemData = disabledItem.split(DELIMITER_REGEX);

            switch (disabledItemData.length)
            {
                // modId|itemName|metadata
                case 3:
                    CraftingRecipeHandler.removeCraftingRecipes(buildItemStack(disabledItemData[0], disabledItemData[1],
                                                                               Integer.valueOf(disabledItemData[2])));
                    break;
                // modId|itemName
                case 2:
                    CraftingRecipeHandler
                            .removeCraftingRecipes(buildItemStack(disabledItemData[0], disabledItemData[1]));
                    break;
                // oreDictionaryName
                case 1:
                    if (OreDictionary.doesOreNameExist(disabledItemData[0]))
                    {
                        for (ItemStack oreStack : OreDictionary.getOres(disabledItemData[0]))
                        {
                            CraftingRecipeHandler.removeCraftingRecipes(oreStack);
                        }
                    }
                    break;
                // Wat am I even reading?
                default:
                    LogHelper.warn(String.format("Unknown config option: %s", String.join(DELIMITER, disabledItemData)));
                    break;
            }
        }
    }

    private static ItemStack buildItemStack (final String modId, final String itemName)
    {
        return buildItemStack(modId, itemName, OreDictionary.WILDCARD_VALUE);
    }

    private static ItemStack buildItemStack (final String modId, final String itemName, final int metadata)
    {
        return new ItemStack(GameRegistry.findItem(modId, itemName), 1, metadata);
    }
}
