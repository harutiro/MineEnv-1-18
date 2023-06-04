package com.example.examplemod.ramen_mod.event;

import com.example.examplemod.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MouseEventHandler {

//    @SubscribeEvent
//    public static void onMouseEvent(InputEvent.MouseInputEvent event) {
//        if (event.getAction() == GLFW.GLFW_PRESS && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
//            double mouseX = event.getCursorX();
//            double mouseY = event.getCursorY();
//
//            // マウスの座標を利用する処理を追加する
//        }
//    }
}
