package nttdata.bootcamp.quarkus.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletRedis {

    private String idWallet;

    private String approvalCode;
    private Date expirationDate;
}
