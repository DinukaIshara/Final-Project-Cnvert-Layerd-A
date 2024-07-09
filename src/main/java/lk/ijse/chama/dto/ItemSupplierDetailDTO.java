package lk.ijse.chama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemSupplierDetailDTO {
    private String itemId;
    private String supId;
    private int qty;
    private double unitPrice;
}
