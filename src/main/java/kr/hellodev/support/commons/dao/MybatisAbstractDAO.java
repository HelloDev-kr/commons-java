package kr.hellodev.support.commons.dao;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 01/11/2018 4:18 PM
 */
public class MybatisAbstractDAO extends SqlSessionDaoSupport {

    @Resource(name = "sqlSessionFactory")
    public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Resource(name = "sqlSessionTemplate")
    public void setSuperSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public int create(String queryId, Object parameterObject) {
        return getSqlSession().insert(queryId, parameterObject);
    }

    public int update(String queryId, Object parameterObject) {
        return getSqlSession().update(queryId, parameterObject);
    }

    public int delete(String queryId, Object parameterObject) {
        return getSqlSession().delete(queryId, parameterObject);
    }

    public <T> T read(String queryId, Object parameterObject) {
        return getSqlSession().selectOne(queryId, parameterObject);
    }

    public <E> List<E> readList(String queryId, Object parameterObject) {
        return getSqlSession().selectList(queryId, parameterObject);
    }

    public <E> List<E> readListWithPaging(String queryId, Object parameterObject, int pageIndex, int pageSize) {
        int offset = pageIndex * pageSize;
        int limit = (pageIndex * pageSize) + pageSize;
        RowBounds rowBounds = new RowBounds(offset, limit);
        return getSqlSession().selectList(queryId, parameterObject, rowBounds);
    }
}
