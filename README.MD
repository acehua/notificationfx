参考element ui 写的javafx 通知组件

## 一、组件说明
灵感部分参考以下：
- https://gitee.com/lichenfei_fei/chenfei-javafx-css.git 的control分支
- element ui https://element.eleme.cn/#/zh-CN/component/notification

兼容jdk8以上任意版本，因为，没有引入任何第三方库，纯手工编写

## 使用说明
目前还未上maven仓库

直接运行 com.ysh.jfx.notification.TestNotification

使用方法和element ui 的通知组件大部分一样
如下：
```java
    // 第一种写法
    Notification.$notify("成功", "这是一条成功的提示消息").setType(NotificationLevel.SUCCESS).show();
    
    // 第二种写法
    Notification.$notify.success("成功", "这是一条成功的提示消息").show();
    
    
```
