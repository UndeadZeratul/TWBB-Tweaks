package com.undeadzeratul.twbbtweaks.reference;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class Settings
{
    public static class TConstruct
    {
        public static boolean              enableTiCTweaks;
        public static boolean              adjustMeltingTemps;
        public static boolean              adjustToolPartCosts;
        public static Map<String, Integer> meltingTemps;
        public static Map<String, Integer> toolPartCosts;
    }

    public static class Tweaks
    {
        public static String[] oreDictionaryAdditions;
    }
}
