<img src="https://raw.githubusercontent.com/ObcbO/click/master/src/images/logo.png" alt="logo" width="100" height="100" align="right" />

# CLICK

这是一款带有 `设置最大值最小值` `改变点击频率概率` 而且还带`GUI`防检测连点器

代码全原创，可能有些繁琐，欢迎在issues给出你的宝贵意见

## 如何使用？(以Windows操作系统为例)

1. 桌面环境可直接启动(双击打开等)
2. 终端输入`java -jar click.jar`来启动此软件
3. 终端使用 启动参数 来启动软件([见下](#启动参数))

## 启动参数

- `-Dmax=`: 最大值 单位:ms 要求: ≠0 >0
- `-Dmin=`: 最小值 单位:ms 要求: ≠0 >0
- `-Dp=`: 改变间隔ms的概率 单位:% 要求:>0 <=100

只有3个参数都输入才运行后就开始连点, 否则就只是填写已输入参数

> 例子: `java -Dmax=20 -Dmin=10 -jar click.jar` 会帮你填写max和min的值
