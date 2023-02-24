package org.pac4j.oauth.profile.foursquare;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.Token;
import lombok.val;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.converter.JsonConverter;
import org.pac4j.oauth.profile.definition.OAuthProfileDefinition;

import java.util.Arrays;

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

/**
 * This class is the Foursquare profile definition.
 *
 * @author Alexey Ogarkov
 * @since 1.5.0
 */
public class FoursquareProfileDefinition extends OAuthProfileDefinition {

    /** Constant <code>FIRST_NAME="firstName"</code> */
    public static final String FIRST_NAME = "firstName";
    /** Constant <code>LAST_NAME="lastName"</code> */
    public static final String LAST_NAME = "lastName";
    /** Constant <code>PHOTO="photo"</code> */
    public static final String PHOTO = "photo";
    /** Constant <code>FIRENDS="friends"</code> */
    public static final String FIRENDS = "friends";
    /** Constant <code>HOME_CITY="homeCity"</code> */
    public static final String HOME_CITY = "homeCity";
    /** Constant <code>CONTACT="contact"</code> */
    public static final String CONTACT = "contact";
    /** Constant <code>BIO="bio"</code> */
    public static final String BIO = "bio";

    /**
     * <p>Constructor for FoursquareProfileDefinition.</p>
     */
    public FoursquareProfileDefinition() {
        super(x -> new FoursquareProfile());
        Arrays.stream(new String[] {
                FIRST_NAME, LAST_NAME, HOME_CITY, BIO, PHOTO
        }).forEach(a -> primary(a, Converters.STRING));
        primary(GENDER, Converters.GENDER);
        primary(FIRENDS, new JsonConverter(FoursquareUserFriends.class));
        primary(CONTACT, new JsonConverter(FoursquareUserContact.class));
        primary(PHOTO, new JsonConverter(FoursquareUserPhoto.class));
    }

    /** {@inheritDoc} */
    @Override
    public String getProfileUrl(final Token accessToken, final OAuthConfiguration configuration) {
        return "https://api.foursquare.com/v2/users/self?v=20131118";
    }

    /** {@inheritDoc} */
    @Override
    public FoursquareProfile extractUserProfile(String body) {
        var profile = (FoursquareProfile) newProfile();
        var json = JsonHelper.getFirstNode(body);
        if (json == null) {
            raiseProfileExtractionJsonError(body);
        }
        var response = (JsonNode) JsonHelper.getElement(json, "response");
        if (response == null) {
            raiseProfileExtractionJsonError(body, "response");
        }
        var user = (JsonNode) JsonHelper.getElement(response, "user");
        if (user != null) {
            profile.setId(ProfileHelper.sanitizeIdentifier(JsonHelper.getElement(user, "id")));

            for (val attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute, JsonHelper.getElement(user, attribute));
            }
        } else {
            raiseProfileExtractionJsonError(body, "user");
        }
        return profile;
    }
}
