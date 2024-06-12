package system.impl;

import java.util.*;
import java.util.function.Function;

import datamodel.Article;
import system.Repository;

/**
 * Implementation class of {@code Repository<T, ID>}.
 *
 * @param <T> generic type of Repository objects (entities).
 * @param <ID> generic type of object identifier (id).
 */
class MockRepositoryImpl<T, ID> implements Repository<T, ID> {

    /**
     * Map that actually stores {@link datamodel} objects of type T.
     */
    private final List<T> list = new ArrayList<>();

    /**
     * Externally provided function that obtains id from entity. 
     */
    private final Function<T, ID> getIdFunc;


    /**
     * Constructor with function argument to obtain the id from an entity of type T.
     * 
     * @param getIdFunc function that obtains id from entity of type T.
     */
    MockRepositoryImpl(Function<T, ID> getIdFunc) {
        if(getIdFunc==null)
            throw new IllegalArgumentException("argument getIdFunc is null.");
        //
        this.getIdFunc = getIdFunc;
    }

    /**
     * Return the current number of objects in repository.
     *
     * @return number of entities.
     */
    @Override
    public long count() {
        return list.size();
    }

    /**
     * Return all repository objects.
     * 
     * @return all repository objects.
     */
    @Override
    public Iterable<T> findAll() {
        return list;
    }

    /**
     * Return result of a lookup of an object by its {@literal id}.
     * 
     * @param id {@literal id} of object to find.
     * @return result of lookup of an object by its {@literal id}.
     * @throws IllegalArgumentException {@literal id} is {@literal null}.
     */
    @Override
    public Optional<T> findById(ID id) {
        if(id==null)
            throw new IllegalArgumentException("argument id is null.");
        //
        return list.stream()
            .filter(obj -> getIdFunc.apply(obj).equals(id))
            .findFirst();
    }

    /**
     * Return result of a lookup of an object by its {@literal id}.
     * 
     * @param id {@literal id} of object to find.
     * @return true if an object with {@literal id} exists in repository.
     * @throws IllegalArgumentException {@literal id} is {@literal null}.
     */
    @Override
    public boolean existsById(ID id) {
        return false;
    }

    /**
     * Return result of a lookup of objects by a collection of {@literal ids}.
     * No object is included in result if {@literal id} could not be found.
     * 
     * @param id collection of {@literal ids} for which objects are looked up.
     * @return result of lookup by a collection of {@literal ids}.
     * @throws IllegalArgumentException {@literal ids} is {@literal null}.
     */
    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        if(ids==null)
            throw new IllegalArgumentException("argument ids is null.");
        //
        return List.of();   // empty result
    }

    /**
     * Save object (entity) to a repository. Object replaces a prior object
     * with the same {@literal id}.
     * 
     * @param <S> sub-class of {@code <T>}.
     * @param entity object saved to the repository.
     * @return the saved entity.
     * @throws IllegalArgumentException {@literal entity} or entity's {@literal id}
     * is {@literal null}.
     */
    @Override
    public <S extends T> S save(S entity) {
        if(entity==null)
            throw new IllegalArgumentException("argument entity is null.");
        //
        if(list.size() < 3 || entity instanceof Article) {
            list.add(entity);
        }
        return entity;
    }

    /**
     * Save a collection of objects (entities) to a repository. Objects replace
     * prior objects with the same {@literal id}.
     * 
     * @param <S> sub-class of {@code <T>}.
     * @param entities collection of objects (entities) saved to repository.
     * @return collection of saved objects.
     * @throws IllegalArgumentException {@literal entities} is {@literal null}.
     */
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        if(entities==null)
            throw new IllegalArgumentException("argument entities is null.");
        //
        return entities;    // method has no effect
    }

    /**
     * Delete object with id: {@literal id} from repository, if objects exists.
     * No change of repository if no object with id: {@literal id} exists.
     * 
     * @param id {@literal id} of entity to delete.
     * @throws IllegalArgumentException {@literal id} is {@literal null}.
     */
    @Override
    public void deleteById(ID id) {
        if(id==null)
            throw new IllegalArgumentException("argument id is null.");
        //
        // method has no effect
    }

    /**
     * Delete object (entity) from repository. No change of repository if object
     * does not exist.
     * 
     * @param entity {@literal entity} to delete.
     * @throws IllegalArgumentException {@literal entity} is {@literal null}.
     */
    @Override
    public void delete(T entity) {
        // method has no effect
    }

    /**
     * Delete objects of matching collection of {@literal ids} from repository.
     * No change of repository if an {@literal id} does not exist.
     * 
     * @param ids collection of {@literal ids} to delete.
     * @throws IllegalArgumentException {@literal entity} is {@literal null}.
     */
    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        // method has no effect
    }

    /**
     * Delete collection of objects (entities) from repository.
     * No change of repository if an {@literal object} does not exist.
     * 
     * @param entities collection of {@literal entities} to delete.
     * @throws IllegalArgumentException {@literal entities} is {@literal null}.
     */
    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        // method has no effect
    }

    /**
     * Clear repository and delete all objects from repository.
     * 
     */
    @Override
    public void deleteAll() {
        // method has no effect
    }
}