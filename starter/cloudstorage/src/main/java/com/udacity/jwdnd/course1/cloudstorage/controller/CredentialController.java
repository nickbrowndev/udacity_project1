package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class CredentialController {

    private final CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = Objects.requireNonNull(credentialService);
    }

    @GetMapping("/credentials")
    public String listCredentials(Model model, Authentication authentication) {

        model.addAttribute("credentials", credentialService.getCredentialsForUser(authentication));
        return "home";
    }
    @PostMapping("/credentials")
    public String postCredential(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (credential.getCredentialId() != null) {
            credentialService.update(credential, authentication);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Credential updated!");
        } else {
            credentialService.insert(credential, authentication);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Credential created!");
        }
        return "redirect:/home";
    }
    @PostMapping("/credentials/{credentialId}/actions/delete")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("successMessage",
                "Credential deleted!");
        return "redirect:/home";
    }
}
