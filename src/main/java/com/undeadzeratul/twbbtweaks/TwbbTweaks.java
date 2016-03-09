package com.undeadzeratul.twbbtweaks;

import com.undeadzeratul.twbbtweaks.handler.ConfigurationHandler;
import com.undeadzeratul.twbbtweaks.proxy.IProxy;
import com.undeadzeratul.twbbtweaks.reference.Names.ModIds;
import com.undeadzeratul.twbbtweaks.reference.Reference;
import com.undeadzeratul.twbbtweaks.tweaks.BetterBeginningsTweaks;
import com.undeadzeratul.twbbtweaks.tweaks.OreDictionaryRegister;
import com.undeadzeratul.twbbtweaks.tweaks.TConstructTweaks;
import com.undeadzeratul.twbbtweaks.utility.LogHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Undead_Zeratul
 */
@Mod (modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
      guiFactory = Reference.GUI_FACTORY_CLASS, dependencies = Reference.DEPENDENCIES)
public class TwbbTweaks
{
    @Mod.Instance (Reference.MOD_ID)
    public static TwbbTweaks instance;

    @SidedProxy (clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy     proxy;

    /**
     * Pre-Init
     *
     * @param event
     */
    @Mod.EventHandler
    public void preInit (final FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        proxy.registerEventHandlers();

        OreDictionaryRegister.init();

        LogHelper.info("Pre-Init Complete");
    }

    /**
     * Init
     *
     * @param event
     */
    @Mod.EventHandler
    public void init (final FMLInitializationEvent event)
    {
        initBetterBeginningsCompat();
        LogHelper.info("Init Complete");
    }

    /**
     * Post-Init
     *
     * @param event
     */
    @Mod.EventHandler
    public void postInit (final FMLPostInitializationEvent event)
    {
        initTConstructCompat();
        LogHelper.info("Post-Init Complete");
    }

    private void initTConstructCompat ()
    {
        if (Loader.isModLoaded(ModIds.TIC))
        {
            LogHelper.info("Initializing Tinkers' Construt Tweaks");
            TConstructTweaks.init();
        }
    }

    private void initBetterBeginningsCompat ()
    {
        if (Loader.isModLoaded(ModIds.BETTER_BEGINNINGS))
        {
            LogHelper.info("Initializing Better Beginnings Tweaks");
            BetterBeginningsTweaks.init();
        }
    }
}
