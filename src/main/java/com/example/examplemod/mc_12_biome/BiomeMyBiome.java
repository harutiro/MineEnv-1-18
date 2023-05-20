package com.example.examplemod.mc_12_biome;

import com.example.examplemod.ExampleMod;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeMyBiome {
    public static Biome makeMyBiome() {
        // バイオームを生成するプログラム
        BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder = new BiomeGenerationSettings.Builder();

        // 花の設定
        PlacedFeature flowerSettings = BiomeUtil.makeFlowerSpawnSetting(
                new BiomeUtil.FlowerData[]{
                        new BiomeUtil.FlowerData(Blocks.POPPY, 1),// 花の種類と出現確率を指定する
                        new BiomeUtil.FlowerData(Blocks.DANDELION, 2)
                },
                "my_biome_flower", 10 // 花の量をしてい
        );
        biomeGenerationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, flowerSettings);

        //地下に生成する鉱石ブロックの設定
        PlacedFeature oreSettings = BiomeUtil.makeOreSpawnSetting(
                "my_biome_ore",
                //                       ブロックの指定      まとめて生成する数 生成する確率 生成する最低高さ　生成する最高高さ
                new BiomeUtil.OreData(Blocks.DIAMOND_BLOCK, 12, 256, 50, 63)
        );

        biomeGenerationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, oreSettings);

        // 木の設定
        PlacedFeature myBiomeTree = BiomeUtil.makeTreeSetting("my_biome_tree", // 木の名前
                Blocks.OAK_LOG, // 木の幹のブロック
                Blocks.OAK_LEAVES, // 木の葉のブロック
                Blocks.OAK_SAPLING // 木の苗のブロック
        );
        biomeGenerationSettingsBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, myBiomeTree);

        MobSpawnSettings.Builder mobSpawnSettingsBuilder = new MobSpawnSettings.Builder();

        // クリーチャーのスポーン設定
        mobSpawnSettingsBuilder.addSpawn(
                MobCategory.CREATURE, // スポーンさせるクリーチャーの種類
                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 1, 100, 100)
        );

        // モンスターのスポーン設定
        mobSpawnSettingsBuilder.addSpawn(
                MobCategory.MONSTER, // スポーンさせるモンスターの種類
                new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 100, 100)
        );

//        // とびすけをスポーンさせる
//        mobSpawnSettingsBuilder.addSpawn(
//                MobCategory.CREATURE, // スポーンさせるクリーチャーの種類
//                new MobSpawnSettings.SpawnerData(ExampleMod.ENTITY_TO, 1, 1, 1)
//        )

        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .biomeCategory(Biome.BiomeCategory.ICY)
                .temperature(0.0f) // 気温を設定する 0.15以下だと雪が降る
                .downfall(1.0f)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xffffff)
                        .waterFogColor(0x000000)
                        .fogColor(0x0d8ff)
                        .skyColor(0x55ffff) // 空の色を設定する
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
                        .build()
                )
                .mobSpawnSettings(mobSpawnSettingsBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();
    }
}
