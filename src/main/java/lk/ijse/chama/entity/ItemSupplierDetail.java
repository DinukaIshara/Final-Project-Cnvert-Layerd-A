package lk.ijse.chama.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemSupplierDetail {
    private String itemId;
    private String supId;
    private int qty;
    private double unitPrice;
}
