package nttdata.bootcamp.quarkus.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletSaveResponseDTO {


    private String ApprovalCode;
    private String idWallet;
}
