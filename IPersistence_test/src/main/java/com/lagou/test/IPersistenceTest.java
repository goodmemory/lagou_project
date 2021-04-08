package com.lagou.test;

import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class IPersistenceTest {

    public void test() throws PropertyVetoException, DocumentException {
        InputStream resourceAsstream = Resources.getResourceAsstream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(resourceAsstream);

        SqlSession sqlSession = sqlSessionFactory.openSqlSession();

        User user = new User();
        user.setId(1);
        user.setUsername("张三");

        User user1 = sqlSession.selectOne("user.selectOne", user);
    }
}
