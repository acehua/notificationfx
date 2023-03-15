[English](./README-CN.md) | 中文

参考element ui 写的javafx 通知组件

## 一、组件说明

灵感部分参考以下：

- https://gitee.com/lichenfei_fei/chenfei-javafx-css.git 的control分支
- element ui https://element.eleme.cn/#/zh-CN/component/notification

兼容jdk8以上任意版本，因为，没有引入任何第三方库，纯手工编写

## 使用说明

maven:
```
    <dependency>
        <groupId>io.github.acehua</groupId>
        <artifactId>notificationfx</artifactId>
        <version>0.0.1</version>
    </dependency>
```

使用方法和element ui 的通知组件大部分一样
如下：

```java
    // 第一种写法
    Notification.$notify("成功","这是一条成功的提示消息").setType(NotificationLevel.SUCCESS).show();
    
    // 第二种写法
    Notification.$notify.success("成功","这是一条成功的提示消息").show();

```
> 具体使用代码可以下载源码，运行 com.ysh.jfx.notification.TestNotification
> 效果如下图所示：


![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/1.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/2.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/3.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/4.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/5.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/6.jpg)
