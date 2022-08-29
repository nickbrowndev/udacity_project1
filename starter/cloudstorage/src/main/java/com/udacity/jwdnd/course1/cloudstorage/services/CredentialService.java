package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.DuplicateEntityException;
import com.udacity.jwdnd.course1.cloudstorage.exception.EntityNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = Objects.requireNonNull(credentialMapper);
        this.userService = Objects.requireNonNull(userService);
        this.encryptionService = Objects.requireNonNull(encryptionService);
    }

    public List<Credential> getCredentialsForUser(Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        return credentialMapper.getCredentialsForUser(currentUser);
    }

    public Integer insert(Credential credential, Authentication authentication) throws DuplicateEntityException {
        User currentUser = userService.getCurrentUser(authentication);

        String encodedKey = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, currentUser, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword));
    }

    public boolean update(Credential credential, Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        String encodedKey = generateKey();
        return credentialMapper.update(new Credential(credential.getCredentialId(), currentUser, credential.getUrl(), credential.getUsername(), encodedKey, encryptionService.encryptValue(credential.getPassword(), encodedKey)));
    }

    public Credential getCredential(Integer credentialId, Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        Credential credential = credentialMapper.getCredential(credentialId);
        if (credential == null) {
            throw new EntityNotFoundException("Credential could not be found for this user");
        }
        return credential;
    }

    public void deleteCredential(int credentialId) {
        credentialMapper.delete(credentialId);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
