package nl.thehpi.myejb;

import javax.ejb.Stateless;

@Stateless
public class MyEjb implements LocalEjb {

  @Override
  public void doSomething() {
    System.out.println("In EJB doing something");
  }
}
