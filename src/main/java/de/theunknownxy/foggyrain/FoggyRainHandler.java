package de.theunknownxy.foggyrain;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.lwjgl.opengl.GL11;

public class FoggyRainHandler {
    private float mix(float x, float y, float a) {
        return x * (1 - a) + y * a;
    }

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.RenderFogEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld.isRaining() && event.farPlaneDistance > 45f) {
            if (event.fogMode == 0 || event.fogMode == -1) {

                float rain_strength = mc.theWorld.rainingStrength;

                float far_mod = rain_strength;
                if (mc.theWorld.isThundering()) {
                    // Only reach the densest rain if it's thundering
                    far_mod = (far_mod + 0.2f);
                }
                far_mod /= 1f + 0.2f;

                int x = MathHelper.floor_double(mc.thePlayer.posX);
                int y = MathHelper.floor_double(mc.thePlayer.posY);
                int z = MathHelper.floor_double(mc.thePlayer.posZ);
                if (mc.theWorld != null && mc.theWorld.blockExists(x, y, z)) {
                    // Fade the effect out if the player cannot see skylight
                    Chunk chunk = mc.theWorld.getChunkFromBlockCoords(x, z);
                    int sl = chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15);
                    far_mod *= sl/15f;
                }

                // Slowly progress to the the densest possible rain but
                float fogf = mix(event.farPlaneDistance, 45f, far_mod);

                GL11.glFogf(GL11.GL_FOG_START, mix(0.75f * event.farPlaneDistance, 0.0f, rain_strength));
                GL11.glFogf(GL11.GL_FOG_END, fogf);
            }
        }
    }
}
