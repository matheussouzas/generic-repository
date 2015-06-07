/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.genericrepository.crud;

import br.genericrepository.enumeration.QueryType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kaynan
 * @param <T>
 */
public interface GenericCrud<T> {

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public T find(Class<T> entityClass, Object id);

    public List<T> find(Class<T> entityClass);

    public List<T> find(Class<T> entityClass, int firstResult, int maxResults);

    @SuppressWarnings("rawtypes")
    public List find(String queryName,
            Map<String, Object> namedParams);

    @SuppressWarnings("rawtypes")
    public List find(QueryType type, String query,
            Map<String, Object> namedParams);

    @SuppressWarnings("rawtypes")
    public List find(String queryName,
            Map<String, Object> namedParams, int firstResult, int maxResults);

    @SuppressWarnings("rawtypes")
    public List find(QueryType type, String query,
            Map<String, Object> namedParams, int firstResult, int maxResults);

    public Object findFirst(String query, Map<String, Object> namedParams);

    public Object findFirst(QueryType type, String query, Map<String, Object> namedParams);

    public int executeUpdate(String sql, Map<String, Object> namedParams);

}
