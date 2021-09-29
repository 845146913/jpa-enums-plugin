# jpa-enums-plugin
一个基于jpa的自定义枚举类转换插件，支持自定义枚举和数据库值的转换，以及枚举请求参数的转换

注意：目前spring-data-jpa和mybatis都支持此插件

+ [使用示例](#使用示例)
    - [maven依赖](#1，maven-依赖-(分注解启动配置版本和starter自动装配版本))
    - [项目配置](#2，项目配置)
    - [使用Demo](#3，使用Demo)
+ [查看最新版本](#查看最新版本)
+ [发布日志](#发布日志)

# 使用示例

插件支持解析请求参数@RequestParam，@PathVariable的自定义枚举类转换，以及@RequestBody参数包含自定义枚举类的转换；

## 1，maven 依赖 (分注解启动配置版本和starter自动装配版本)

pom.xml添加repository
``` java
<repositories>
    <repository>
        <id>silencew-maven</id>
        <url>https://gitee.com/wangshuip/mvn-repo/raw/master</url>
        <releases>
            <updatePolicy>always</updatePolicy>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <updatePolicy>always</updatePolicy>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

引入最新依赖
需要配置启动类(加@EnableParamEnumConfig)版本：
``` java
<dependency>
    <groupId>com.silencew.plugins</groupId>
    <artifactId>jpa-enums-plugin</artifactId>
    <version>0.0.3</version>
</dependency>
```

自动装配版本依赖<font color='red'>（添加完成后不需要任何配置）</font>，请自由选择：
``` java
<dependency>
    <groupId>com.silencew.plugins</groupId>
    <artifactId>silencew-enums-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

## 2，项目配置
PS：自动装配版本不需要配置config;以下为未自动装配配置;
springboot的启动类上添加@EnableParamEnumConfig注解;
```java
@SpringBootApplication
@EnableParamEnumConfig
public class DemoApplication {
    // 省略...
}
```
## 3，使用Demo
controller方法示例:
``` java
    @PostMapping("/test")
    public String test(@RequestBody Demo param) {
        return "ok" + param.toString() + " scope:"+scope;
    }
    @GetMapping("/pt/{gender}")
    public String tset(@PathVariable Demo.Gender gender){
        return gender.toString();
    }
    @GetMapping("/pt")
    public String tset( Demo.WindowType type){
        return type.toString();
    }
    @GetMapping("/pt/w/{type}")
    public String tset1(@PathVariable Demo.WindowType type){
        return type.toString();
    }
    @GetMapping("/pt/d")
    public String tset2(Demo type){
        return type.toString();
    }
```
<font color='red'>PS: 目前版本默认不需要给实体枚举类添加@JsonEnumDeserialize注解</font>

@RequestBody参数实体的枚举类需要添加@JsonEnumDeserialize注解;
``` java
@JsonEnumDeserialze
private Gender gender;
```

数据库和实体类转换示例:
```java
@Entity
@Table
public class Demo {
    private Long id;
    
    private Gender gender;
    // 省略getter,setter

    // 自定义枚举类code=>Enum【如1=>MAN】需要继承BaseEnum基类
    public enum Gender implements BaseEnum<Gender, Integer> {
        MAN(1),
        WOMAN(2);
        private int code;

        Gender(int code) {
            this.code = code;
        }

        @Override
        public Integer getCode() {
            return this.code;
        }
        public class GenderConvert extends BaseEnumConverter<Gender, Integer> {

        }
    }

    // 原始enum支持，0=>LINUX,1=>WINDOWS
    public enum WindowType {
        LINUX(0),
        WINDOWS(1);
        private int code;
        WindowType(int code) {
            this.code = code;
        }
        public int getCode() {
            return this.code;
        }
    }

}
```

# 查看最新版本
最新版本链接:
https://gitee.com/wangshuip/mvn-repo/tree/master/com/silencew/plugins/jpa-enums-plugin

# 发布日志
## 0.0.2
* 修复@RequestBody注解实体解析枚举名NAME时报错
* 同时支持基础enum类和继承BaseEnum基类的枚举类实体的json与实体相互转换
* @RequestBody注解的实体自定义枚举类不需要添加@JsonEnumDeserialze注解
## 0.0.1
* 支持数据库与Entity枚举相互转换
* 支持请求参数@PathVariable，@RequestParam，@RequestBody的自定义枚举类解析转换
* 添加jpa-enums-starter版本