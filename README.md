# spring-boot-rte-study

Rich Text Editor study

todo :  
1.markdown和html各保存了一份。不知道有没有更优雅的办法，譬如后台只存html,edit时，可以自动把html转换成markdown。   
2.jpa生成的表结构，也domain对象中定义的顺序不一致，影响易用性（select看数据时）。目前有个折衷的办法来解决此问题，就是手工创建table。这样不爽，但不知道有没有优雅的办法来解决此问题。    
3.根据手工创建的表，自动生成domain对象。目前idea在spring boot环境下不支持。不知道有没有比较好插件来解决这个问题。效率嘛能提高一点就提高一点嘛


Spring Security配合使用时，出现以下报错
>image-dialog.js:158 Uncaught DOMException: Blocked a frame with origin "http://localhost:8080" from accessing a cross-origin frame.
     at HTMLIFrameElement.uploadIframe.onload (http://localhost:8080/editormd/plugins/image-dialog/image-dialog.js:158:129)
 uploadIframe.onload @ image-dialog.js:158

解决办法：
```java
 http.headers().frameOptions().disable();	
```


> 参考了 https://github.com/wchstrife/blog.git 
