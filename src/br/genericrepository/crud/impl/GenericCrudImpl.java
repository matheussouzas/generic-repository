/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.genericrepository.crud.impl;

import br.genericrepository.crud.GenericCrud;
import br.genericrepository.enumeration.QueryType;
import br.genericrepository.repository.Repository;
import br.genericrepository.repository.impl.RepositoryImpl;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @brief Classe GenericDaoImpl
 * @author Kaynan
 * @date 06/06/2015
 */
public class GenericCrudImpl<T> implements GenericCrud<T> {

    private final Repository<T> repository;

    public GenericCrudImpl() {
        this.repository = new RepositoryImpl<>();
    }

    @Override
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public void update(T entity) {
        repository.update(entity);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public List<T> find(Class<T> entityClass) {
        return find(entityClass, -1, -1);
    }

    @Override
    public T find(Class<T> entityClass, Object id) {
        return repository.find(entityClass, id);
    }

    @Override
    public List<T> find(Class<T> entityClass, int firstResult, int maxResults) {
        return repository.find(entityClass, firstResult, maxResults);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(QueryType type, String query, Map<String, Object> namedParams) {
        return find(type, query, namedParams, -1, -1);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(String query, Map<String, Object> namedParams) {
        return find(query, namedParams, -1, -1);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(String queryName, Map<String, Object> namedParams,
            int firstResult, int maxResults) {
        return find(QueryType.NAMED, queryName, namedParams, firstResult, maxResults);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List find(QueryType type, String query,
            Map<String, Object> namedParams, int firstResult, int maxResults) {
        return repository.find(type, query, namedParams, firstResult, maxResults);
    }

    @Override
    public Object findFirst(String query, Map<String, Object> namedParams) {
        return findFirst(QueryType.NAMED, query, namedParams);
    }

    @Override
    public Object findFirst(QueryType type, String query, Map<String, Object> namedParams) {
        return repository.findFirst(type, query, namedParams);
    }

    @Override
    public int executeUpdate(String sql, Map<String, Object> namedParams) {
        return repository.executeUpdate(sql, namedParams);
    }
}
