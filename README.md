demo-app-android
================

App for demonstration of RongIMKit component.

运行条件：
AndroidStudio 0.6+
Gradle 1.11+
RongIMSDK 0.9.5
在DemoContext中init方法中填入申请的APP_Key测试

注意事项：
imkit.jar中已经集成如下开源库为防重复引用特此提示
com.sea_monster:core_jar:0.0.19
com.github.chrisbanes.bitmapcache:library:2.3@jar
com.jakewharton:disklrucache:2.0.2@jar

如需混淆请在混淆脚本中加入如下代码。
-keepattributes Exceptions,InnerClasses
-keep class io.rong.imkit.RongIM$GetFriendsProvider {*;}
-keep class io.rong.imkit.RongIM$GetUserInfoProvider {*;}
-keep class io.rong.imkit.RongIM$OnReceiveMessageListener {*;}
-keep class io.rong.imkit.RongIM$OnConversationStartedListener {*;}
-keep class io.rong.imkit.RongIM$OnConversationListStartedListener {*;}

-keep class io.rong.imkit.* {*;}
-keep class io.rong.imkit.common.** {*;}
-keep class io.rong.imlib.RongIMClient$* {*;}
-keep class io.rong.imkit.model.** {*;}
-keep class io.rong.imkit.libs.pinyin.* {*;}