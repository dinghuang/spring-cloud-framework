package org.dinghuang.demo.application;

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
        codeGenerateUtils.generate(true, "test_order", "order", "订单", "org.dinghuang.demo.application", "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root");
    }
}