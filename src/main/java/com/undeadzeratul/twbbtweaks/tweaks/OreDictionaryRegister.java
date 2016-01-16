package com.undeadzeratul.twbbtweaks.tweaks;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;
import static net.minecraftforge.oredict.OreDictionary.getOres;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.utility.LogHelper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class OreDictionaryRegister
{
    private static final String COLON           = ":";
    private static final String DELIMITER       = "|";
    private static final String DELIMITER_REGEX = "\\|";

    public static void init ()
    {
        parseOreDictEntries();
    }

    private static void parseOreDictEntries ()
    {
        for (String entry : Lists.newArrayList(Settings.Tweaks.oreDictionaryAdditions))
        {
            parseOreDictEntry(entry);
        }
    }

    private static void parseOreDictEntry (final String entry)
    {
        String[] entryData = entry.split(DELIMITER_REGEX);

        switch (entryData.length)
        {
            case 2:
                // OreDictFrom|oreDictTo
                LogHelper.info(String.format("Registering all items under {} as {}", entryData[0], entryData[1]));

                for (final ItemStack ore : getOres(entryData[0]))
                {
                    registerOre(entryData[1], ore);
                }
                break;
            case 3:
                // ModId|ItemName|OreDict
                LogHelper.info(String.format("Registering item {}:{} as {}", entryData[0], entryData[1], entryData[2]));

                registerEntry(buildItemName(entryData), WILDCARD_VALUE, entryData[2]);
                break;
            case 4:
                // ModId|ItemName|Metadata|OreDict
                LogHelper.info(String.format("Registering item {}:{}:{} as {}", entryData[0], entryData[1],
                                             entryData[2], entryData[3]));

                registerEntry(buildItemName(entryData), parseMetadata(entryData[2]), entryData[3]);
                break;
            default:
                // lolidk wut u did.
                LogHelper.warn(String.format("Unknown Config Value: {}", String.join(DELIMITER, entryData)));
                break;
        }
    }

    private static String buildItemName (final String[] entryData)
    {
        return entryData[0] + COLON + entryData[1];
    }

    private static void registerEntry (final String itemName, final int metadata, final String oreDictEntry)
    {
        registerOre(oreDictEntry, GameRegistry.makeItemStack(itemName, metadata, 0, ""));
    }

    private static int parseMetadata (final String input)
    {
        try
        {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException nfe)
        {
            return WILDCARD_VALUE;
        }
    }

}
