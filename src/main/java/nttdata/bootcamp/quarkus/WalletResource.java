package nttdata.bootcamp.quarkus;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.entity.api.Client;
import nttdata.bootcamp.quarkus.entity.api.DebitCard;
import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;
import nttdata.bootcamp.quarkus.repository.redis.WalletRedisRepository;
import nttdata.bootcamp.quarkus.service.WalletService;
import nttdata.bootcamp.quarkus.service.api.ClientAPI;
import nttdata.bootcamp.quarkus.service.api.DebitCardAPI;
import org.bson.types.ObjectId;


@Path("/api/wallet")
public class WalletResource {


    @Inject
    WalletService walletService;


    @Inject
    ClientAPI clientAPI;

    @Inject
    DebitCardAPI debitCardAPI;


    @Inject
    WalletRedisRepository walletRedisRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        return "Hello from RESTEasy Reactive Wallet";
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Wallet> getAll() {

        return walletService.getAll();
    }



    @POST
    @Path("/register")
    public Uni<Response> save(WalletSaveDTO walletDTO) {

        Uni<Client> clientUni = clientAPI.findClientById(walletDTO.getClientId());
        return clientUni.flatMap(client -> {
            if(clientUni != null){
                Uni<DebitCard> debitCardUni = debitCardAPI.findCreditCardById(walletDTO.getDebitCardId());
                debitCardUni.flatMap( debitCard -> {
                    if(debitCard != null){
                        if(debitCard.getValidationcode().equals(walletDTO.getValidationCode())){
                            if(walletDTO.getPassword().length() == 6){
                                return walletService.saveWallet(walletDTO,client).map(r -> Response.accepted().build());
                            }else{
                                return Uni.createFrom().item(Response.serverError().entity("Password must be six letters").build());
                            }
                        }else{
                            return Uni.createFrom().item(Response.serverError().entity("Validation Code Invalid").build());
                        }
                    }else{
                        return Uni.createFrom().item(Response.serverError().entity("DebitCard not exists").build());
                    }
                });
            }else{
                return Uni.createFrom().item(Response.serverError().entity("Client not exists").build());
            }
            return null;
        });
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

        wallet.set_id(new ObjectId(id));
        return walletService.update(id,wallet).
                map(r -> Response.accepted().build());

    }

    @POST
    @Path("/redis")
    public void create(WalletRedis walletRedis) {
         walletRedisRepository.save(walletRedis.getIdWallet(),walletRedis);
               // .map(r -> Response.accepted().build());

    }

    @GET
    @Path("/redis/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public WalletRedis getkey(String key) {
        return walletRedisRepository.findByKey(key);
    }



}
