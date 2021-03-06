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
4. 启动sso-client服务，在此服务target目录下执行java -jar sso-client-1.0-SNAPSHOT.jar进行启动，端口号8081
5. 服务启动后访问http://127.0.0.1:8081/test,由于此时未登录，地址重定向到 http://127.0.0.1/sso/loginPage?webapp=http%3A%2F%2F127.0.0.1%3A8080%2Ftest
   页面如下：
   
   ![](https://github.com/chaojunma/file_repository/blob/master/sso-plugn/images/sso-login.png)  

**如何配置**

sso-client服务的application.yml文件如有如下配置
````
sso:
  enable: true #是否使用sso单点登录 true启用 false禁用 
  sso-server: http://127.0.0.1 #单点登录服务器地址
  ignore-urls: #禁止校验的请求路径
````
  
sso-client服务启动类中添加@EnableSso注解（表示应用sso单点登录插件，要配合配置文件中的配置使用），代码如下：
````
@EnableSso
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
````
JFinal技术交流群（不仅限于JFinal）：866797945

JFinal极速开发之从零开始视频教程：

腾讯课堂：https://mming.ke.qq.com/
网易云课堂：https://study.163.com/provider/400000000551018/index.htm
