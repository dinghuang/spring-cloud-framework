package org.dinghuang.core.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 类路径不同JAR中多个同一名称的资源加载工具
 *
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
public class MultiResourcesUtils {

    private MultiResourcesUtils() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiResourcesUtils.class);

    public static List<String> load(String resourceName) {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            Enumeration<URL> ps = Thread.currentThread().getContextClassLoader().getResources(resourceName);
            List<String> urls = new ArrayList<>();
            while (ps.hasMoreElements()) {
                URL url = ps.nextElement();
                urls.add(url.toString());
                inputStreamReader = new InputStreamReader(url.openStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    if (StringUtils.isBlank(line)) {
                        continue;
                    }
                    if (line.startsWith("#") || line.startsWith("//")) {
                        continue;
                    }
                    list.add(line);
                }
            }
            LOGGER.info("加载资源: {}", urls);
            LOGGER.info("成功加载的资源行数: {}", list.size());
        } catch (Exception e) {
            LOGGER.error("加载资源出错", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.error("关闭资源出错", e);
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    LOGGER.error("关闭资源出错", e);
                }
            }
        }
        return list;
    }
}
