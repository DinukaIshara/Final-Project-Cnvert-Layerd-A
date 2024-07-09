package lk.ijse.chama.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Supplier {
    private String supId;
    private String companyName;
    private String personName;
    private String tel;
    private String location;
    private String email;
}
