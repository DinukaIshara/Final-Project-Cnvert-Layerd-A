package lk.ijse.chama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDTO {
    private String orderId;
    private String customerId;
    private String trId;
    private Date date;
    private String payment;

    private OrderDTO order;

    private List<OrderDetailDTO> odList;

    public OrderDTO(String orderId,String customerId,String trId,Date data, String payment){
        this.orderId = orderId;
        this.customerId = customerId;
        this.trId = trId;
        this.date = data;
        this.payment = payment;
    }
}
