package com.undeadzeratul.twbbtweaks.client.gui;

import static com.undeadzeratul.twbbtweaks.reference.Reference.MOD_ID;
import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import com.undeadzeratul.twbbtweaks.handler.ConfigurationHandler;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig (GuiScreen parentScreen)
    {
        super(parentScreen,
              new ConfigElement(ConfigurationHandler.configuration.getCategory(CATEGORY_GENERAL)).getChildElements(),
              MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
    }
}
