package nttdata.bootcamp.quarkus.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.dto.response.WalletSaveResponseDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.entity.api.Client;
import nttdata.bootcamp.quarkus.repository.WalletRepository;

@ApplicationScoped
public class WalletServiceImpl implements  WalletService{


    @Inject
    WalletRepository walletRepository;



    @Override
    public Uni<WalletSaveResponseDTO> saveWallet(WalletSaveDTO walletSaveDTO, Client client) {
        Wallet wallet = new Wallet();
        wallet.setPhone(client.getCellPhone());
        wallet.setPassword(walletSaveDTO.getPassword());
        wallet.setIdClient(walletSaveDTO.getClientId());
        wallet.setValidationCode(walletSaveDTO.getValidationCode());
        wallet.setIdDebitCard(walletSaveDTO.getDebitCardId());
        wallet.setStatus("I");
        return walletRepository.persist(wallet).flatMap(wallet1 -> {
            WalletSaveResponseDTO walletSaveResponseDTO = new WalletSaveResponseDTO();
            walletSaveResponseDTO.setIdWallet(wallet.get_id().toString());
            return null;
        });
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
