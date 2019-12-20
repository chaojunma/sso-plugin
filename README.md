# sso-plugin
springboot集成单点登录插件

**应用如下**
+ sso-sdk(单点登录插件，sso-server和sso-client需要引用此依赖)
+ sso-server(单点登录服务器)
+ sso-client(单点登录客户端)

**如何使用**
1. 使用git clone命令将此上面三个服务下载到本地
2. 切换到sso-sdk目录下执行 mvn clean install命令将此服务发布到本地仓库
3. 启动sso-server服务，在此服务target目录下执行java -jar sso-server.jar进行启动，端口号80
4. 启动sso-client服务，在此服务target目录下执行java -jar sso-client-1.0-SNAPSHOT.jar进行启动，端口号8080
5. 服务启动后访问localhost:8080/test,由于此时未登录，地址重定向到 http://127.0.0.1/sso/loginPage?webapp=http%3A%2F%2Flocalhost%3A8080%2Ftest
   页面如下：
   
   ![](https://github.com/chaojunma/file_repository/tree/master/sso-plugin/images/sso-login.png)  

