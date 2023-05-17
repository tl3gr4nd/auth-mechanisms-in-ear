package nl.thehpi.mybeans;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class MyBean {

  private void init(@Observes @Initialized(ApplicationScoped.class) Object data) {
    System.out.println("Initialized");
  }
}
