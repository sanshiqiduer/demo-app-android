# 融云

![Mou icon](http://www.rongcloud.cn/images/logo_1.png)





#### 运行条件

**AndroidStudio 0.6+**

**Gradle 1.11+**

**RongIMSDK 0.9.5**

**Android Support V4**

*如需使用自行申请APP_Key测试，需要搭建自己的auth服务器*

*测试服务器搭建请参照<https://github.com/rongcloud/auth-service-nodejs>*

服务器方面需要配置conf.json中的appKey属性完成验证

*在DemoContext中init方法中填入申请的APP_Key测试，并在DemoApi中配置HOST指向到自己的auth服务上*

#### 注意事项：

imkit.jar中已经集成如下开源库为防重复引用特此提示

* com.sea_monster:core_jar:0.0.19
* com.github.chrisbanes.bitmapcache:library:2.3@jar
* com.jakewharton:disklrucache:2.0.2@jar


**如需混淆请在混淆脚本中加入如下代码。**

* -keepattributes Exceptions,InnerClasses
* -keep class io.rong.imkit.RongIM$GetFriendsProvider {*;}
* -keep class io.rong.imkit.RongIM$GetUserInfoProvider {*;}
* -keep class io.rong.imkit.RongIM$OnReceiveMessageListener {*;}
* -keep class io.rong.imkit.RongIM$OnConversationStartedListener {*;}
* -keep class io.rong.imkit.RongIM$OnConversationListStartedListener {*;}

* -keep class io.rong.imkit.* {*;}
* -keep class io.rong.imkit.common.** {*;}
* -keep class io.rong.imlib.RongIMClient$* {*;}
* -keep class io.rong.imkit.model.** {*;}
* -keep class io.rong.imkit.libs.pinyin.* {*;}

#### 联系我们
商务合作
Email：<bd@rongcloud.cn>

新浪微博 [@融云RongCloud](http://weibo.com/rongcloud)

客服 QQ 2948214065

公众帐号
融云RongCloud RongCloud 公众账号二维码

![Smaller icon](http://www.rongcloud.cn/images/code1.png "RongCloud")