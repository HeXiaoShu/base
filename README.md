# 快速开发脚手架

- 整合TKmapper 通用service
- swagger3 文档
- 常用工具类

#### 注意事项：

- 线程池

  配置类：

  ```
  AsyncConfig
  ```

  应用：

  ```java
  //异步，无返回值
  @Async("cpuDefaultThreadPool")
  void asyncTask(Integer param);
  ```

  ```java
  //异步，返回值
  @Async("cpuDefaultThreadPool")
  CompletableFuture<Object> asyncTask(Integer param);
  
  @Override
  public CompletableFuture<Object> asyncTask(Integer param) {
      return CompletableFuture.completedFuture(param+1);
  }
  
  //demo.get(); 取值阻塞
  
  //多任务并发
  @Override
  public Object taskDemo() {
      Object o=null;
      System.out.println("开始执行任务");
      System.out.println("3次任务的执行时间一样，并发执行的！");
      CompletableFuture<Object> task1 = asyncService.asyncTask(1);
      CompletableFuture<Object> task2 = asyncService.asyncTask(2);
      CompletableFuture<Object> task3 = asyncService.asyncTask(3);
      try {
          o=CompletableFuture.allOf(task1, task2, task3).get();
      } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
      }
      return o;
  }
  ```

- 事件监听器

  `com.listener`

  > 应用场景：以增量的设计方式去应对这些变化多端的需求；
  >
  > 如：话费充值后，可能有的附加操作；充值话费后，发送短信，添加积分

  事件发布：

  ```java
  @Service("someService")
  public class SomeServiceImpl implements ISomeService {
      @Resource
      ApplicationContext applicationContext;
  
      @Override
      public void toDo(Object source,Long id,Object param) {
          applicationContext.publishEvent(new SomeEvent(source,id,param));
      }
  }
  ```

- swagger配置

  `SwaggerConfig`

  > 建议，要业务要求做接口文档分组.

- 参数校检

  ```java
      @NotNull(message = "id不能为空",groups = ValidGroupEdit.class)
      private Long id;
  
      @NotNull(message = "手机号不能为空",groups = ValidGroupInsert.class)
      private String phone;
  ```

  

  ```java
      @GetMapping("/search")
      public Object get(@Validated(ValidGroupSearch.class) User user){
          return user;
      }
  
      @PostMapping("/add")
      public Object add(@Validated(ValidGroupInsert.class) 
                        @RequestBody User user){
          return user;
      }
  ```

  



