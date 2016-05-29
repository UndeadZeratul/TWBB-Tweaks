package com.undeadzeratul.twbbtweaks.tweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nincraft.nincraftlib.handler.TConstructHandler;
import com.nincraft.nincraftlib.utility.LogHelper;
import com.undeadzeratul.twbbtweaks.handler.TSteelworksHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.tweaks.wrapper.MeltingRecipeWrapper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.tools.items.Pattern;

public class TConstructTweaks
{
    private static final String       DELIMITER_REGEX = "\\|";

    private static final List<String> patternNames    = Lists.newArrayList(
        "rod",
        "pickaxe",
        "shovel",
        "axe",
        "swordblade",
        "largeguard",
        "mediumguard",
        "crossbar",
        "binding",
        "frypan",
        "sign",
        "knifeblade",
        "chisel",
        "largerod",
        "toughbinding",
        "largeplate",
        "broadaxe",
        "scythe",
        "excavator",
        "largeblade",
        "hammerhead",
        "fullguard",
        "bowstring",
        "fletching",
        "arrowhead"
    );

    public static void init ()
    {
        if (Settings.TConstruct.enableTiCTweaks)
        {
            addMeltingRecipes();

            if (Settings.TConstruct.adjustAlloyRatios)
            {
                adjustAlloyRatios();
            }

            if (Settings.TConstruct.adjustMeltingTemps)
            {
                adjustMeltingTemps();
            }

            if (Settings.TConstruct.adjustToolPartCosts)
            {
                adjustToolPartcosts();
            }
        }
    }

    private static void addMeltingRecipes ()
    {
        for (MeltingRecipeWrapper recipe : parseNewMeltingRecipesConfig(Settings.TConstruct.newMeltingRecipes))
        {
            TConstructHandler.removeMeltingRecipe(recipe.getInput());
            TConstructHandler.addMeltingRecipe(recipe.getInput(), recipe.getOutput(), recipe.getMeltingPoint(), recipe.getBlock());
        }
    }

    private static List<MeltingRecipeWrapper> parseNewMeltingRecipesConfig (final String[] configEntries)
    {
        List<MeltingRecipeWrapper> recipeList = new ArrayList<MeltingRecipeWrapper>();

        for (String entry : configEntries)
        {
            String[] entryData = entry.split(DELIMITER_REGEX);

            if (ArrayUtils.isNotEmpty(entryData))
            {
                MeltingRecipeWrapper recipe = new MeltingRecipeWrapper();

                switch (entryData.length)
                {
                    // modId|itemName|metadata|fluidName|amount|temperature|modId|blockName|metadata
                    case 9:
                        recipe.setInput(new ItemStack(GameRegistry.findItem(entryData[0], entryData[1]),
                                                      1, Integer.valueOf(entryData[2])));
                        recipe.setOutput(FluidRegistry.getFluidStack(entryData[3], Integer.valueOf(entryData[4])));
                        recipe.setMeltingPoint(Integer.valueOf(entryData[5]));
                        recipe.setBlock(new ItemStack(GameRegistry.findItem(entryData[6], entryData[7]),
                                                      1, Integer.valueOf(entryData[8])));
                        recipeList.add(recipe);
                        break;
                    // modId|itemName|fluidName|amount|temperature|modId|blockName|metadata
                    case 8:
                        recipe.setInput(new ItemStack(GameRegistry.findItem(entryData[0], entryData[1]),
                                                      1, OreDictionary.WILDCARD_VALUE));
                        recipe.setOutput(FluidRegistry.getFluidStack(entryData[2], Integer.valueOf(entryData[3])));
                        recipe.setMeltingPoint(Integer.valueOf(entryData[4]));
                        recipe.setBlock(new ItemStack(GameRegistry.findItem(entryData[5], entryData[6]),
                                                      1, Integer.valueOf(entryData[7])));
                        recipeList.add(recipe);
                        break;
                    // oreDictName|fluidName|amount|temperature|modId|blockName|metadata
                    case 7:
                        if (OreDictionary.doesOreNameExist(entryData[0]))
                        {
                            for (ItemStack oreStack : OreDictionary.getOres(entryData[0]))
                            {
                                recipe.setInput(oreStack);
                                recipe.setOutput(FluidRegistry.getFluidStack(entryData[1], Integer.valueOf(entryData[2])));
                                recipe.setMeltingPoint(Integer.valueOf(entryData[3]));
                                recipe.setBlock(new ItemStack(GameRegistry.findItem(entryData[4], entryData[5]),
                                                              1, Integer.valueOf(entryData[6])));
                                recipeList.add(recipe);
                            }
                        }
                        break;
                    // ayy lmao
                    default:
                        LogHelper.warn(String.format("Invalid config option: %s", String.join("|", entryData)));
                        break;
                }
            }
        }

        return recipeList;
    }

    private static void adjustAlloyRatios ()
    {
        for (Entry<FluidStack, List<FluidStack>> alloyEntry : parseAlloyRatiosConfig(Settings.TConstruct.alloyRatios).entrySet())
        {
            TConstructHandler.removeAlloy(alloyEntry.getKey());
            TConstructHandler.addAlloy(alloyEntry.getKey(), alloyEntry.getValue().toArray(new FluidStack[0]));
        }
    }

    private static Map<FluidStack, List<FluidStack>> parseAlloyRatiosConfig (final String[] configEntries)
    {
        Map<FluidStack, List<FluidStack>> map = new HashMap<FluidStack, List<FluidStack>>();

        for (final String entry : configEntries)
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

    private static void adjustMeltingTemps ()
    {
        for (final String fluidName : buildFluidDictEntries(Settings.TConstruct.meltingTemps.keySet()))
        {
            TConstructHandler.setMeltingTemp(fluidName, Settings.TConstruct.meltingTemps.get(fluidName));

            if (Loader.isModLoaded(ModIds.TISTEELWORKS))
            {
                TSteelworksHandler.init();
                TSteelworksHandler.setMeltingTemp(fluidName, Settings.TConstruct.meltingTemps.get(fluidName));
            }
        }
    }

    private static Set<String> buildFluidDictEntries (final Set<String> fluidNames)
    {
        Set<String> entryList = Sets.newHashSet();

        for (final String fluidName : fluidNames)
        {
            String fluidEntry = getFluidDictionaryEntry(fluidName);

            if (fluidEntry != null)
            {
                entryList.add(fluidEntry);
            }
        }

        return entryList;
    }

    private static String getFluidDictionaryEntry (final String fluidName)
    {
        for (final String registeredFluidName : FluidRegistry.getRegisteredFluids().keySet())
        {
            if (registeredFluidName.equalsIgnoreCase(fluidName))
            {
                return registeredFluidName;
            }
        }

        return null;
    }

    private static void adjustToolPartcosts ()
    {
        for (final Integer toolPartIndex : buildToolPartEntries(Settings.TConstruct.toolPartCosts.keySet()))
        {
            TConstructHandler.setToolPartCost(toolPartIndex,
                                              Settings.TConstruct.toolPartCosts.get(patternNames.get(toolPartIndex)));
        }
    }

    private static Set<Integer> buildToolPartEntries (final Set<String> toolParts)
    {
        Set<Integer> entryList = Sets.newHashSet();

        for (final String toolPart : toolParts)
        {
            Integer partIndex = getToolPartEntry(toolPart);

            if (partIndex != null)
            {
                entryList.add(partIndex);
            }
        }

        return entryList;
    }

    private static Integer getToolPartEntry (final String toolPart)
    {
        for (final Integer partIndex : Pattern.getPatternCosts().keySet())
        {
            if (patternNames.indexOf(toolPart) == partIndex)
            {
                return partIndex;
            }
        }

        return null;
    }
}
