package org.zalando.example.zauth;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UidExtractor {

    private final String uid_key = "https://identity.zalando.com/managed-id";

    @SuppressWarnings("unchecked")
    public String extractUid(String accessToken) {
        String middle = accessToken.substring(accessToken.indexOf(".") + 1, accessToken.lastIndexOf("."));
        Map<String, String> content = null;
        ObjectMapper om = new ObjectMapper();
        try {
            content = om.readValue(Base64.getDecoder().decode(middle), Map.class);
            return (String) content.get(uid_key);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
