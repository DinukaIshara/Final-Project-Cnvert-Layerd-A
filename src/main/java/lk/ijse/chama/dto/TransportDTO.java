package lk.ijse.chama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TransportDTO {
    private String trId;
    private String vehicalNo;
    private String driverName;
    private String location;
    private double cost;

}
