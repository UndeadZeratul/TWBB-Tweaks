package com.undeadzeratul.twbbtweaks.tweaks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.core.helpers.Loader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.undeadzeratul.twbbtweaks.api.ITweak;
import com.undeadzeratul.twbbtweaks.api.ITweaksParser;
import com.undeadzeratul.twbbtweaks.reference.Reference;
import com.undeadzeratul.twbbtweaks.tweaks.parser.BBAdvancedCraftingParser;
import com.undeadzeratul.twbbtweaks.tweaks.parser.TiCSmelteryParser;
import com.undeadzeratul.twbbtweaks.utility.LogHelper;

import scala.actors.threadpool.Arrays;

public class TweaksParser
{
    private static File                       tweaksFolder;
    private static File                       defaultTweaks;
    private static final String               defaultTweaksInternal = "assets/com/undeadzeratul/tweaks/default.json";
    private static Map<String, ITweaksParser> tweaksHandlers        = Maps.newHashMap();

    public static List<ITweak>                parsedTweaks          = Lists.newArrayList();

    private TweaksParser ()
    {
    }

    public static boolean registerTweakHandler (final String template, final ITweaksParser handler)
    {
        if (!tweaksHandlers.containsKey(template))
        {
            tweaksHandlers.put(template, handler);
            return true;
        }

        LogHelper.error(String.format("Attempted to register duplicate tweaks handler '%s'!", template));
        return false;
    }

    public static void init ()
    {
        tweaksFolder = new File(Reference.configDir, Reference.MOD_CONFIG_DIRECTORY);
        if (!tweaksFolder.exists())
        {
            try
            {
                tweaksFolder.mkdir();
            }
            catch (Throwable t)
            {
                LogHelper.error(t);
            }
        }

        defaultTweaks = new File(Reference.configDir, Reference.MOD_CONFIG_DIRECTORY + "default.json");
        try
        {
            if (defaultTweaks.createNewFile())
            {
                IOUtils.copy(Loader.getResource(defaultTweaksInternal, null).openStream(),
                             new FileOutputStream(defaultTweaks));
            }
        }
        catch (Throwable t)
        {
            LogHelper.error(t);
        }

        LogHelper.info("Registering Default Handlers.");
        registerTweakHandler("advancedCrafting", new BBAdvancedCraftingParser());
        registerTweakHandler("smeltery", new TiCSmelteryParser());
        LogHelper.info("Default Handlers Registered Successfully.");
    }

    private static void addFiles (final List<File> fileList, final File folder)
    {
        File[] fileArr = folder.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept (final File file, final String fileName)
            {
                if (fileName == null)
                {
                    return false;
                }

                return fileName.toLowerCase(Locale.US).endsWith(".json") || new File(file, fileName).isDirectory();
            }
        });

        if (ArrayUtils.isEmpty(fileArr))
        {
            LogHelper.error(String.format("There are no Tweak files present in %s.", folder));
            return;
        }

        LogHelper.info(String.format("%s files found in %s/.", fileArr.length, folder));
        fileList.addAll(Arrays.asList(fileArr));
    }

    public static void parseTweaksFile ()
    {
        JsonParser parser = new JsonParser();

        List<File> tweaksList = Lists.newArrayList();
        addFiles(tweaksList, tweaksFolder);

        for (File tweakFile : tweaksList)
        {
            if (tweakFile.isDirectory())
            {
                addFiles(tweaksList, tweakFile);
                continue;
            }

            JsonObject genList;
            try
            {
                genList = (JsonObject) parser.parse(new InputStreamReader(new FileInputStream(tweakFile), "utf8"));
            }
            catch (Throwable t)
            {
                LogHelper.error(String
                        .format("Failed to read from tweaks file: %s.  Please make sure the file is correct.", t));
                continue;
            }

            LogHelper.info(String.format("Reading tweaks information from: %s", tweakFile));
            for (Entry<String, JsonElement> genEntry : genList.entrySet())
            {
                try
                {
                    if (parseTweakEntry(genEntry.getKey(), genEntry.getValue()))
                    {
                        LogHelper.debug(String.format("Tweak entry successfully parsed: '%s'", genEntry.getKey()));
                    }
                    else
                    {
                        LogHelper.error(
                                        String.format("Error parsing tweak entry: '%s'.  Please make sure the file is correct.",
                                                      genEntry.getKey()));
                    }
                }
                catch (Throwable t)
                {
                    LogHelper.fatal(String.format("There was a fatal error parsing '%s'!", t));
                }
            }
        }
    }

    private static boolean parseTweakEntry (final String tweakName, final JsonElement jsonEntry)
    {
        JsonObject jsonObject = jsonEntry.getAsJsonObject();

        if (jsonObject.has("enabled") && !jsonObject.get("enabled").getAsBoolean())
        {
            LogHelper.info(String.format("'%s' is disabled", tweakName));
            return true;
        }

        String templateName = parseTemplate(jsonObject);

        ITweaksParser tweakTemplate = tweaksHandlers.get(templateName);
        if (tweakTemplate != null)
        {
            ITweak tweak = tweakTemplate.parseTweak(tweakName, jsonObject);
            if (tweak != null)
            {
                parsedTweaks.add(tweak);
                return TweaksManager.addTweak(tweak);
            }
            LogHelper.warn(String.format("Tweak Template '%s' failed to parse its entry.", templateName));
        }
        else
        {
            LogHelper.warn(String.format("Unknown Tweak Template '%s'", templateName));
        }

        return false;
    }

    private static String parseTemplate (final JsonObject jsonObject)
    {
        JsonElement element = jsonObject.get("template");

        if (element.isJsonObject())
        {
            return jsonObject.getAsJsonObject().get("type").getAsString();
        }
        else
        {
            return element.getAsString();
        }
    }
}
