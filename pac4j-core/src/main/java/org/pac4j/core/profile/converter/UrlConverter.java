package org.pac4j.core.profile.converter;

import lombok.val;
import org.pac4j.core.util.CommonHelper;

import java.net.URI;

/**
 * URL converter.
 *
 * @author Jerome Leleu
 * @since 2.0.0
 */
public class UrlConverter extends AbstractAttributeConverter {

    /**
     * <p>Constructor for UrlConverter.</p>
     */
    public UrlConverter() {
        super(URI.class);
    }

    /** {@inheritDoc} */
    @Override
    protected URI internalConvert(final Object attribute) {
        if (attribute instanceof String sAttribute) {
            val s = sAttribute.replaceAll("\\/", "/");
            return CommonHelper.asURI(s);
        }
        return null;
    }
}
