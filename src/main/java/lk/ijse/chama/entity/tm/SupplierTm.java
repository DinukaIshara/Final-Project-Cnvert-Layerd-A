package lk.ijse.chama.entity.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierTm {
    private String supId;
    private String companyName;
    private String personName;
    private String tel;
    private String location;
    private String email;
}
