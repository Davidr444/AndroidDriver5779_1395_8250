package com.jct.davidandyair.androiddriver5779_1395_8250.model.backend;

/**
 * <h1>Factory Backend Class</h1></h1>
 * The department is responsible for realizing the
 * Factory and Singleton patterns for the implementation of the backend
 * @author  David Rakovsky and Yair Ben-David
 * @version 1.0
 * @since   2019-02-05
 */
public class FactoryBackend {
    private static IBackend backend;

    /**
     * The function returns the appropriate backend
     * according to the Factory and Singleton pattern
     * @return The appropriate backend according to the Factory and Singleton pattern
     */
    public static IBackend getBackend()
    {
        if(backend == null)
            backend = new FireBaseBackend();
        return backend;
    }
}
