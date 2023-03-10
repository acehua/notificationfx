package com.ysh.jfx.notification;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 空窗口
 */
public class EmptyStage extends Stage {
    {
        this.initStyle(StageStyle.UTILITY); // 无任务栏图标
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: null;");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setWidth(1); // 设置0不起作用
        this.setHeight(1);
        this.setX(Double.MAX_VALUE); // 移动动特别远的地方，看不到
        this.setOpacity(0);// 全透明
    }
    
}
