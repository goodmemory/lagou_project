package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法就是使用dom4j将配置文件进行解析，封装Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");

        Properties properties=new Properties();

        for (Element element : list) {
            //获取配置文件中name的值
            String name = element.attributeValue("name");
            //获取配置文件中value的值
            String value = element.attributeValue("value");
            //将获取到的值存入properties中
            properties.setProperty(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml解析：拿到路径--字节输入流--使用dom4j进行解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String resources = element.attributeValue("resources");
            InputStream resourceAsstream = Resources.getResourceAsstream(resources);
            //使用dom4j解析mappr.xml方法
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
        }


        return configuration;
    }
}
