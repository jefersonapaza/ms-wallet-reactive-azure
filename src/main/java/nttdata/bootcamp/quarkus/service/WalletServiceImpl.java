package nttdata.bootcamp.quarkus.service;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.dto.response.WalletSaveResponseDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.entity.api.Client;
import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;
import nttdata.bootcamp.quarkus.repository.WalletRepository;
import nttdata.bootcamp.quarkus.repository.redis.WalletRedisRepository;
import nttdata.bootcamp.quarkus.service.api.RedisApi;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

@ApplicationScoped
public class WalletServiceImpl implements  WalletService{


    @Inject
    WalletRepository walletRepository;


    @Inject
    WalletRedisRepository walletRedisRepository;

  //  @RestClient
  //  RedisApi redisApi;


    private String generateApprovalCode(){
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    @Override
    public Uni<WalletSaveResponseDTO> saveWallet(WalletSaveDTO walletSaveDTO, Client client) {
        Wallet wallet = new Wallet();

        wallet.setPhone(client.getCellPhone());
        wallet.setPassword(walletSaveDTO.getPassword());
        wallet.setIdClient(walletSaveDTO.getClientId());
        wallet.setValidationCode(walletSaveDTO.getValidationCode());
        wallet.setIdDebitCard(walletSaveDTO.getDebitCardId());
        wallet.setStatus("I");
     //   System.out.println("ANTES DE PERSISTIR EN BASE DE DATOS ..!!! ");

        return walletRepository.persist(wallet).flatMap(wallet1 -> {
            WalletSaveResponseDTO walletSaveResponseDTO = new WalletSaveResponseDTO();

            walletSaveResponseDTO.setIdWallet(wallet1.getId().toString());
            String approvalCode = this.generateApprovalCode();
            walletSaveResponseDTO.setApprovalCode(approvalCode);

            WalletRedis walletRedis = new WalletRedis();
            walletRedis.setIdWallet(walletSaveResponseDTO.getIdWallet());
            walletRedis.setExpirationDate(new Date());
            walletRedis.setApprovalCode(walletSaveResponseDTO.getApprovalCode());

            //redisApi.add(walletRedis);

            /*Save Cache Redis*/
          //ssf  this.registerRedisWallet(wallet1,approvalCode);
            /*-----------------*/

            return Uni.createFrom().item(walletSaveResponseDTO);
        });


    }


    public void registerRedisWallet(Wallet wallet ,String approvalCode){
        WalletRedis walletRedis = new WalletRedis();
        walletRedis.setIdWallet(wallet.getId().toString());
        walletRedis.setExpirationDate(new Date());
        walletRedis.setApprovalCode(approvalCode);
        walletRedisRepository.save(wallet.getId().toString(),walletRedis);
    }

    @Override
    public Multi<Wallet> getAll() {
        return walletRepository.getAll();
    }

    @Override
    public Uni<Void> update(String id, Wallet wallet) {
        return null;
    }

    @Override
    public Uni<Boolean> delete(String id) {
        return null;
    }

    @Override
    public Uni<Wallet> findById(String id) {
        return null;
    }



}
