package org.ddx.util;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  Utility for working with java 7 nio Paths API
 */
public class PathUtil {

    /**
     *  Gets a Path object for nio API relative to the classpath.
     *
     * @param classpathRelativePath
     * @return
     * @throws URISyntaxException
     */
    public Path getFromClasspath(String classpathRelativePath) throws URISyntaxException {
        return Paths.get( PathUtil.class.getClassLoader().getResource(classpathRelativePath).toURI());
    }
}
