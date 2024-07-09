package lk.ijse.chama.view.tdm.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransportTm {
    private String trId;
    private String vehicalNo;
    private String driverName;
    private String location;
    private double cost;
}
