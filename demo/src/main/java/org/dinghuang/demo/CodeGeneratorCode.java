package org.dinghuang.demo;

import org.dinghuang.core.utils.CodeGeneratorUtils;

/**
 * 代码自动生成器
 *
 * @author dinghuang123@gmail.com
 * @since 2019/03/21
 */
public class CodeGeneratorCode {

    public static void main(String[] args) throws Exception {
        CodeGeneratorUtils codeGenerateUtils = new CodeGeneratorUtils();
//        codeGenerateUtils.generate("/Users/dinghuang/Documents/workSpace/huarun/spring-cloud-framework/demo",true, "com_esb_log", "esb_log", "esb日志表", "org.dinghuang.demo", "jdbc:mysql://127.0.0.1:3306/sale_common_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root");
        codeGenerateUtils.generate("/Users/dinghuang/Documents/workSpace/huarun/spring-cloud-framework/demo",true, "test", "test", "测试对象", "org.dinghuang.demo", "jdbc:oracle:thin:@127.0.0.1:1521:helowin", "root", "root");
    }
}