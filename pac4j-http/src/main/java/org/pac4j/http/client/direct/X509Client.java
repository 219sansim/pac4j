package org.pac4j.http.client.direct;

import org.pac4j.core.client.DirectClient;
import org.pac4j.http.credentials.authenticator.X509Authenticator;
import org.pac4j.http.credentials.extractor.X509CredentialsExtractor;

/**
 * Direct client for X509 certificates.
 *
 * @author Jerome Leleu
 * @since 3.3.0
 */
public class X509Client extends DirectClient {

    /** {@inheritDoc} */
    @Override
    protected void internalInit(final boolean forceReinit) {
        setCredentialsExtractorIfUndefined(new X509CredentialsExtractor());
        setAuthenticatorIfUndefined(new X509Authenticator());
    }
}
