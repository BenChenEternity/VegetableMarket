package ajie.wiki.MarketManager.entity.sql;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Vegetable {
    private Integer id;
    private String name;
    private String description;
    private String unit;
    private LocalDate productionDate;
    private Integer shelfLife;
    private LocalDate shelfDate;
    private BigDecimal price;
    private Integer stock;
    private String supplier;
    private BigDecimal sale;
}
