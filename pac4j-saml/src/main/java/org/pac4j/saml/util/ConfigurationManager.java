package org.pac4j.saml.util;

/**
 * A Java service provider API hook to allow configuration of OpenSAML.
 *
 * Implementations should include a javax|jakarta.annotation.Priority annotation. The lowest priority implementation
 * found is the one used.
 *
 * @see java.util.ServiceLoader
 * @since 3.3.0
 * @author bidou
 */
@FunctionalInterface
public interface ConfigurationManager {
    /**
     * <p>configure.</p>
     */
    void configure();
}
