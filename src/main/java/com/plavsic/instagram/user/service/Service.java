package com.plavsic.instagram.user.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface Service<T,U> {
    Optional<U> findById(Long id);
    List<U> findAll();
    U save(T t);
    U update(Long id,T t);
    boolean delete(Long id);
}
