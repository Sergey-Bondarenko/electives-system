package com.epamtraining.builder;


import com.epamtraining.exception.BuildException;

import java.util.Map;

/**
 * Abstract entity from request builder
 */
public abstract class EntityBuilder<T> {

    /**
     * Build entity from request
     *
     * @param map
     * @throws BuildException
     */
    public abstract T build(Map<String, String[]> map) throws BuildException;
}
