package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("MR")
public class ModuleRessources {

    private ModuleBusiness moduleBusiness = new ModuleBusiness();
    private UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    @GET
    @Path("lstMd")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Module> getModules() {
        return moduleBusiness.getAllModules();
    }

    @GET
    @Path("MT/{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModule(@PathParam("matricule") String matricule) {
        Module m = moduleBusiness.getModuleByMatricule(matricule);
        return m != null ? Response.ok(m).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("UE")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Module> getModulesByUE(@QueryParam("codeUE") int codeUE) {
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);
        return ue != null ? moduleBusiness.getModulesByUE(ue) : List.of();
    }

    @POST
    @Path("addM")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addModule(Module module) {
        boolean added = moduleBusiness.addModule(module);
        return added ? Response.status(Response.Status.CREATED).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/updateM/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        boolean updated = moduleBusiness.updateModule(matricule, module);
        return updated ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/deleteM/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        boolean deleted = moduleBusiness.deleteModule(matricule);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
