package de.theunknownxy.foggyrain;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = FoggyRain.MODID, version = FoggyRain.VERSION)
public class FoggyRain
{
    public static final String MODID = "foggyrain";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new FoggyRainHandler());
    }
}
