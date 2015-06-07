/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.genericrepository.repository.impl;

import br.genericrepository.enumeration.QueryType;
import br.genericrepository.repository.Repository;
import br.genericrepository.util.ConfigDataSource;
import com.mysql.jdbc.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 * @param <T>
 * @brief Classe RepositoryImpl
 * @author Kaynan
 * @date 06/06/2015
 */
public class RepositoryImpl<T> implements Repository<T> {

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory(ConfigDataSource.nameConnection);
    protected EntityManager em = emf.createEntityManager();

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void openConnection() {
        emf = Persistence.createEntityManagerFactory(ConfigDataSource.nameConnection);
        em = emf.createEntityManager();
    }

    @Override
    public void closeConnection() {
        em.close();
        emf.close();
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Session session = (Session) em.getDelegate();
            SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
            ConnectionProvider cp = sfi.getConnectionProvider();
            connection = (Connection) cp.getConnection();
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    @Override
    public void save(T entity) {
        this.em.getTransaction().begin();
        this.em.persist(entity);
        this.em.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        this.em.getTransaction().begin();
        this.em.merge(entity);
        this.em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        this.em.getTransaction().begin();
        this.em.remove(entity);
        this.em.getTransaction().commit();
    }

    @Override
    public T find(Class<T> entityClass, Object id) {
        T result = null;
        result = em.find(entityClass, id);
        return result;
    }

    @Override
    public List<T> find(Class<T> entityClass) {
        return find(entityClass, -1, -1);
    }

    @Override
    public List<T> find(Class<T> entityClass, int firstResult, int maxResults) {
        List<T> result = null;
        Query q = em.createQuery("select obj from " + entityClass.getSimpleName() + " obj");
        if (firstResult >= 0 && maxResults >= 0) {
            q = q.setFirstResult(firstResult).setMaxResults(maxResults);
        }
        result = q.getResultList();
        return result;
    }

    @Override
    public List find(String queryName, Map<String, Object> namedParams) {
        return find(QueryType.NAMED, queryName, namedParams);
    }

    @Override
    public List find(QueryType type, String query, Map<String, Object> namedParams) {
        return find(type, query, namedParams, -1, -1);
    }

    @Override
    public List find(String queryName, Map<String, Object> namedParams, int firstResult, int maxResults) {
        return find(QueryType.NAMED, queryName, namedParams, firstResult, maxResults);
    }

    @Override
    public List find(QueryType type, String query, Map<String, Object> namedParams, int firstResult, int maxResults) {
        List result = null;
        Query q;
        if (type == QueryType.JPQL) {
            q = em.createQuery(query);
        } else if (type == QueryType.NATIVE) {
            q = em.createNativeQuery(query);
        } else if (type == QueryType.NAMED) {
            q = em.createNamedQuery(query);
        } else {
            throw new IllegalArgumentException("Invalid query type: " + type);
        }

        setNamedParameters(q, namedParams);

        if (firstResult >= 0 && maxResults >= 0) {
            q = q.setFirstResult(firstResult).setMaxResults(maxResults);
        }

        result = q.getResultList();

        return result;
    }

    private void setNamedParameters(Query q, Map<String, Object> namedParams) {
        if (namedParams != null) {
            Set<String> keys = namedParams.keySet();
            for (String key : keys) {
                q.setParameter(key, namedParams.get(key));
            }
        }
    }

    @Override
    public Object findFirst(String queryName, Map<String, Object> namedParams) {
        return findFirst(QueryType.NAMED, queryName, namedParams);
    }

    @Override
    public Object findFirst(QueryType type, String query, Map<String, Object> namedParams) {
        @SuppressWarnings("rawtypes")
        List result = find(type, query, namedParams, 0, 1);
        return result == null || result.size() == 0 ? null : result.get(0);
    }

    @Override
    public int executeUpdate(String sql, Map<String, Object> namedParams) {
        Query q = em.createNativeQuery(sql);
        setNamedParameters(q, namedParams);
        return q.executeUpdate();
    }
}
