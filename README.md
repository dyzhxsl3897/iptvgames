# iptvgames
## 代码规范
* 整个工程，甚至整个Workspace，统一使用UTF-8编码
* 包名全部小写：com.yunyouhudong.games.{gamename}.xxx
* 工程名字全部小写，尽量使用一到两个单词表明游戏，游戏名字同理
* 常量大写加下划线
* 类名首字母大写，驼峰式命名规则，方法名，变量名首字母小写，帕斯卡命名规则
* 可以使用拼音，但是不要拼音和英文混写
* 所有// TODO全部删掉，除非确实有需要将来改进的地方
* 所有项目没有用到的图片，音乐等资源文件全部从项目删除，不要出现在最终jar包里
* 图片音乐资源文件目录不易过深，最多不超过2层
* 最终工程里Errors和Warnings都不能有，如果有Warnings根据eclipse提示修改
* 创建自己的MIDlet使用eclipse的New Wizard创建J2ME MIDlet。不要通过New Class来创建
* 最终工程不要留下任何System.out.println()，也不要通过注释保留。如果想保留日志，使用Log.info(...),Log.debug(...),Log.error(...)这些函数来输出日志
* 不要用Sprite创建Sprite。比如：Sprite s = new Sprite(anotherSprite);
* 可以添加注释，但不要注释代码，注释掉的代码全部删除。如果必须保留注释代码作为参考，使用下面的格式：
  * 首先在没有注释的情况下，格式化代码，保留所有缩进
  * 在首行单独使用：      /*- 代码注释目的
  * 最后一行使用这个结束： */
## 逻辑规范
* 所有GameCanvas构造函数里不要加载图片，音乐等任何资源，不要初始化游戏，不要运行线程。在需要的时候再加载图片，开启线程，比如进入到canvas以后
* 退出GameCanvas尽量清空所有已加载图片，音乐等所有引用的资源，并中断线程
* 每一帧固定80毫秒
* 每次在run方法里最后调用一次layerManager.paint(...)和this.flushGraphics()，其余地方不要调用，除非特殊情况，比如确实有明显动作延迟之类情况
* 如果没有动画效果，不推荐implements Runnable和创建线程
* 永远只使用KeyPressed(...)一个函数来做按键检测，不考虑持续按键的情况，因为遥控器不支持，都是单独发送红外按键事件
