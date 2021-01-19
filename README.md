# jpa-enums-plugin
一个基于jpa的自定义枚举类转换插件，支持自定义枚举和数据库值的转换，以及枚举请求参数的转换

注意：理论上目前spring-data-jpa和mybatis都支持此插件

# 使用示例

插件支持解析请求参数@RequestParam，@PathVariable的自定义枚举类转换，以及@RequestBody参数包含自定义枚举类的转换；

## 1，maven 依赖

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

``` java
<dependency>
    <groupId>com.silencew.plugins</groupId>
    <artifactId>jpa-enums-plugin</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 2，项目配置
springboot的启动类上添加@EnableParamEnumConfig注解;
```java
@SpringBootApplication
@EnableParamEnumConfig
public class DemoApplication {
    // 省略...
}
```
controller方法示例:
``` java
@PostMapping("/test")
public String test(@RequestBody Demo param) {
    return "ok" + param.getGender();
}
@GetMapping("/pt/{gender}")
public String tset(@PathVariable Demo.Gender gender){
    return gender.toString();
}
```
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

    @JsonEnumDeserialze
    @Convert(converter = GenderConvert.class)
    private Gender gender;
    // 省略getter,setter
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
}
```

## 查看最新版本
最新版本链接:
https://gitee.com/wangshuip/mvn-repo/tree/master/com/silencew/plugins/jpa-enums-plugin