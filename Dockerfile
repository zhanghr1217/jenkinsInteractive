#构建镜像的Dockerfile
# From java image, version : 8
FROM java:8

# 挂载app目录
#VOLUME /app

##将maven构建好的jar添加到镜像中(前面为打包出的jar包名，后面任意与上上步执行shell中一致即可)
ADD target/interactivetools-0.0.2-SNAPSHOT.jar accout-mange-1.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "accout-mange-1.0.jar"]

