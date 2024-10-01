package com.egov.egovtemplate.personnel.auth;


import com.egov.egovtemplate.CredentialLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthApi
{
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    CredentialLookupService credentialLookupService;

   // @Autowired
   // @Qualifier("dummycredential")
    Credential dummyCredential;

    AuthApi(Credential credential)
    {
        this.dummyCredential = credential;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password
                         )
    {
        Credential credential = credentialLookupService.getDummyCredential();

        //Credential credential = new Credential(); // USING THE NEW KEYWORD WITH LIMITED SCOPE IS NOT SUCH A BAD THINGS
        credential.setId(java.util.UUID.randomUUID());
        credential.setUsername(username);
        credential.setPassword(password);

        // Persist the Credential in the database
        credentialRepository.save(credential);

        // Redirect to the login page
        return "redirect:/index.html";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model
    )
    {
        Credential credential = credentialRepository.findByUsername(username);
        if(credential != null && credential.getPassword().equals(password))
        {
            model.addAttribute("username", username);
            return "dashboard";
        }
        else
        {
            return "login?error";
        }
    }

}
