package org.pac4j.oauth.profile.cronofy;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.Token;
import lombok.val;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;

import java.util.Arrays;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * This class is the Cronofy profile definition.
 *
 * @author Jerome Leleu
 * @since 5.3.1
 */
public class CronofyProfileDefinition extends OAuthProfileDefinition {

    /** Constant <code>SUB="sub"</code> */
    public static final String SUB = "sub";
    /** Constant <code>ACCOUNT_ID="account_id"</code> */
    public static final String ACCOUNT_ID = "account_id";
    /** Constant <code>PROVIDER_NAME="provider_name"</code> */
    public static final String PROVIDER_NAME = "provider_name";
    /** Constant <code>PROFILE_ID="profile_id"</code> */
    public static final String PROFILE_ID = "profile_id";
    /** Constant <code>PROFILE_NAME="profile_name"</code> */
    public static final String PROFILE_NAME = "profile_name";
    /** Constant <code>PROVIDER_SERVICE="provider_service"</code> */
    public static final String PROVIDER_SERVICE = "provider_service";

    /**
     * <p>Constructor for CronofyProfileDefinition.</p>
     */
    public CronofyProfileDefinition() {
        super(x -> new CronofyProfile());
        Arrays.stream(new String[] {ACCOUNT_ID, PROVIDER_NAME, PROFILE_ID, PROFILE_NAME, PROVIDER_SERVICE})
            .forEach(a -> primary(a, Converters.STRING));
    }

    /** {@inheritDoc} */
    @Override
    public String getProfileUrl(final Token accessToken, final OAuthConfiguration configuration) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CronofyProfile extractUserProfile(final String body) {
        val profile = (CronofyProfile) newProfile();
        val json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(JsonHelper.getElement(json, SUB)));
            convertAndAdd(profile, PROFILE_ATTRIBUTE, ACCOUNT_ID, JsonHelper.getElement(json, ACCOUNT_ID));
            val linkingProfile = (JsonNode) JsonHelper.getElement(json, "linking_profile");
            if (linkingProfile != null) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, PROVIDER_NAME, JsonHelper.getElement(linkingProfile, PROVIDER_NAME));
                convertAndAdd(profile, PROFILE_ATTRIBUTE, PROFILE_ID, JsonHelper.getElement(linkingProfile, PROFILE_ID));
                convertAndAdd(profile, PROFILE_ATTRIBUTE, PROFILE_NAME, JsonHelper.getElement(linkingProfile, PROFILE_NAME));
                convertAndAdd(profile, PROFILE_ATTRIBUTE, PROVIDER_SERVICE, JsonHelper.getElement(linkingProfile, PROVIDER_SERVICE));
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }
}
