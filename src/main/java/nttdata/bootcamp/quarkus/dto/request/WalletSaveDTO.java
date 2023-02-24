package nttdata.bootcamp.quarkus.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletSaveDTO {

    private String clientId;
    private String password;
    private String debitCardId;
    private Integer pin;

    private String validationCode;

}
