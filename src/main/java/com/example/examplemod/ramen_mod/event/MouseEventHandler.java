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

    // どうしても、マウスカーソルを動かした時に、イベントを走らせることは難しかったので、マウスホイールを回したイベントが走った時に
    // マウスの座標を取得するようにしました。
    @SubscribeEvent
    public static void onCursorMove(InputEvent.MouseScrollEvent event) {
        // マウスの座標を取得
        double x = event.getMouseX();
        double y = event.getMouseY();

        long time = System.currentTimeMillis();

        // 現在時間、x座標、y座標を記録
        sensing.initSensorValues(x, y, time);
        // センサー値の計算
        sensing.calculation();
        // センサーの閾値処理　マウスを振っているかを判定
        boolean flag = sensing.checkSensor();
        if (flag) {
            // インスタンスを取得
            Minecraft minecraft = Minecraft.getInstance();
            // プレイヤーを取得
            LocalPlayer player = minecraft.player;
            if (player != null) {
                // インベントリの捜査
                for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
                    // アイテムの取得
                    ItemStack stack = player.getInventory().getItem(slot);
                    // アイテムが水に濡れてる麺ならば
                    if (stack.getItem() == ExampleMod.ITEM_WATER_NOODLE) {
                        // 変換するアイテムの取得
                        ItemStack convertedStack = new ItemStack(ExampleMod.ITEM_NOODLE);
                        // インベントリのスロットに変換するアイテムをセット
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
    }
}
