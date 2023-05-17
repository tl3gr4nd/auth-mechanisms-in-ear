package nl.thehpi.webapp2.api;

import nl.thehpi.ABean;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/h")
public class MyResource {

  private final ABean aBean;

  @Inject
  public MyResource(ABean aBean) {
    this.aBean = aBean;
  }

  @GET
  @Path("i")
  public Response hi() {

    aBean.doSomething();
    return Response.status(Response.Status.OK).build();
  }
}
