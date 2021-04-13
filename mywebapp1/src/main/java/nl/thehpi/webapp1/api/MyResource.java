package nl.thehpi.webapp1.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/h")
public class MyResource {

  @GET
  @Path("i")
  public Response hi() {
    return Response.status(Response.Status.OK).entity("Hello from resource A").build();
  }
}
