package com.ysh.jfx.notification;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;


public class Notification implements INotification {
    
    @FXML
    private StackPane root;
    
    @FXML
    private HBox hBox;
    
    @FXML
    private StackPane content;
    
    @FXML
    private VBox vBox;
    
    @FXML
    private HBox titleHbox;
    
    @FXML
    private Label messageLabel;
    
    private static final ArrayList<Notification> instances = new ArrayList<>();// 存放所有当前的通知实例，显示时添加，移除时删除
    private static final EmptyStage emptyStage = new EmptyStage();
    public static final INotification $notify = new Notification(); // 用于直接使用success等方法的实例
    private static final double SCREEN_PADDING = 16.0; // 默认距离屏幕的距离
    private static final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    private final Popup popup = new Popup();
    // 移动动画
    private TranslateTransition translate;
    private NotificationLevel type = NotificationLevel.INFO;
    private Label titleLabel;
    private int duration = 4500; // 显示时间, 毫秒。设为 0 则不会自动关闭
    
    private double verticalOffset = 0; // 每个通知显示时的垂直偏移量
    
    private boolean showClose = true; // 是否显示关闭按钮
    private NotificationPosition notificationPosition = NotificationPosition.TOP_RIGHT; // 通知位置，默认右上
    private EventHandler<ActionEvent> onClose; // 关闭时的回调函数
    private EventHandler<ActionEvent> onClick; // 点击 通知 时的回调函数
    
    private EventHandler<ActionEvent> onShown; // 通知显示完毕的回调函数
    
    private static final double maxX;
    
    private static final double maxY;
    
    private static final double minX;
    
    private static final double minY;
    
    static {
        maxX = visualBounds.getMaxX();
        maxY = visualBounds.getMaxY();
        minX = visualBounds.getMinX();
        minY = visualBounds.getMinY();
        // 如果空窗口没有显示，则直接显示
        if (!emptyStage.isShowing()) {
            emptyStage.show();
        }
    }
    
    public Notification(String message) {
        initFXML();
        // root在initFXML后才有值，所以，translate 只能在这里初始化
        translate = new TranslateTransition(Duration.millis(300), root);
        translate.setOnFinished(event -> {
            if (onShown != null) {
                onShown.handle(new ActionEvent());
            }
        });
        popup.getContent().add(root);
        popup.setAutoHide(false);
        popup.setAutoFix(false);
        initEvent();
        setMessage(message);
    }
    
    /**
     * 初始化fxml文件，初始化布局组件
     */
    private void initFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Notification.fxml"));
        
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private Notification() {
    }
    
    /**
     * 初始化事件
     */
    private void initEvent() {
        root.setOnMouseClicked(event -> {
            if (onClick != null) {
                onClick.handle(new ActionEvent());
            }
        });
        
        popup.setOnShown(event -> {
            // 根据 notificationPosition 的值来决定显示在什么位置
            // 当显示第2个及后面的通知时，需要根据 instances 中保存的通知实例来动态计算当前通知的要显示的位置
            // 按位置来过滤
            instances.stream().filter(notification -> notificationPosition.equals(notification.notificationPosition)).forEach(notification -> {
                verticalOffset += notification.root.getHeight() + SCREEN_PADDING;
            });
            verticalOffset += SCREEN_PADDING;
            // 根据显示的位置来动态计算通知要显示的位置坐标，并设置动画
            if (notificationPosition.equals(NotificationPosition.TOP_RIGHT)) {
                this.translate.setFromX(maxX);
                this.translate.setToX(maxX - popup.getWidth() - SCREEN_PADDING);
                popup.setY(minY + verticalOffset);
            } else if (notificationPosition.equals(NotificationPosition.TOP_LEFT)) {
                this.popup.setX(minX - popup.getWidth());
                this.translate.setFromX(minX);
                this.translate.setToX(popup.getWidth() + SCREEN_PADDING);
                popup.setY(minY + verticalOffset);
            } else if (notificationPosition.equals(NotificationPosition.BOTTOM_LEFT)) {
                popup.setY(maxY - verticalOffset - popup.getHeight());
                this.popup.setX(minX - popup.getWidth());
                this.translate.setFromX(minX);
                this.translate.setToX(popup.getWidth() + SCREEN_PADDING);
            } else if (notificationPosition.equals(NotificationPosition.BOTTOM_RIGHT)) {
                popup.setY(maxY - verticalOffset - popup.getHeight());
                this.translate.setFromX(maxX);
                this.translate.setToX(maxX - popup.getWidth() - SCREEN_PADDING);
            }
            this.translate.play(); // 播放动画
        });
    }
    
    public int getDuration() {
        return duration;
    }
    
    /**
     * 设置通知显示时间，0为永久显示,单位ms
     *
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    public Notification setDuration(int duration) {
        this.duration = duration;
        return this;
    }
    
    public Node getContent() {
        return content.getChildren().get(0);
    }
    
    public Notification setContent(Node content) {
        this.content.getChildren().set(0, content);
        return this;
    }
    
    /**
     * 获取通知显示的文本
     *
     * @return 通知显示的文本
     */
    public String getMessage() {
        return messageLabel.getText();
    }
    
    /**
     * 设置通知显示的文本
     *
     * @return {@link Notification}
     */
    public Notification setMessage(String message) {
        messageLabel.setText(message);
        return this;
    }
    
    /**
     * 是否显示关闭按钮
     *
     * @return true为显示关闭
     */
    public boolean isShowClose() {
        return showClose;
    }
    
    /**
     * 设置是否显示关闭按钮
     *
     * @return {@link Notification}
     */
    public Notification setShowClose(boolean showClose) {
        this.showClose = showClose;
        return this;
    }
    
    /**
     * 设置通知关闭的回调函数
     *
     * @param onClose 通知关闭的回调函数
     * @return {@link Notification}
     */
    public Notification setOnClose(EventHandler<ActionEvent> onClose) {
        this.onClose = onClose;
        return this;
    }
    
    /**
     * 设置点击通知的回调函数
     *
     * @param onClick 点击通知的回调函数
     * @return {@link Notification}
     */
    public Notification setOnClick(EventHandler<ActionEvent> onClick) {
        this.onClick = onClick;
        return this;
    }
    
    /**
     * 设置通知显示完毕的回调函数
     *
     * @param onShown 通知显示完毕的回调函数
     * @return {@link Notification}
     */
    public Notification setOnShown(EventHandler<ActionEvent> onShown) {
        this.onShown = onShown;
        return this;
    }
    
    /**
     * 终结操作，显示通知，任何没有调用此方法之前，都可以链式调用
     */
    public void show() {
        ////// 自动关闭 /////////////
        if (duration > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(this::close);
            }).start();
        }
        
        //////// 是否显示关闭按钮 //////////
        if (showClose) {
            Label closeLabel = new Label();
            closeLabel.setOnMouseClicked(event -> {
                close();
            });
            
            hBox.getChildren().add(closeLabel);
            closeLabel.setGraphic(new Region());
            closeLabel.getStyleClass().addAll("icon", "close");
            closeLabel.setCursor(Cursor.HAND);
        }
        
        // 是否显示左侧图标
        if (type != null) {
            Label iconLabel = new Label();
            // iconLabel.setGraphic(type.getFontIcon());
            iconLabel.setGraphic(new Region());
            iconLabel.getStyleClass().addAll("icon", type.getStyleClass());
            hBox.getChildren().add(0, iconLabel);
        }
        
        if (!popup.isShowing()) {
            popup.show(emptyStage, 0, 0);
            instances.add(this);
        }
    }
    
    /**
     * 关闭通知,关闭后调用关闭回调函数并重新计算剩余通知位置
     */
    public void close() {
        if (popup.isShowing()) {
            popup.hide();
            if (onClose != null) {
                onClose.handle(new ActionEvent());
            }
            resetHeightAfterClose();
        }
    }
    
    /**
     * 关闭一个通知后，重新计算剩余通知位置
     */
    private void resetHeightAfterClose() {
        int index = -1; // 删除元素的索引，用于计算高度
        // 要找到当前要移动的对象在instances中的index
        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).equals(this)) {
                index = i;
                break;
            }
        }
        // 如果没有找到，则return
        if (index == -1) {
            return;
        }
        
        instances.remove(index); // 移除删除的实例
        if (instances.size() == 0) {
            // 删除后的大小为0，返回
            return;
        }
        double removedHeight = root.getHeight();
        
        for (int i = index; i < instances.size(); i++) {
            Notification notification = instances.get(i);
            if (notification.notificationPosition.equals(this.notificationPosition)) {
                // 动态的改变y
                notification.verticalOffset = notification.verticalOffset - removedHeight - SCREEN_PADDING;
                notification.popup.setY(minY + notification.verticalOffset);
            }
        }
    }
    
    /**
     * 设置通知显示的位置
     *
     * @param pos 通知显示的位置 {@link NotificationPosition}
     * @return {@link Notification}
     */
    public Notification setPos(NotificationPosition pos) {
        this.notificationPosition = pos;
        return this;
    }
    
    /**
     * 获取通知显示的类型
     *
     * @return {@link NotificationLevel}
     */
    public NotificationLevel getType() {
        return type;
    }
    
    /**
     * 获取通知标题
     *
     * @return 通知标题
     */
    public String getTitle() {
        if (titleLabel != null) {
            return titleLabel.getText();
        }
        return null;
    }
    
    /**
     * 设置通知标题
     *
     * @param title 通知标题
     */
    public void setTitle(String title) {
        titleLabel = new Label(title);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.getStyleClass().add("el-notification__title");
        titleHbox.getChildren().add(titleLabel);
    }
    
    /**
     * 设置通知显示的类型
     *
     * @param type 通知显示的类型，可选值有 info,success,warning,error,{@link NotificationLevel}
     * @return {@link Notification}
     */
    public Notification setType(NotificationLevel type) {
        this.type = type;
        return this;
    }
    
    /**
     * 构造通知对象
     *
     * @param message 消息内容
     * @return {@link Notification}
     */
    public static Notification $notify(String message) {
        return new Notification(message);
    }
    
    public static Notification $notify(Node node) {
        Notification notification = new Notification("");
        notification.setContent(node);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title   标题
     * @param message 消息内容
     * @return {@link Notification}
     */
    public static Notification $notify(String title, String message) {
        Notification notification = $notify(message);
        notification.setTitle(title);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title    标题
     * @param message  消息内容
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    public static Notification $notify(String title, String message, int duration) {
        Notification notification = $notify(title, message);
        notification.setDuration(duration);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification success(String message) {
        Notification notification = $notify(message);
        notification.setType(NotificationLevel.SUCCESS);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title   标题
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification success(String title, String message) {
        Notification notification = success(message);
        notification.setTitle(title);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title    标题
     * @param message  消息内容
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    @Override
    public Notification success(String title, String message, int duration) {
        Notification notification = success(title, message);
        notification.setDuration(duration);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification info(String message) {
        Notification notification = $notify(message);
        notification.setType(NotificationLevel.INFO);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title   标题
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification info(String title, String message) {
        Notification notification = info(message);
        notification.setTitle(title);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title    标题
     * @param message  消息内容
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    @Override
    public Notification info(String title, String message, int duration) {
        Notification notification = info(title, message);
        notification.setDuration(duration);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification warning(String message) {
        Notification notification = $notify(message);
        notification.setType(NotificationLevel.WARNING);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title   标题
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification warning(String title, String message) {
        Notification notification = warning(message);
        notification.setTitle(title);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title    标题
     * @param message  消息内容
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    @Override
    public Notification warning(String title, String message, int duration) {
        Notification notification = warning(title, message);
        notification.setDuration(duration);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification error(String message) {
        Notification notification = $notify(message);
        notification.setType(NotificationLevel.ERROR);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title   标题
     * @param message 消息内容
     * @return {@link Notification}
     */
    @Override
    public Notification error(String title, String message) {
        Notification notification = error(message);
        notification.setTitle(title);
        return notification;
    }
    
    /**
     * 构造通知对象
     *
     * @param title    标题
     * @param message  消息内容
     * @param duration 通知显示时间，0为永久显示,单位ms
     * @return {@link Notification}
     */
    @Override
    public Notification error(String title, String message, int duration) {
        Notification notification = error(title, message);
        notification.setDuration(duration);
        return notification;
    }
}
