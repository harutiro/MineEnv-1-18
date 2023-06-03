package com.example.examplemod.mc_15_tobisuke;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelTobisuke<T extends EntityTobisuke> extends AgeableListModel<T> {

    private final ModelPart head;
    private final ModelPart body;

    public ModelTobisuke(ModelPart modelPart) {
        this.head = modelPart.getChild("head");
        this.body = modelPart.getChild("body");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        // 頭に関連するところ
        partDefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        // 頭の形状
                        .texOffs(0, 0)
                        .addBox(-3.0F, -1.0F, -7.0F, 6.0F, 6.0F, 6.0F)
                        // くちばし
                        .texOffs(24, 0)
                        .addBox(-1.5f, 3.0f, -8.0f, 3, 1, 1),
                PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        // 体に関連するところ
        partDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        // 体
                        .texOffs(0, 18)
                        .addBox(-4.5f, -6.0f, -7.5f, 9, 6, 12)
                        // 翼右
                        .texOffs(30, 0)
                        .addBox(-5.5f, -5.0f, -7.0f, 1, 4, 8)
                        // 翼左
                        .texOffs(30, 0)
                        .addBox(4.5f, -5.0f, -7.0f, 1, 4, 8),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        // .texOffs(x方向に動かす量,y方向に動かす量)
        // .addBox(始点ｘ,始点ｙ,始点ｚ,幅,高さ,奥行き

        // 新しく描画するものを作り、読み込むテクスチャを64*64に指定する
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(
            T entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI) * 0.25F;
        this.body.xRot = netHeadYaw / (180F / (float) Math.PI) * 0.25F;
    }
}
