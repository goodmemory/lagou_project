package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    //查询所有数据
    public <E> List<E> selectList(String statementid,Object... params);

    //根据条件查询单个
    public <T> T selectOne(String statementid,Object... params);
}
