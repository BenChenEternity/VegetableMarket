package ajie.wiki.MarketManager.entity.post;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VegetableQueryParams {
    private String name;
    private Date productionDate;
    private Integer shelfLife;
    private Date shelfDate;
    private BigDecimal price;
    private Integer stock;
    private String supplier;
    private BigDecimal sale;
}
