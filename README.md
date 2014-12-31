如何配置开发环境
===========================================
1. 安装好JDK1.6以上的版本，设置好JAVA_HOME环境变量
2. 下载activator-1.2.10， 解压，然后把路径添加到path环境变量，如D:\activator-1.2.10
3. 安装好intellij IDEA , 安装好play2 和 scala 插件
4. 到用户目录下，例如：C:\Users\Administrator\.sbt， 下面去创建文件repositories，把下面的内容放到文件里
'''
  [repositories]
    local
    sunlights nexus:http://saturn:8081/nexus/content/groups/public/
    typesafe-ivy-releases: http://repo.typesafe.com/typesafe/releases/, [organization]/[module]/[revision]/[type]s/[artifact](-[classifier]).[ext]
    maven-central
    sonatype-snapshots: https://oss.sonatype.org/content/repositories/snapshots
 '''
5. 安装好git
6. git clone git@EARTH:sunlights/operationPlatform.git
7. 导入项目到intellij IDEA.
8. 在Terminal里运行 activator, 然后运行 ~run来启动应用
9. 打开浏览器访问http://localhost:9000

代码重构
========================================
1. 把现在代码移动到operationPlatform
2. 完善单元测试用例

## 相关的知识
* [UI Router](https://github.com/angular-ui/ui-router/wiki)比原生的router好用，功能更强大
* [Play Framework](https://www.playframework.com)


## 第三方依赖的Web库
* 请先到[WebJars](http://www.webjars.org/)上面去找



