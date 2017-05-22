package org.zalando.example.zauth.config;

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;

import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.FileCredentialsSupplierSupport;
import org.springframework.util.Assert;

public class PlatformCredentialsetFileReader extends FileCredentialsSupplierSupport
        implements ClientCredentialsSupplier {

    private static final String CLIENT_SECRET = "-client-secret";
    private static final String CLIENT_ID = "-client-id";
    private final String name;

    public PlatformCredentialsetFileReader(String credentialsDirectoryPath, String name) {
        super(credentialsDirectoryPath);
        Assert.hasText(name, "'name' should never be null or empty");
        this.name = name;
    }

    @Override
    public String getClientId() {
        try {
            return new String(readAllBytes(getFileInCredentialsDir(this.name + CLIENT_ID).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClientSecret() {
        try {
            return new String(readAllBytes(getFileInCredentialsDir(this.name + CLIENT_SECRET).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
