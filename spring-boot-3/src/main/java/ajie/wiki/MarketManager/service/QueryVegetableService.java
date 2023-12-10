package ajie.wiki.MarketManager.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public interface QueryVegetableService {
    HashMap<String, Object> get(String name, LocalDate productionDate, Integer shelfLife, LocalDate shelfDate, BigDecimal price, Integer stock, String supplier, BigDecimal sale, Integer currentPage,
                                Integer pageSize);
}
