package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("UE")
public class UniteEnseignementRessources {

    private UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UniteEnseignement> getUE(@QueryParam("semestre") Integer semestre,
                                         @QueryParam("code") Integer code) {
        if (code != null) {
            UniteEnseignement ue = ueBusiness.getUEByCode(code);
            return ue != null ? List.of(ue) : List.of();
        }
        if (semestre != null) {
            return ueBusiness.getUEBySemestre(semestre);
        }
        return ueBusiness.getListeUE();
    }

    @POST
    @Path("ADD")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUE(UniteEnseignement ue) {
        boolean added = ueBusiness.addUniteEnseignement(ue);
        return added ? Response.status(Response.Status.CREATED).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUE(@PathParam("code") int code, UniteEnseignement ue) {
        boolean updated = ueBusiness.updateUniteEnseignement(code, ue);
        return updated ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/delete/{code}")
    public Response deleteUE(@PathParam("code") int code) {
        boolean deleted = ueBusiness.deleteUniteEnseignement(code);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

}
