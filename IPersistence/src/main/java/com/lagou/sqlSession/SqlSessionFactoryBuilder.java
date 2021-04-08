package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(InputStream inputStream) throws DocumentException, PropertyVetoException {

        //1.使用dom4j解析配置文件，将解析出来的对象封装到configuration
        XMLConfigBuilder xmlConfiguration = new XMLConfigBuilder();
        Configuration configuration = xmlConfiguration.parseConfig(inputStream);

        //2.创建sqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);


        return null;
    }
}
