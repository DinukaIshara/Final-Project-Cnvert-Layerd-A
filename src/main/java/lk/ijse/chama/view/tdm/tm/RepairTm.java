package lk.ijse.chama.view.tdm.tm;

import lk.ijse.chama.entity.Repair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairTm extends Repair {
    private String repId;
    private String itemName;
    private String description;
    private String custId;
    private double cost;
    private LocalDate reciveDate;
    private LocalDate reternDate;
}
