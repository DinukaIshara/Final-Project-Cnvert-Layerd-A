package lk.ijse.chama.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Location {
    private String place;
    private Double latitude;
    private Double longitude;

}
