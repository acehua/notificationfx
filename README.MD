English | [中文](./README-CN.md)

## a javafx notification component

### 🔎 Component description
Be inspired by：
- https://gitee.com/lichenfei_fei/chenfei-javafx-css.git 的control branch
- element ui https://element.eleme.cn/#/zh-CN/component/notification

### 🔎 Requirements
> Compatible with any version of jdk 8 and above, because, no third-party library has been introduced, pure manual writing
> 

### 🎮 Installation

maven:
```
    <dependency>
        <groupId>io.github.acehua</groupId>
        <artifactId>notificationfx</artifactId>
        <version>0.0.1</version>
    </dependency>
```

### 👨‍🎤 How to Use
It works much the same way as the notification component of the element ui:

```java
    // The first way
    Notification.$notify("success","This is a successful message").setType(NotificationLevel.SUCCESS).show();

    // The second way
    Notification.$notify.success("success","This is a successful message").show();

```
> run com.ysh.jfx.notification.TestNotification,and you can see below 
> Effect：


![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/1.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/2.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/3.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/4.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/5.jpg)

![Image text](https://gitee.com/vip_huage/notificationfx/raw/master/snapshots/6.jpg)
