package com.epamtraining.resource;

import java.util.ResourceBundle;

/**
 * Performs path reading from property file.
 * @author Sergey Bondarenko
 */
public enum  PathManager {
    INSTANCE;

    private static final String BUNDLE_NAME = "path";
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Get path
     * @param key
     * @return path from bundle
     */
    public synchronized String getString(String key) {
        return bundle.getString(key);
    }
}

