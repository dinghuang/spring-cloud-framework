# 示例应用
把本地maven的``setting.xml``加入下面的配置
```
<server>
    <id>huarun</id>
    <username>admin</username>
    <password>admin123</password>
</server>


<mirror>
    <id>huarun</id>
    <mirrorOf>*</mirrorOf>
    <name>huarun maven</name>
    <url>http://nexuss.strongsickcat.com:8080/repository/nexus-public/</url>
</mirror>


<profile>
    <id>huarun</id>
    <repositories>
        <repository>
        <id>central</id>
        <url>http://central</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
        <id>central</id>
        <url>http://central</url>
        <releases><enabled>true</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
        </pluginRepository>
    </pluginRepositories>
</profile>
```