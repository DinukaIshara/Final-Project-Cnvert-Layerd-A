package lk.ijse.chama.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PlaceOrder {
    private Order order;
    private List<OrderDetail> odList;
}
