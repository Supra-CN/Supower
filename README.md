# Supower
** Supra powered framework lib for android; **

** 超能力开发开源框架； **

**Supower** Android 应用开发框架，提供了对**AppController**的抽象；实现了对应用级别单例Controller的维护和管理；在此基础上框架提供了**ModelManager**和**NetworkCenter**这两个**AppController**框架；除此之外使用者还可以继承"AppController"实现自己的Controller；


* `Supra` - **Supower**框架的单例实现；提供了获取`AppController`单例对象的静态方法，使用前需要在`Application`的`onCreate()`方法中调用`Supra.init(Context c)`来初始化框架
* `AppController` - 由`Supra`维护的单例全局控制器，实现了`ComponentCallbacks2`接口的虚基类，可以继承`AppController`来实现自己的全局。可以使用`Supra.getController(Class<T> clazz)`来取得控制器实例，例如`SimpleController controller = Supra.getController(SimpleController.class);'
* `ModelManager` - 一个负责维护模型对象(`ModelObj`)的`AppController`,使用对象标识符`ObjIdentifiter`来定位模型对象(`ModelObj`)，自动维护模型对象的构造和销毁，避免频繁GC操作，实现全局模型对象的缓存。取得`ModelManager`实例：`Supra.getContorller(ModelManager.class);`或`Supra.getModelManager()`或｀ModelManager.getInstance()｀;
    * `ObjIdentifiter` - `ModelObj`对象的标识符虚基类；是对URI的封装，需要在子类中实现对象URI的定义和构造方法
    * `ModelObj`  - 可被`ModelManager`模型对象虚基类
    * `PersistableObj` - 可持久化的模型对象虚基类，需要用户实现填充数据(`onReset()`)和写入数据(`onFlush()`)的方法
* `NetworkCenter` - 对网络请求的封装，使用将一个请求抽象成请求任务(`HttpTask`)来处理，除此之外还提供了两个Volley的任务队列，一个用于普通请求任务，一个用于图片的加载任务
    * `HttpTask` - 是对请求任务的抽象封装，包括请求信息(`HttpTaskInfo`)、请求结果处理(`HttpResult`)、请求回调(`TaskCallBack`)和代理(`HttpDelegate`)等信息
    * `HttpTaskInfo` - 在请求信息中实现某个请求的方法和具体参数等信息
    * `HttpResult` - 在请求结果处理中异步处理请求到的内容
    * `TaskCallBack` - 请求状态回调，包括请求开始、进度变化和请求结束这三种事件
    * `HttpDelegate` - 请求操作代理，可以根据不同的网络框架来实现对应的HttpDelegate，代理请求任务，如`VolleyDelegateRequest`就是使用Volley框架来代理网络任务的具体实现；


## usage
### 初始化**Supower**框架
```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Supower框架
        Supra.init(this);
    }
}
```

### 取得一个**AppController**的单例对象,如`ModelManager`
```
// 获得ModelManager实例；
ModelManager modelManager = Supra.getControler(ModelManager.class);
// 构造用户标识符；
UserIdentifier uIdentifier = new UserIdentifier(userId);
// 获得用户实例；
User user = modelManager.getObj(uIdentifier);
```

# Developed By
* Supra Wang - <supra.cn@gmail.com>


# License

    Copyright 2015 Supra Wang
    Copyright (C) 2011 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
