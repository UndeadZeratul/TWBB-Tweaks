package com.undeadzeratul.twbbtweaks.tweaks;

import java.util.List;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nincraft.nincraftlib.utility.LogHelper;
import com.undeadzeratul.twbbtweaks.api.ITweak;

public class TweaksManager
{
    public static TweaksManager instance   = new TweaksManager();

    private static List<ITweak> tweaks     = Lists.newArrayList();
    private static Set<String>  tweakNames = Sets.newHashSet();

    public static void init()
    {

    }

    public static boolean addTweak (final ITweak tweak)
    {
        return instance.registerTweak(tweak);
    }

    public boolean registerTweak (final ITweak tweak)
    {
        String tweakName = tweak.getTweakName();

        if (Strings.isNullOrEmpty(tweakName))
        {
            LogHelper.error("Tweak attempted to register without a valid name, ignoring.");
            return false;
        }

        if (tweakNames.contains(tweakName))
        {
            LogHelper.debug(String.format("Tweak %s attempted to be registered twice, ignoring.", tweakName));
            return false;
        }

        tweakNames.add(tweakName);
        tweaks.add(tweak);

        return true;
    }
}
