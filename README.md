
### abina-common-util

####工具类汇总

> 阿里巴巴开发手册推荐部分工具类，有现成的二方类库提供，具体参考
> - json操作：fastjson
> - md5操作：commons-codec
> - 工具集合：Guava包
> - 数组操作：ArrayUtils (org.apache.commons.lang3.ArrayUtils)
> - 集合操作：CollectionUtils (org.apache.commons.collections4.CollectionUtils)
> - NumberUtils, DateFormatUtils, DateUtils 等；不建议使用 org.apache.commons.lang.* 这个版本较低。

**basetype**
- Map参数获取值判断 - paramsUtils
- 字符串操作工具类 - StrUtils 
- 金额加减乘除转换 - CurrencyUtils
- 项目路径操作 - PathUtils
- 中文拼音操作 - PinyinUtils
- java反射工具类 - ReflectionUtils
- 正则验证 - RegexUtils
- 枚举Demo - UsageStatus
- 日期工具类 - DateUtils 
- list操作 - ListUtils

**generate**
- 生成二维码工具类 - GenerateQRcode

**http**
- 操作Cookie - CookieUtils
- request请求操作 - RequestUtils

**security**
- 密码加密工具类 - PasswordUtils

**webRoot**
- js插件集合文件夹 - plug-in 
- 省市区三级下拉 - plug-in/distpicker.js
- 实时时间demo - plug-in/timer
