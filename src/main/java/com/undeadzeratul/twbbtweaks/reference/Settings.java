package com.undeadzeratul.twbbtweaks.reference;

import java.util.List;
import java.util.Map;

import com.undeadzeratul.twbbtweaks.tweaks.wrapper.MeltingRecipeWrapper;

import net.minecraftforge.fluids.FluidStack;

public class Settings
{
    public static class BetterBeginnings
    {
        public static boolean enableBBTweaks;

        public static boolean nerfCraftingRecipes;

        public static boolean nerfAllArmorRecipes;
        public static boolean nerfAllToolRecipes;
        public static boolean nerfAllMiscRecipes;

        public static boolean nerfAllCookingRecipes;
        public static boolean nerfAllCampfireRecipes;
        public static boolean nerfAllBrickOvenRecipes;
        public static boolean nerfAllKilnRecipes;
    }

    public static class TConstruct
    {
        public static boolean                           adjustAlloyRatios;
        public static boolean                           adjustMeltingTemps;
        public static boolean                           adjustToolPartCosts;
        public static boolean                           enableTiCTweaks;
        public static Map<FluidStack, List<FluidStack>> alloyRatios;
        public static Map<String, Integer>              meltingTemps;
        public static Map<String, Integer>              toolPartCosts;
        public static List<MeltingRecipeWrapper>        newMeltingRecipes;
    }

    public static class Tweaks
    {
        public static String[] disabledItems;
        public static String[] oreDictionaryAdditions;
    }
}
