package org.dinghuang.core.utils;


import freemarker.template.Template;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.core.model.ColumnClass;
import org.dinghuang.core.mybatis.model.enums.BaseModelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 自动生成代码
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/20
 */
public class CodeGeneratorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGeneratorUtils.class);

    private static final String AUTHOR = "dinghuang123@gmail.com";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    private static final String DO_PATH = "infra/dataobject/";
    private static final String REPOSITORY_PATH = "infra/repository/";
    private static final String ENUM_PATH = DO_PATH + "enums/";
    private static final String DTO_PATH = "dto/";
    private static final String MAPPER_PATH = "mapper/";
    private static final String SERVICE_PATH = "service/";
    private static final String CONTROLLER_PATH = "controller/";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";
    private static final String COLUMN_KEY = "COLUMN_KEY";
    private static final String PRI = "PRI";
    private Boolean cover = false;
    private String priMaryName = "Id";
    private String tableName;

    public static void main(String[] args) throws Exception {
        CodeGeneratorUtils codeGenerateUtils = new CodeGeneratorUtils();
        codeGenerateUtils.generate(true, "test_order", "order", "订单", "org.dinghuang.demo.application", "jdbc:mysql://127.0.0.1:3306/sale_customer_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root");
    }

    /**
     * 自动生成代码
     * <p>
     * 例如(true, "cus_potential_customer", "potential_customer", "潜在客户", "com.crland.sale.customer", "jdbc:mysql://127.0.0.1:3306/sale_customer_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root")
     *
     * @param cover            是否覆盖
     * @param tableName        表名
     * @param moduleName       模块名
     * @param tableDescription 表描述
     * @param path             项目相对路径
     * @param url              数据库url
     * @param user             数据库用户名
     * @param password         数据库密码
     */
    public void generate(Boolean cover, String tableName, String moduleName, String tableDescription, String path, String url, String user, String password) throws SQLException {
        this.tableName = tableName;
        this.cover = cover;
        try {
            String filePath = System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", "/") + "/";
            List<ColumnClass> columnClassList = new ArrayList<>();
            ColumnClass columnClass;
            List<Map<String, Object>> list = getColumnData(url, user, password);
            assert list != null;
            for (Map<String, Object> map : list) {
                if (map.get(COLUMN_NAME).equals(BaseModelEnum.CREATED_BY.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.CREATED_BY_NAME.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.CREATION_DATE.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.OBJECT_VERSION_NUMBER.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.LAST_UPDATED_BY.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.LAST_UPDATED_BY_NAME.value())
                        || map.get(COLUMN_NAME).equals(BaseModelEnum.LAST_UPDATED_DATE.value())
                        ) {
                    continue;
                }
                columnClass = new ColumnClass();
                //获取字段名称
                columnClass.setColumnName(map.get(COLUMN_NAME).toString());
                //获取字段类型
                columnClass.setColumnType(map.get(DATA_TYPE).toString().toUpperCase());
                if ("TINYINT".equals(columnClass.getColumnType())) {
                    //转换字段名称，如 sys_name 变成 SysName
                    String name = map.get(COLUMN_NAME).toString().replace("is_", "").replace("is", "");
                    columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(name));
                    //全部大写
                    columnClass.setChangeColumnNameUp(name.toUpperCase());
                } else {
                    //转换字段名称，如 sys_name 变成 SysName
                    columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(map.get(COLUMN_NAME).toString()));
                    //全部大写
                    columnClass.setChangeColumnNameUp(map.get(COLUMN_NAME).toString().toUpperCase());
                }
                //字段在数据库的注释
                columnClass.setColumnComment(map.get(COLUMN_COMMENT).toString());
                //是否必填
                columnClass.setIsNullAble("NO".equals(map.get(IS_NULLABLE)));
                //字段长度
                columnClass.setColumnLength(map.get(CHARACTER_MAXIMUM_LENGTH) == null ? null : map.get(CHARACTER_MAXIMUM_LENGTH).toString());
                //是否主键
                if (map.get(COLUMN_KEY).equals(PRI)) {
                    this.priMaryName = replaceUnderLineAndUpperCase(map.get(COLUMN_NAME).toString());
                    columnClass.setIsPrimary(true);
                } else {
                    columnClass.setIsPrimary(false);
                }
                columnClassList.add(columnClass);
            }
            Map<String, Object> dataMap = new HashMap<>(1);
            dataMap.put("model_column", columnClassList);
            //生成DO文件
            generateDOFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成Repository文件
            generateRepositoryFile(moduleName, path, filePath, tableDescription);
            //生成Enums文件
            generateEnumsFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成DTO文件
            generateDTOFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成createDTO文件
            generateCreateDTOFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成updateDTO文件
            generateUpdateDTOFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成createOrUpdateDTO文件
            generateCreateOrUpdateDTOFile(dataMap, moduleName, path, filePath, tableDescription);
            //生成Mapper文件
            generateMapperFile(moduleName, path, filePath, tableDescription);
            //生成服务实现层文件
            generateServiceFile(moduleName, path, filePath, tableDescription);
            //生成Controller层文件
            generateControllerFile(moduleName, path, filePath, tableDescription);
            //生成Mapper.xml文件
            generateXmlMapperFile(moduleName, path, tableDescription);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateXmlMapperFile(String moduleName, String path, String tableDescription) throws Exception {
        final String suffix = "Mapper.xml";
        String filePath = System.getProperty("user.dir") + "/src/main/resources/mapper/";
        final String pathUrl = filePath + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "XMLMapper.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateCreateOrUpdateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "CreateOrUpdateDTO.java";
        final String pathUrl = filePath + DTO_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "CreateOrUpdateDTO.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private List<Map<String, Object>> getColumnData(String url, String user, String password) throws SQLException {
        Connection conn = null;
        String sql = "select * from information_schema.columns where TABLE_NAME='" + this.tableName + "'";
        PreparedStatement stmt = null;
        try {
            conn = getConnection(url, user, password);
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            return convertList(rs);
        } catch (Exception e) {
            LOGGER.error("数据库连接失败");
            throw new CommonException("数据库连接失败", e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    private Connection getConnection(String url, String user, String passworg) throws Exception {
        Class.forName(DRIVER);
        return DriverManager.getConnection(url, user, passworg);
    }

    private void generateControllerFile(String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Controller.java";
        final String pathUrl = filePath + CONTROLLER_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateServiceFile(String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Service.java";
        final String pathUrl = filePath + SERVICE_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "Service.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateMapperFile(String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Mapper.java";
        final String pathUrl = filePath + MAPPER_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateUpdateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "UpdateDTO.java";
        final String pathUrl = filePath + DTO_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "UpdateDTO.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateCreateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "CreateDTO.java";
        final String pathUrl = filePath + DTO_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "CreateDTO.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String description) throws Exception {
        final String suffix = "DTO.java";
        final String pathUrl = filePath + DTO_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "DTO.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, description);
    }

    private void generateEnumsFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Enum.java";
        final String pathUrl = filePath + ENUM_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "Enum.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateRepositoryFile(String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Repository.java";
        final String pathUrl = filePath + REPOSITORY_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "Repository.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateDOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "DO.java";
        final String pathUrl = filePath + DO_PATH + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "DO.ftl";
        File mapperFile = new File(pathUrl);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }


    private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        //获取行的数量
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            //声明Map
            Map<String, Object> rowData = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                //获取键名及值
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    private void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap, String moduleName, String path, String tableDescription) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(new Date());
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        if (cover) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name_small", moduleName);
        dataMap.put("table_name_full", this.tableName);
        dataMap.put("table_name", replaceUnderLineAndUpperCase(moduleName));
        dataMap.put("table_name_first_low", toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName)));
        dataMap.put("author", AUTHOR);
        dataMap.put("priMaryName", priMaryName);
        dataMap.put("date", date);
        dataMap.put("package_name", path);
        dataMap.put("table_annotation", tableDescription);
        dataMap.put("do_path", DO_PATH.replace("/", "."));
        dataMap.put("repository_path", REPOSITORY_PATH.replace("/", "."));
        dataMap.put("enum_path", ENUM_PATH.replace("/", "."));
        dataMap.put("dto_path", DTO_PATH.replace("/", "."));
        dataMap.put("mapper_path", MAPPER_PATH.replace("/", "."));
        dataMap.put("service_path", SERVICE_PATH.replace("/", "."));
        dataMap.put("controller_path", CONTROLLER_PATH.replace("/", "."));
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        template.process(dataMap, out);
    }

    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    private String replaceUnderLineAndUpperCase(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return org.apache.commons.lang3.StringUtils.capitalize(result);
    }


}
