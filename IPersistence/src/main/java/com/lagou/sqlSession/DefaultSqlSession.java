package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    //查询多条数据
    @Override
    public <E> List<E> selectList(String statementid, Object... params) {

        //将要去完成simpleExecutor里的query调用方法
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        //根据statementid获取mappedStatement的值
        MappedStatement mappedStatement = mappedStatementMap.get(statementid);

        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);


        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) {
        List<Object> objects = selectList(statementid, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw  new RuntimeException("查询结果为空或者返回结果过多");
        }
    }
}
