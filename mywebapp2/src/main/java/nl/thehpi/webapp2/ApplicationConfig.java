package nl.thehpi.webapp2;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login‑servlet",
                errorPage = "/login‑servlet‑fail"
        )
)
public class ApplicationConfig {
}
