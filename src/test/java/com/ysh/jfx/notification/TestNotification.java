package com.ysh.jfx.notification;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class TestNotification extends Application {
    // 基本用法
    @FXML
    private Button autoCloseBtn; // 可自动关闭
    
    @FXML
    private Button notAutoCloseBtn; // 不会自动关闭
    
    // 带有倾向性
    @FXML
    private Button successBtn;
    
    @FXML
    private Button warningBtn;
    
    @FXML
    private Button infoBtn;
    
    @FXML
    private Button errorBtn;
    
    
    // 自定义弹出位置
    @FXML
    private Button topRightBtn;
    
    @FXML
    private Button bottomRightBtn;
    
    @FXML
    private Button bottomLeftBtn;
    
    @FXML
    private Button topLeftBtn;
    
    // 自定义显示任意内容
    
    @FXML
    private Button useNodeBtn;
    
    // 隐藏关闭按钮
    @FXML
    private Button hideCloseBtn;
    
    // 回调函数
    @FXML
    private Button clickCallbackBtn; // 点击 通知 时的回调函数
    
    @FXML
    private Button closeCallbackBtn; // 关闭时的回调函数
    
    @FXML
    private Button onShownCallbackBtn; // 通知显示完毕的回调函数
    
    public void initialize() {
        initFirstTab(); // 第1个tab
        initSecondTab(); // 第2个tab
        initThirdTab(); // 第3个tab
        initFourthTab(); // 第4个tab
        initFifthTab(); // 第5个tab
        initSixthTab(); // 第6个tab
    }
    
    private void initSixthTab() {
        clickCallbackBtn.setOnAction(event -> {
            Notification.$notify.success("提示", "演示设置点击通知回调函数", 0).setOnClick(event1 -> {
                Notification.$notify.info("你点击了通知").show();
            }).setShowClose(false).show();
        });
        
        closeCallbackBtn.setOnAction(event -> {
            Notification.$notify.success("提示", "演示通知关闭的回调函数", 0).setOnClose(event1 -> {
                Notification.$notify.info("通知已关闭").show();
            }).show();
        });
        
        onShownCallbackBtn.setOnAction(event -> {
            Notification.$notify.success("提示", "演示通知显示完毕回调函数", 0).setOnShown(event1 -> {
                Notification.$notify.info("通知已显示完毕").show();
            }).show();
        });
    }
    
    private void initFifthTab() {
        hideCloseBtn.setOnAction(event -> {
            Notification.$notify.info("Info", "这是一条没有关闭按钮的消息").setShowClose(false).show();
        });
    }
    
    private void initFourthTab() {
        useNodeBtn.setOnAction(event -> {
            VBox content = new VBox();
            Slider slider = new Slider(0, 100, 50);
            Label label = new Label("set value");
            label.textProperty().bind(slider.valueProperty().asString());
            content.getChildren().addAll(slider, label);
            content.setSpacing(10);
            content.setAlignment(Pos.CENTER);
            Notification.$notify.success("success").setContent(content).show();
        });
        
    }
    
    
    private void initThirdTab() {
        topRightBtn.setOnAction(event -> {
            Notification.$notify("自定义位置", "右上角弹出的消息").show();
        });
        bottomRightBtn.setOnAction(event -> {
            Notification.$notify("自定义位置", "右下角弹出的消息").setPos(NotificationPosition.BOTTOM_RIGHT).show();
        });
        bottomLeftBtn.setOnAction(event -> {
            Notification.$notify("自定义位置", "左下角弹出的消息").setPos(NotificationPosition.BOTTOM_LEFT).show();
        });
        topLeftBtn.setOnAction(event -> {
            Notification.$notify("自定义位置", "左上角弹出的消息").setPos(NotificationPosition.TOP_LEFT).show();
        });
    }
    
    private void initSecondTab() {
        successBtn.setOnAction(event -> {
            // 第一种写法
            Notification.$notify("成功", "这是一条成功的提示消息").setType(NotificationLevel.SUCCESS).show();
            // 第二种写法
            Notification.$notify.success("成功", "这是一条成功的提示消息").show();
        });
        warningBtn.setOnAction(event -> {
            // 第一种写法
            Notification.$notify("警告", "这是一条警告的提示消息").setType(NotificationLevel.WARNING).show();
            // 第二种写法
            Notification.$notify.warning("警告", "这是一条警告的提示消息").show();
        });
        infoBtn.setOnAction(event -> {
            // 第一种写法
            Notification.$notify("消息", "这是一条消息的提示消息").setType(NotificationLevel.INFO).show();
            // 第二种写法
            Notification.$notify.info("消息", "这是一条消息的提示消息").show();
        });
        errorBtn.setOnAction(event -> {
            // 第一种写法
            Notification.$notify("错误", "这是一条错误的提示消息").setType(NotificationLevel.INFO).show();
            // 第二种写法
            Notification.$notify.info("错误", "这是一条错误的提示消息").show();
        });
    }
    
    private void initFirstTab() {
        autoCloseBtn.setOnAction(event -> {
            Notification.$notify("标题名称", "这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案").show();
        });
        notAutoCloseBtn.setOnAction(event -> {
            Notification.$notify("提示", "这是一条不会自动关闭的消息", 0).show();
        });
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TestNotification.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setTitle("test Notification");
        stage.show();
        
        
        stage.setOnCloseRequest(event -> {
            Platform.exit(); // 因为，有一个隐藏的emptyStage不会关闭，所以，这里要重写stage的关闭方法
        });
    }
    
    
}
