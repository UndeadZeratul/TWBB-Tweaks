package com.undeadzeratul.twbbtweaks;

import static com.undeadzeratul.twbbtweaks.reference.Names.ModIds.TIC;
import static com.undeadzeratul.twbbtweaks.reference.Reference.CLIENT_PROXY;
import static com.undeadzeratul.twbbtweaks.reference.Reference.DEPENDENCIES;
import static com.undeadzeratul.twbbtweaks.reference.Reference.GUI_FACTORY_CLASS;
import static com.undeadzeratul.twbbtweaks.reference.Reference.MOD_ID;
import static com.undeadzeratul.twbbtweaks.reference.Reference.MOD_NAME;
import static com.undeadzeratul.twbbtweaks.reference.Reference.SERVER_PROXY;
import static com.undeadzeratul.twbbtweaks.reference.Reference.VERSION;

import com.undeadzeratul.twbbtweaks.handler.ConfigurationHandler;
import com.undeadzeratul.twbbtweaks.proxy.IProxy;
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
@Mod (modid = MOD_ID, name = MOD_NAME, version = VERSION, guiFactory = GUI_FACTORY_CLASS, dependencies = DEPENDENCIES)
public class TwbbTweaks
{
    @Mod.Instance (MOD_ID)
    public static TwbbTweaks instance;

    @SidedProxy (clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
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
        if (Loader.isModLoaded(TIC))
        {
            TConstructTweaks.init();
        }
    }
}
