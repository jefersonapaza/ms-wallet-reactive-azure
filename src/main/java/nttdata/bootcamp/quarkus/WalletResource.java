package nttdata.bootcamp.quarkus;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.service.WalletService;
import org.bson.types.ObjectId;


@Path("/api/wallet")
public class WalletResource {


    @Inject
    WalletService walletService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive Wallet";
    }


    @POST
    public Uni<Response> save(WalletSaveDTO walletDTO) {
        return walletService.save(walletDTO).map(r -> Response.accepted().build());
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Wallet> findUserById(@PathParam("id") String id) {
        return walletService.findById(id);
    }
    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteUserById(@PathParam("id") String id) {
        return walletService.delete(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateWalletById(@PathParam("id") String id,Wallet wallet) {

        wallet.setId(new ObjectId(id));
        return walletService.update(id,wallet).
                map(r -> Response.accepted().build());

    }

}
