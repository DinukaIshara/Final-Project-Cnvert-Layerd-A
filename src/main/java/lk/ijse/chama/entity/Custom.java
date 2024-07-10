package lk.ijse.chama.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Custom {
    private String date;
    private int count;

    private String itemId;
    private int orderCount;
    private int sumQty;

    public Custom(String date,int count){
        this.date = date;
        this.count = count;
    }
    public Custom(String itemId, int orderCount,int sumQty){
        this.itemId = itemId;
        this.orderCount = orderCount;
        this.sumQty = sumQty;
    }
}
