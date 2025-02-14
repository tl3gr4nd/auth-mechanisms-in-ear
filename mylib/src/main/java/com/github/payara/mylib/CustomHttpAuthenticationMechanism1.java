package com.github.payara.mylib;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class CustomHttpAuthenticationMechanism1 implements HttpAuthenticationMechanism {

  @Override
  public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
    String user = request.getParameter("user");
    String group = request.getParameter("group");
    if (user != null && group != null) {
      CredentialValidationResult validationResult =
          new CredentialValidationResult(user, new HashSet<>(Collections.singletonList(group)));

      // Register session manually (if @AutoApplySession used, this would be done by its interceptor)
      httpMessageContext.setRegisterSession(validationResult.getCallerPrincipal().getName(), validationResult.getCallerGroups());
      return httpMessageContext.notifyContainerAboutLogin(validationResult);
    }
    return AuthenticationStatus.SEND_FAILURE;
  }

  private void init(@Observes @Initialized(ApplicationScoped.class) Object data) {
    System.out.println("Some custom initialization here");
  }

}
