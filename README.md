  # 如何在项目中直接使用xxl-job 
  ## Documentation
  - [中文文档](http://www.xuxueli.com/xxl-job/)
  
  ## quick start (一个 # 代表一级标题，## 代表二级标题)
  ### 1.初始化数据库表
        1. 这些可以直接在文档中得到说明
        2. 直接去github上下载源代码 https://github.com/xuxueli/xxl-job/  解压，执行即可
  ### 2.配置调度中心
        1.在github上下载的原代码中，xxl-job-admin 就是调度中心，本身是用 spring-boot 写的，写好了配置之后，
          直接 java -jar xx.jar 即可运行，成功之后， 即可登录
        2.具体配置：在 xxl-job-admin 的 resources/application.properties 配置文件中，制定好需要链接的数据库
          这个数据库就是1 中创建好的数据库，其他的配置暂时没有配置
        3.maven 打包，上传服务器， 执行 java -jar xxx.jar
        4.启动成功，登录即可

  ### 3.配置执行器
       1.下载的源码 可以直接配置执行
       2.直接在已有的项目中使用
        1）导入依赖
        		<dependency>
        			<groupId>com.xuxueli</groupId>
        			<artifactId>xxl-job-core</artifactId>
        			<version>${project.parent.version}</version>
        		</dependency>
       2）配置自定义的执行器
          1）继承 IJobHandler 抽象类
          2）添加 @Component 注解（交给spring管理）
          3）添加 @JobHandler(value="demoJobHandler") 注解，表示是一个任务管理器
          
       3）配置spring 配置文件
         1）导入属性配置文件 xxl-job-executor-sample-spring 模块中的 xxl-job-executor.properties 属性配置文件
         2）配置 applicationcontext-xxl-job.xml 配置文件 
        
       4）打包 部署到 tomcat下
       
  ### 4.页面操作
       1）首先来到 '执行器管理',将上面写好的 appName （在 3.3 中的 属性文件中）填入，其他随便</br>
       2）执行成功后，就可以看到onLine 地址
       3）然后来到 '任务管理' 新建任务，即可测试
  #### 5.注意
       1）我在同一个ip服务器上开启了3个tomcat（端口分别为: 9001,9002,9003） 跑应用，然后执行器的模式是'轮询'，但是发现当执行到 
         9001,9002 端口的应用时，执行器都是执行失败，只有在9003的时候是成功的，
         （因为只有9003才有 hackerJobHandker,另两个是 demoJobHandker)
        理论上 轮询的方式在 9001,9002一旦检测到无法执行时，应该跑到9003去执行，为什么会直接失败呢？
       因为我这种部署方式不属于集群，要把服务部署到不同ip的服务器上，才属于集群（其实我这个方式也是集群，属于集群应用）
       
       然后，我将 9003 服务关掉，然后在本地起来，（服务器和 本地可以ping通）再去测试，还是出现同样的问题
       
      首先，说一下，同一个ip服务器部署多个 tomcat 服务，也是集群，但是这种集群存在单点故障的问题
      
      而为什么我的服务在 9001 9002 会出现 'job handler [hackerJobHandler] not found.' 的异常，理论上应该是
      检测到 这种情况，他会路由到 9003上去执行，为什么直接出现 'job handler [hackerJobHandler] not found.' 的异常呢？
      
      其实，应该是 9001,9002,9003 这3个tomcat 要部署一样的代码，至少保证3个服务都有 hackerJobHandler ，这样挡在 9001 出现错误
      或者宕机时，他会路由到 9003（9002和9001在一台服务器上，同生同死)
      
      

        