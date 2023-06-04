package com.example.examplemod.ramen_mod.event;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MouseEventHandler {

    public static SensingBase sensing = new SensingBase();

    @SubscribeEvent
    public static void onCursorMove(InputEvent.MouseScrollEvent event) {
        double x = event.getMouseX();
        double y = event.getMouseY();

        long time = System.currentTimeMillis();


        sensing.initSensorValues(x, y, time);
        sensing.calculation();
        boolean flag = sensing.checkSensor();
        if (flag) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
                    ItemStack stack = player.getInventory().getItem(slot);
                    if (stack.getItem() == ExampleMod.ITEM_WATER_NOODLE) {
                        ItemStack convertedStack = new ItemStack(ExampleMod.ITEM_NOODLE);
                        player.getInventory().setItem(slot, convertedStack);
                        // インベントリの更新
                        player.getInventory().setChanged();
                        if (!player.level.isClientSide) {
                            // サーバー側でインベントリの変更を反映する
                            player.inventoryMenu.broadcastChanges();
                        }
                        break;
                    }
                }
            }
        }

//        System.out.println(time + "," + x + "," + y + ",");
    }
}
