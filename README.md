# Getting Started


### `/room` Room API
#### `/add` add new one
#### `/update` update one by its id
#### `/delete` delete one by its id
#### `/find_list` find a list by limit
#### `/find_one` find one by its id
#### `/find_all` find all

### `/ai` Artificial Intelligence API

#### `/tagging` AI Recognize Stuff

##### Request

Use Huawei Cloud & Tencent Cloud API to recognize and detect what is exactly hidden in the picture, the request is
a `JSON` file, the structure `ImageInfo` is below:

```java
//图片的公网url，需要图床或CDN
private String imgUrl;
//是否需要返回坐标：true或false，同时也是2种不同AI接口的区分属性
private String needDet;
//i18n设置：zh或en，默认全部
private String language;
//置信度设置，越大越苛刻，建议：1.物品检测：20, 2.家具检测：30
private String threshold;
//物体检测数量限制，建议：1.物品检测：15个, 2.家具检测：5个
private Integer limit;
//AI厂商名：huawei, tencent, alibaba, deepl, google, azure, aws
private String serverName;
//SecretKey
private SecretKey secretKey;
```

the `SecretKey` within is subject to enable 3rd Party API Serve:

```java
//AK/SI
private String ak;
//SK
private String sk;
```

##### Return

and when return the raw data from AI servers, data will be secondly processed into an Object called `TagResult`.
And it data structure is:

```java
//Type
private String type;
//Tag
private String tag;
//物体坐标位置类
private BoundingBox boundingBox;
```

the `BoundingBox` within is subject to record stuff position:

```java
//高度
private Integer height;
//左上角x距离
private Integer topLeftX;
//左上角y距离
private Integer topLeftY;
//宽度
private Integer width;
```

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.13/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.13/maven-plugin/reference/html/#build-image)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#data.nosql.redis)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#web)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#io.email)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#data.nosql.mongodb)

### Guides

The following guides illustrate how to use some features concretely:

* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

