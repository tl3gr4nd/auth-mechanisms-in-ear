package nl.thehpi;

import nl.thehpi.myejb.LocalEjb;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "Titi",
    urlPatterns = {"/titi/*"})
public class MyServlet extends HttpServlet {

  @EJB
  private LocalEjb localEjb;

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    localEjb.doSomething();
  }
}
