package nttdata.bootcamp.quarkus.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.dto.response.WalletSaveResponseDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;

@ApplicationScoped
public class WalletServiceImpl implements  WalletService{


    @Override
    public Uni<WalletSaveResponseDTO> save(WalletSaveDTO walletSaveDTO) {
        return null;
    }

    @Override
    public Multi<Wallet> list() {
        return null;
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
