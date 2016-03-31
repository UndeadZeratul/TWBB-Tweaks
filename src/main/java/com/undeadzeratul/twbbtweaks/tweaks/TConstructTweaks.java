package com.undeadzeratul.twbbtweaks.tweaks;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nincraft.nincraftlib.handler.TConstructHandler;
import com.undeadzeratul.twbbtweaks.handler.TSteelworksHandler;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Settings;
import com.undeadzeratul.twbbtweaks.tweaks.wrapper.MeltingRecipeWrapper;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.tools.items.Pattern;

public class TConstructTweaks
{

    private static final List<String> patternNames = Lists.newArrayList(
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
        for (MeltingRecipeWrapper recipe : Settings.TConstruct.newMeltingRecipes)
        {
            TConstructHandler.addMeltingRecipe(recipe.getInput(), recipe.getOutput(), recipe.getMeltingPoint(), recipe.getBlock());
        }
    }

    private static void adjustAlloyRatios ()
    {
        for (Entry<FluidStack, List<FluidStack>> alloyEntry : Settings.TConstruct.alloyRatios.entrySet())
        {
            TConstructHandler.removeAlloy(alloyEntry.getKey());
            TConstructHandler.addAlloy(alloyEntry.getKey(), alloyEntry.getValue().toArray(new FluidStack[0]));
        }
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
