package nl.thehpi;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ABean {

  public void doSomething() {
    System.out.println("Did something");
  }
}
