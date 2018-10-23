## 进程间通信（IPC）

#### Android进程间通信方式有几种

1. Bundle(主要用于activity/service/broadcast之间Intent传递)
1. Messenger
1. AIDL
1. ContentProvider(基于URI共享数据)
1. 基于本地文件读写（不适用与并发性高的场景）
1. socket

#### Messenger

##### Messenger双向通信时，出现 java.lang.ClassNotFoundException异常？

```kotlin
    // 设置classLoader解决
    val message: Message
    val data = message.data
    data.classLoader = javaClass.classLoader
```

> [Android中跨进程通信传递Parcelable对象时出现android.os.BadParcelableException:
ClassNotFoundException when unmarsh](https://blog.csdn.net/Bettarwang/article/details/45315091)

* Android有两种不同的classloaders，framework classloader和apk classloader
* framework classloader负责加载Android Classes
* apk classloader负责加载应用的classes
* apk classloader继承自framework classloader，也支持加载Android Classes
* 在应用刚启动时，默认class loader是apk classloader
* 在系统内存不足应用被系统回收会再次启动应用class loader会变为framework classloader

#### AIDL

##### AIDL不支持什么基础类型

short

##### AIDL文件参数类型in/inout/out区别

* in:
* inout:
* out:

#### AIDL中添加监听器

* 监听器要使用aidl来实现（IPC不支持内存共享）
* 使用RemoteCallbackList来注册监听器和反监听器
（aidl每次请求会将监听器转化成代理对象传到服务端，所有无法实现反注册）

#### AIDL服务耗时怎么处理

*

#### AIDL中Binder死亡

*

#### AIDL服务端权限验证

####


