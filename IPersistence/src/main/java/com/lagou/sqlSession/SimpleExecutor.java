package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;
import com.lagou.utils.TokenHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        //1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        
        //2.获取sql语句 ：select * from user where id=${id} and username=#{username}
        //转换成：select * from user where id=？ and username=？转换过程中还需要对#{}里面的值进行解析
        String sql = mappedStatement.getSql();
        BoundSql boundSql= getBoundSql(sql);

        //3.获取预处理对象preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数

        //5.执行sql

        //6.封装返回结果集

        return null;
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替；2.解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        //1.1标记处理类：配合标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}",parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;
    }
}
