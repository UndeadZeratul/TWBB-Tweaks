package com.undeadzeratul.twbbtweaks.reference;

import java.util.Map;

public class Settings
{
    public static class BetterBeginnings
    {
        public static boolean disableBBSmelter;

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
        public static boolean              adjustMeltingTemps;
        public static boolean              adjustToolPartCosts;
        public static boolean              enableTiCTweaks;
        public static Map<String, Integer> meltingTemps;
        public static Map<String, Integer> toolPartCosts;
    }

    public static class Tweaks
    {
        public static String[] oreDictionaryAdditions;
    }
}
