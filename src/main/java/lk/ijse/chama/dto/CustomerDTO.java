package lk.ijse.chama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO implements Serializable {
    private String custId;
    private String cName;
    private String cAddress;
    private String cNIC;
    private String contactNo;
    private String cEmail;

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "custId='" + custId + '\'' +
                ", cName='" + cName + '\'' +
                ", cAddress='" + cAddress + '\'' +
                ", cNIC='" + cNIC + '\'' +
                ", contactNo=" + contactNo +
                ", cEmail='" + cEmail + '\'' +
                '}';
    }
}
