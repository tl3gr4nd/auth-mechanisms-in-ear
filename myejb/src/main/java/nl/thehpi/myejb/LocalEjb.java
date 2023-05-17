package nl.thehpi.myejb;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public interface LocalEjb {

  void doSomething();

}
