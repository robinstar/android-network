# 监听Log
> adb logcat -s CallbackActivity
> adb logcat -s MessageActivity

# 启动Activity
演示Activity使用Callback造成的内存泄漏问题
- Activity实现一个Callback
- 匿名类Callback
- 非静态内部类Callback
- 静态类Callback持有Activity的引用

```
> adb shell am start -n com.example.network/com.example.network.leaked.CallbackActivity
> adb shell am start -n com.example.network/com.example.network.leaked.CallbackActivity --ez leak true
```

演示Activity使用Handler造成的内存泄漏问题
- 匿名类Handler
- 非静态内部类Handler
- 静态类Handler持有Activity的引用

```
> adb shell am start -n com.example.network/com.example.network.leaked.MessageActivity
> adb shell am start -n com.example.network/com.example.network.leaked.MessageActivity --ez leak true
```

# 检查内存泄漏问题
退出观测Activity后，在AndroidStudio的 `Android Profiler` 工具中选择Memory，触发GC，之后dump堆内存快照，可以检查是否有Activity泄漏的问题。