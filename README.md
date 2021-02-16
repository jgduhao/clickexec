# clickexec
服务器遥控器，用于替代手机ssh，配置好后直接在网页点执行按钮执行命令

**目前仅能当作内网个人服务遥控器使用，暴露图形界面访问地址等同于暴露root权限密码**

后端基于java8+springboot2.4.2+h2数据库

前端仅是能用的程度，基于bootstrap+jquery

## 版本日志

### 0.1.2 

- 解决了部分脚本会阻塞进程的问题,超时时间50秒(但在执行类似于java -jar xxx.jar命令时,在读取process的InputStream输出内容时依旧可能阻塞,但阻塞的是单个线程,不影响正常使用,将相关的进程kill后线程也可以正常结束)
- 执行命令后的输出可以在日志文件中查看了,日志文件位置/var/clickexec/log

### 0.1.1 

完成基本的新增,修改,执行,删除功能

## 之后可能加入的功能

- 执行日志记录查询
- 脚本筛选查询
- 脚本统一备份管理
- 脚本版本记录
- 脚本文件网页编辑
- 密码权限控制

## 从源码启动

在代码目录下执行（使用root权限）

`./mvnw clean spring-boot:run`

h2数据库文件在/var/clickexec/目录生成

日志文件在目录/var/clickexec/log生成

启动后使用http://127.0.0.1:8081/index.html 访问图形界面

## 打包与启动

代码目录下执行命令

`./mvnw clean package`

在代码target目录下生成jar包

使用root权限启动

`java -jar clickexec-0.1.1.jar`

或使用root权限后台启动

`nohup java -jar clickexec-0.1.1.jar &`

因为需要执行各项命令，所以建议使用root权限启动

## 注册服务

以ubuntu20.04注册systemd服务为例

在目录/etc/systemd/system目录建立文件clickexec.service

```
Description=clickexec
Documentation=
After=network-online.target
Wants=network-online.target

[Service]
Type=simple
User=root
#配置为jar包所在目录
ExecStart=java -jar /home/xyz/clickexec-0.1.1.jar
ExecStop=/bin/kill -s QUIT $MAINPID
Restart=always
StandOutput=syslog

StandError=inherit

[Install]
WantedBy=multi-user.target
```

文件创建保存后刷新systemd

`sudo systemctl daemon-reload`

设置开机自启动

`sudo systemctl enable clickexec`

启动，重启与停止

```
sudo service clickexec start
sudo service clickexec restart
sudo service clickexec stop
```

查看状态

`sudo service clickexec status`





