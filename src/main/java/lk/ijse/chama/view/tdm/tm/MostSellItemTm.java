package lk.ijse.chama.view.tdm.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MostSellItemTm {
    private String itemId;
    private int orderCount;
    private int sumQty;
}
