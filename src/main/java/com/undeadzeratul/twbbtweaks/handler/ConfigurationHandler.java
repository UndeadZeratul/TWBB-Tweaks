package com.undeadzeratul.twbbtweaks.handler;

import static com.undeadzeratul.twbbtweaks.reference.Reference.MOD_ID;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.nincraft.nincraftlib.utility.LogHelper;
import com.undeadzeratul.twbbtweaks.reference.ConfigurationTwbbTweaks;
import com.undeadzeratul.twbbtweaks.reference.Settings;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
    private static final String DELIMITER_REGEX = "\\|";
    public static Configuration configuration;

    public static void init (final File configFile)
    {
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration ()
    {
        loadTweakConfigs(ConfigurationTwbbTweaks.CATEGORY_TWEAKS);
        loadTConstructConfigs(ConfigurationTwbbTweaks.CATEGORY_TCONSTRUCT);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    private static void loadTweakConfigs (final String category)
    {
        Settings.Tweaks.oreDictionaryAdditions = configuration.getStringList("oreDictionaryAdditions", category,
                                                                             new String[]
        {}, "Add items to the ore dictionary formatted as modID|itemName|Metadata|oreDictionaryName");
    }

    private static void loadTConstructConfigs (final String category)
    {
        Settings.TConstruct.enableTiCTweaks = configuration.getBoolean("enableTiCTweaks", category, true,
                                                                       "This will enable/disable all TiC tweaks.");
        Settings.TConstruct.adjustMeltingTemps = configuration
                .getBoolean("adjustMeltingTemps", category, true,
                            "Set this to true to alter the melting temperatures for various molten metals in the TiC smeltery.");
        Settings.TConstruct.adjustToolPartCosts = configuration
                .getBoolean("adjustToolPartCosts", category, true,
                            "Set this to true to alter the TiC tool part costs across the board.");
        Settings.TConstruct.meltingTemps = parseMeltingTempsConfig(category, "meltingTemps");
        Settings.TConstruct.toolPartCosts = parseToolPartCostsConfig(category, "toolPartCosts");
    }

    private static Map<String, Integer> parseMeltingTempsConfig (final String category, final String configKey)
    {
        return parseKeyValuePairs(category, configKey, new String[]
        {
            "tin.molten|230",
            "emerald.liquid|650",
            "aluminum.molten|660",
            "aluminumbrass.molten|950",
            "bronze.molten|950",
            "gold.molten|1050",
            "copper.molten|1080",
            "iron.molten|1400",
            "steel.molten|1500"
        }, "Provide a list of fluid dictionary names and their desired melting temperature, in the format 'fluidName|temperature'.");
    }

    private static Map<String, Integer> parseToolPartCostsConfig (final String category, final String configKey)
    {
        return parseKeyValuePairs(category, configKey, new String[]
        {
            "pickaxe|3",
            "shovel|1",
            "axe|3",
            "swordblade|2",
            "frypan|4",
            "sign|6",
            "knifeblade|1",
            "chisel|2",
            "largeplate|9",
            "broadaxe|9",
            "scythe|3",
            "excavator|9",
            "largeblade|9",
            "hammerhead|9"
        }, "Provide a list of tool part names and their costs in ingots, in the format 'toolPart|numIngots'.");
    }

    private static Map<String, Integer> parseKeyValuePairs (final String category, final String key,
                                                            final String[] defaultValues, final String comment)
    {
        Map<String, Integer> map = new HashMap<String, Integer>();

        for (final String entry : configuration.getStringList(key, category, defaultValues, comment))
        {
            String[] entryData = entry.split(DELIMITER_REGEX);

            if (ArrayUtils.isNotEmpty(entryData))
            {
                if (entryData.length > 1)
                {
                    map.put(entryData[0], Integer.parseInt(entryData[1]));
                }
                else
                {
                    LogHelper.warn(String.format("Invalid config option: %s", entryData[0]));
                }
            }
        }

        return map;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent (final ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(MOD_ID))
        {
            loadConfiguration();
        }
    }
}
