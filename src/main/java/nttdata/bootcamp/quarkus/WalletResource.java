package nttdata.bootcamp.quarkus;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
import javax.ws.rs.*;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.entity.api.Client;
import nttdata.bootcamp.quarkus.entity.api.DebitCard;
import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;
import nttdata.bootcamp.quarkus.repository.redis.IncrementService;
import nttdata.bootcamp.quarkus.repository.redis.WalletRedisRepository;
import nttdata.bootcamp.quarkus.service.WalletService;
import nttdata.bootcamp.quarkus.service.api.ClientAPI;
import nttdata.bootcamp.quarkus.service.api.DebitCardAPI;
import nttdata.bootcamp.quarkus.service.api.RedisApi;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;




@Path("/api/wallet")
public class WalletResource {


    @Inject
    WalletService walletService;


    @Inject
    ClientAPI clientAPI;

    @Inject
    DebitCardAPI debitCardAPI;


    //@Inject
    //IncrementService service;

    @RestClient
    RedisApi redisApi;

    @Inject
    WalletRedisRepository walletRedisRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        return "Hello from RESTEasy Reactive Wallet";
    }

    @GET
    @Path("/list")
    @Operation(summary = "Lista Wallets",description = "Lista Wallets")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Wallet> getAll() {

        return walletService.getAll();
    }

    @GET
    @Path("/debit")
    @Operation(summary = "Obtener Todas las tarjetas de debito",description = "Obtener Todas las tarjetas de debito")
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<DebitCard> getAllDebit() {

        return DebitCard.streamAll();
    }

    @GET
    @Path("/debit/{id}")
    @Operation(summary = "Obtiene la tarjeta de debito por ID",description = "Obtiene la tarjeta de debito por ID")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<DebitCard> getAllDebitById(@PathParam("id") String id) {

        return DebitCard.findById(new ObjectId(id));
    }


    private String generateApprovalCode(){
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    @POST
    @Path("/register")
    @Operation(summary = "Registra Wallet",description = "Registra Wallet obteniendo codigo de verificacion")
    public Uni<Response> save(WalletSaveDTO walletDTO) {

        Uni<Client> clientUni = clientAPI.findClientById(walletDTO.getClientId());
        return clientUni.flatMap(client -> {
            if(client != null){
                Uni<DebitCard> debitCardUni = DebitCard.findById(new ObjectId(walletDTO.getDebitCardId()));
                return debitCardUni.flatMap( debitCard -> {
                    if(debitCard != null){
                        if(debitCard.getValidationcode().equals(walletDTO.getValidationCode())){
                            if(walletDTO.getPassword().length() == 6){
                                //return walletService.saveWallet(walletDTO,client).map(r -> Response.accepted().entity(r).build());
                                return walletService.saveWallet(walletDTO,client).flatMap(walletSaveResponseDTO -> {


                                    /*
                                    WalletRedis walletRedis = new WalletRedis();
                                    walletRedis.setIdWallet(walletSaveResponseDTO.getIdWallet());
                                    walletRedis.setExpirationDate(new Date());
                                    walletRedis.setApprovalCode(walletSaveResponseDTO.getApprovalCode());
                                    */

                                    /*
                                    HttpRequest request = HttpRequest.newBuilder()
                                            .uri(URI.create("https://foo.com/"))
                                            .timeout(Duration.ofMinutes(2))
                                            .header("Content-Type", "application/json")
                                            .POST(HttpRequest.BodyPublishers.fr)
                                            .build();
                                    client.sendAsync(request, BodyHandlers.ofString())
                                            .thenApply(HttpResponse::body)
                                            .thenAccept(System.out::println);
                                    */
                                   // walletRedisRepository.save(walletSaveResponseDTO.getIdWallet(),walletRedis);

                                    return Uni.createFrom().item(Response.accepted().entity(walletSaveResponseDTO).build());
                                });

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
        });
    }




    @GET
    @Path("/{id}")
    @Operation(summary = "Registra Wallet",description = "Registra Wallet obteniendo codigo de verificacion")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Wallet> findUserById(@PathParam("id") String id) {
        return walletService.findById(id);
    }
    @DELETE
    @Path("{id}")
    @Operation(summary = "Registra Wallet",description = "Registra Wallet obteniendo codigo de verificacion")
    public Uni<Boolean> deleteUserById(@PathParam("id") String id) {
        return walletService.delete(id);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Registra Wallet",description = "Registra Wallet obteniendo codigo de verificacion")
    public Uni<Response> updateWalletById(@PathParam("id") String id,Wallet wallet) {

        wallet.setId(new ObjectId(id));
        return walletService.update(id,wallet).
                map(r -> Response.accepted().build());

    }

    @POST
    @Path("/redis/save")
    @Operation(summary = "Registra Cache Redis",description = "Registra Cache Redis")
    public void create(WalletRedis walletRedis) {
         walletRedisRepository.save(walletRedis.getIdWallet(),walletRedis);
               // .map(r -> Response.accepted().build());

    }

    @GET
    @Path("/redis/{key}")
    @Operation(summary = "Registra Wallet",description = "Registra Wallet obteniendo codigo de verificacion")
    @Produces(MediaType.APPLICATION_JSON)
    public WalletRedis getkey(String key) {
        return walletRedisRepository.findByKey(key);
    }




    @GET
    @Path("/rediss/test/all")
    @Operation(summary = "Lista todo Redis",description = "Lista todo Redis")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<String>> allRedis(String key) {
        //return service.keys();
        return redisApi.getAll();
    }





}
