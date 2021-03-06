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
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
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
    private static final String ORACLE = "jdbc:oracle:thin";
    private static final String POSTGRESSQL = "jdbc:postgresql:";
    private static final String DTO = ".dto.";
    private Boolean cover = false;
    private String priMaryName = "Id";
    private String tableName;

    /**
     * 自动生成代码
     * <p>
     * 例如(null,true, "aaa_asd", "asd", "哈哈", "com.dinghuang.sale.customer", "jdbc:mysql://127.0.0.1:3306/adas?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root")
     *
     * @param rootPath         根路径
     * @param cover            是否覆盖
     * @param tableName        表名
     * @param moduleName       模块名
     * @param tableDescription 表描述
     * @param path             项目相对路径
     * @param url              数据库url
     * @param user             数据库用户名
     * @param password         数据库密码
     */
    public void generate(String rootPath, Boolean cover, String tableName, String moduleName, String tableDescription, String path, String url, String user, String password) {
        this.tableName = tableName;
        this.cover = cover;
        try {
            rootPath = rootPath != null ? rootPath : System.getProperty("user.dir");
            String filePath = rootPath + "/src/main/java/" + path.replace(".", "/") + "/";
            List<ColumnClass> columnClassList = new ArrayList<>();
            ColumnClass columnClass;
            List<Map<String, Object>> list = getColumnData(url, user, password);
            assert list != null;
            for (Map<String, Object> map : list) {
                if (map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.CREATED_BY.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.CREATED_BY_NAME.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.CREATION_DATE.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.OBJECT_VERSION_NUMBER.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.LAST_UPDATED_BY.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.LAST_UPDATED_BY_NAME.value())
                        || map.get(COLUMN_NAME).toString().equalsIgnoreCase(BaseModelEnum.LAST_UPDATED_DATE.value())
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
                columnClass.setColumnComment(map.get(COLUMN_COMMENT) == null ? null : map.get(COLUMN_COMMENT).toString());
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
            generateXmlMapperFile(moduleName, path, rootPath, tableDescription);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateXmlMapperFile(String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "Mapper.xml";
        filePath = filePath + File.separator + "/src/main/resources/mapper/";
        final String pathUrl = filePath + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "XMLMapper.ftl";
        File mapperFile = new File(pathUrl);
        Map<String, Object> dataMap = new HashMap<>(0);
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateCreateOrUpdateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "CreateOrUpdateDTO.java";
        final String pathUrl = filePath + DTO_PATH + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName) + "/") + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "CreateOrUpdateDTO.ftl";
        File mapperFile = new File(pathUrl);
        path = path + DTO + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName));
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private List<Map<String, Object>> getColumnData(String url, String user, String password) throws SQLException {
        Connection conn = null;
        String sql;
        Boolean preparedStatement = true;
        if (url.contains(ORACLE)) {
            sql = "SELECT utc.column_name AS COLUMN_NAME,utc.data_type AS DATA_TYPE,utc.data_length AS CHARACTER_MAXIMUM_LENGTH,CASE " +
                    "utc.nullable WHEN 'N' THEN 'NO' ELSE 'YES' END AS IS_NULLABLE,ucc.comments AS COLUMN_COMMENT,CASE utc.column_name " +
                    "WHEN (SELECT col.column_name FROM user_constraints con,user_cons_columns col WHERE con.constraint_name = " +
                    "col.constraint_name AND con.constraint_type = 'P' AND col.table_name = '" + this.tableName.toUpperCase() + "') THEN 'PRI' ELSE 'OTHER' END AS COLUMN_KEY " +
                    "FROM user_tab_columns utc,user_col_comments ucc WHERE utc.table_name = '" + this.tableName.toUpperCase() + "' AND utc.table_name = ucc.table_name " +
                    "AND utc.column_name = ucc.column_name ORDER BY utc.column_id";
        } else if (url.contains(POSTGRESSQL)) {
            sql = "SELECT COLUMN_NAME , data_type AS DATA_TYPE, COALESCE ( character_maximum_length, numeric_precision,- 1 ) " +
                    "AS CHARACTER_MAXIMUM_LENGTH, is_nullable,CASE    WHEN b.pk_name IS NULL THEN  'OTHER' ELSE'PRI'  END AS COLUMN_KEY," +
                    " C.DeText AS COLUMN_COMMENT FROM information_schema. COLUMNS LEFT JOIN ( SELECT  pg_attr.attname AS colname, " +
                    " pg_constraint.conname AS pk_name  FROM  pg_constraint  INNER JOIN pg_class ON pg_constraint.conrelid = pg_class.oid" +
                    "  INNER JOIN pg_attribute pg_attr ON pg_attr.attrelid = pg_class.oid   AND pg_attr.attnum = pg_constraint.conkey [ 1 ]" +
                    "  INNER JOIN pg_type ON pg_type.oid = pg_attr.atttypid  WHERE  pg_class.relname = '" + this.tableName + "'   AND pg_constraint.contype = 'p'  ) " +
                    "b ON b.colname = information_schema.COLUMNS. COLUMN_NAME LEFT JOIN ( SELECT  attname,  description AS DeText  FROM  pg_class  " +
                    "LEFT JOIN pg_attribute pg_attr ON pg_attr.attrelid = pg_class.oid  LEFT JOIN pg_description pg_desc ON pg_desc.objoid = pg_attr.attrelid  " +
                    " AND pg_desc.objsubid = pg_attr.attnum  WHERE  pg_attr.attnum > 0   AND pg_attr.attrelid = pg_class.oid   AND pg_class.relname = '" + this.tableName + "'  ) " +
                    "C ON C.attname = information_schema.COLUMNS.COLUMN_NAME WHERE table_schema = 'public'  AND TABLE_NAME = '" + this.tableName + "' ORDER BY ordinal_position ASC";
            preparedStatement = false;
        } else {
            sql = "select * from information_schema.columns where TABLE_NAME='" + this.tableName + "'";
        }
        if (preparedStatement) {
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
        } else {
            Statement stmt = null;
            try {
                conn = getConnection(url, user, password);
                stmt = conn.createStatement();
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

    }

    private Connection getConnection(String url, String user, String password) throws Exception {
        if (url.contains(ORACLE)) {
            Class.forName(ORACLE_DRIVER);
        } else if (url.contains(POSTGRESSQL)) {
            Class.forName(POSTGRESQL_DRIVER);
        } else {
            Class.forName(MYSQL_DRIVER);
        }
        return DriverManager.getConnection(url, user, password);
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
        final String pathUrl = filePath + DTO_PATH + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName) + "/") + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "UpdateDTO.ftl";
        File mapperFile = new File(pathUrl);
        path = path + DTO + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName));
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateCreateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String tableDescription) throws Exception {
        final String suffix = "CreateDTO.java";
        final String pathUrl = filePath + DTO_PATH + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName) + "/") + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "CreateDTO.ftl";
        File mapperFile = new File(pathUrl);
        path = path + DTO + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName));
        generateFileByTemplate(templateName, mapperFile, dataMap, moduleName, path, tableDescription);
    }

    private void generateDTOFile(Map<String, Object> dataMap, String moduleName, String path, String filePath, String description) throws Exception {
        final String suffix = "DTO.java";
        final String pathUrl = filePath + DTO_PATH + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName) + "/") + replaceUnderLineAndUpperCase(moduleName) + suffix;
        final String templateName = "DTO.ftl";
        File mapperFile = new File(pathUrl);
        path = path + DTO + toLowerCaseFirstOne(replaceUnderLineAndUpperCase(moduleName));
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
                Object value = null;
                if (rs.getObject(i) instanceof String) {
                    value = ((String) rs.getObject(i)).toUpperCase();
                }
                rowData.put(md.getColumnName(i).toUpperCase(), value);
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
        if (cover || !file.exists()) {
            file.createNewFile();
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
    }

    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    private String replaceUnderLineAndUpperCase(String str) {
        str = str.toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        int count = sb.indexOf("_");
        while (count != 0) {
            int num = sb.indexOf("_", count);
            count = num + 1;
            if (num != -1) {
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                if (Character.isDigit(ss)) {
                    ia = ss;
                }
                sb.replace(count, count + 1, ia + "");
            }
        }
        String result = sb.toString().replaceAll("_", "");
        return org.apache.commons.lang3.StringUtils.capitalize(result);
    }


}
