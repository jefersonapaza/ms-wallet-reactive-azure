package nttdata.bootcamp.quarkus.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import nttdata.bootcamp.quarkus.dto.request.WalletSaveDTO;
import nttdata.bootcamp.quarkus.dto.response.WalletSaveResponseDTO;
import nttdata.bootcamp.quarkus.entity.Wallet;
import nttdata.bootcamp.quarkus.entity.api.Client;

public interface WalletService {

    public Uni<WalletSaveResponseDTO> saveWallet(WalletSaveDTO walletSaveDTO, Client client);
    public Multi<Wallet> getAll();

    public Uni<Void> update(String id , Wallet wallet);

    public Uni<Boolean> delete(String id);

    public Uni<Wallet> findById(String id);

}
