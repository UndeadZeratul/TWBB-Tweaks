package com.undeadzeratul.twbbtweaks.handler;

import static com.undeadzeratul.twbbtweaks.reference.Reference.MOD_ID;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.nincraft.nincraftlib.utility.LogHelper;
import com.undeadzeratul.twbbtweaks.reference.ConfigurationTwbbTweaks;
import com.undeadzeratul.twbbtweaks.reference.Settings;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
        loadBetterBeginningsConfigs(ConfigurationTwbbTweaks.CATEGORY_BETTER_BEGINNINGS);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    private static void loadTweakConfigs (final String category)
    {
        Settings.Tweaks.disabledItems = configuration
                .getStringList("disabledItems", category, new String[0],
                               "Disable items by removing their crafting recipe(s), formatted as 'modID|itemName|metadata'.");
        Settings.Tweaks.oreDictionaryAdditions = configuration
                .getStringList("oreDictionaryAdditions", category, new String[0],
                               "Add items to the ore dictionary formatted as 'modID|itemName|metadata|oreDictionaryName'.");
    }

    private static void loadTConstructConfigs (final String category)
    {
        Settings.TConstruct.enableTiCTweaks = configuration.getBoolean("enableTiCTweaks", category, true,
                                                                       "This will enable/disable all TiC tweaks.");
        Settings.TConstruct.adjustAlloyRatios = configuration
                .getBoolean("adjustAlloyRatios", category, true,
                            "Set this to true to alter the list of fluids used to mix together into new alloys.");
        Settings.TConstruct.adjustMeltingTemps = configuration
                .getBoolean("adjustMeltingTemps", category, true,
                            "Set this to true to alter the melting temperatures for various molten metals in the TiC smeltery.");
        Settings.TConstruct.adjustToolPartCosts = configuration
                .getBoolean("adjustToolPartCosts", category, true,
                            "Set this to true to alter the TiC tool part costs across the board.");
        Settings.TConstruct.alloyRatios = parseAlloyRatiosConfig(category, "alloyRatios");
        Settings.TConstruct.meltingTemps = parseMeltingTempsConfig(category, "meltingTemps");
        Settings.TConstruct.toolPartCosts = parseToolPartCostsConfig(category, "toolPartCosts");
    }

    private static void loadBetterBeginningsConfigs (final String category)
    {
        Settings.BetterBeginnings.enableBBTweaks = configuration
                .getBoolean("enableBBTweaks", category, true, "This will enable/disable all BetterBeginnings tweaks.");

        loadBBRecipeNerfConfigs(category + ".recipeNerfs");
    }

    private static void loadBBRecipeNerfConfigs (final String category)
    {
        Settings.BetterBeginnings.nerfCraftingRecipes = configuration
                .getBoolean("enableAllCraftingRecipeNerfs", category, true,
                            "Set this to true to activate all recipe nerfs, making it so that any recipe nerfed will require the advanced crafting table.");
        Settings.BetterBeginnings.nerfAllArmorRecipes = configuration
                .getBoolean("nerfAllArmorRecipes", category, true,
                            "Set this to true to nerf all armor recipes to require the advanced crafting table.");
        Settings.BetterBeginnings.nerfAllToolRecipes = configuration
                .getBoolean("nerfAllToolRecipes", category, true,
                            "Set this to true to nerf all tool recipes to require the advanced crafting table.");
        Settings.BetterBeginnings.nerfAllMiscRecipes = configuration
                .getBoolean("nerfAllMiscRecipes", category, true,
                            "Set this to true to nerf other random recipes to require the advanced crafting table.");

        Settings.BetterBeginnings.nerfAllCookingRecipes = configuration
                .getBoolean("nerfAllCookingRecipes", category, true,
                            "Set this to true to nerf various cooking recipes to require the brick oven, campfire, or kiln.");
        Settings.BetterBeginnings.nerfAllBrickOvenRecipes = configuration
                .getBoolean("nerfAllBrickOvenRecipes", category, true,
                            "Set this to true to nerf other random recipes to require the advanced crafting table.");
        Settings.BetterBeginnings.nerfAllCampfireRecipes = configuration
                .getBoolean("nerfAllCampfireRecipes", category, true,
                            "Set this to true to nerf other random recipes to require the advanced crafting table.");
        Settings.BetterBeginnings.nerfAllKilnRecipes = configuration
                .getBoolean("nerfAllKilnRecipes", category, true,
                            "Set this to true to nerf other random recipes to require the advanced crafting table.");
    }

    private static Map<FluidStack, List<FluidStack>> parseAlloyRatiosConfig (final String category, final String key)
    {
        Map<FluidStack, List<FluidStack>> map = new HashMap<FluidStack, List<FluidStack>>();

        for (final String entry : configuration
                .getStringList(key, category,
                               new String[0], "Provide a list of fluid dictionary names, their amount in mBs, " +
                                              "and a list of other fluid dictioanry names and their amounts in mBs " +
                                              "that will mix together in a TiC Smeltery," +
                                              "in the format 'fluidName|amount|fluidName|amount|fluidName|amount'."))
        {
            String[] entryData = entry.split(DELIMITER_REGEX);

            if (ArrayUtils.isNotEmpty(entryData))
            {
                // Alloys need at least one output and two inputs,
                // so 3 sets of 2 values, or a even length of at least 6.
                if (entryData.length > 5 && entryData.length % 2 == 0)
                {
                    if (FluidRegistry.isFluidRegistered(entryData[0]))
                    {
                        FluidStack output = FluidRegistry.getFluidStack(entryData[0], Integer.valueOf(entryData[1]));
                        List<FluidStack> inputs = new ArrayList<FluidStack>();

                        // Skip past the first two, which represent the output,
                        // to get to the list of inputs.
                        for (int i = 2; i + 1 < entryData.length; i += 2)
                        {
                            if (FluidRegistry.isFluidRegistered(entryData[i]))
                            {
                                inputs.add(FluidRegistry.getFluidStack(entryData[i],
                                                                       Integer.valueOf(entryData[i + 1])));
                            }
                        }

                        // Only register the alloy if the output fluid
                        // as well as the list of input fluids are valid.
                        if (output != null && inputs.size() > 1)
                        {
                            map.put(output, inputs);
                        }
                    }
                }
                else
                {
                    LogHelper.warn(String.format("Invalid config option: %s", entryData[0]));
                }
            }
        }

        return map;
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
