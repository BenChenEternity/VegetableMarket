package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.entity.sql.Vegetable;
import ajie.wiki.MarketManager.mapper.VegetableMapper;
import ajie.wiki.MarketManager.service.QueryVegetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class QueryVegetableServiceImpl implements QueryVegetableService {
    private final VegetableMapper vegetableMapper;
    private static final Logger logger = LoggerFactory.getLogger(QueryVegetableServiceImpl.class);
    @Autowired
    public QueryVegetableServiceImpl(VegetableMapper vegetableMapper) {
        this.vegetableMapper = vegetableMapper;
    }

    @Override
    public HashMap<String, Object> get(
            String name,
            LocalDate productionDate,
            Integer shelfLife,
            LocalDate shelfDate,
            BigDecimal price,
            Integer stock,
            String supplier,
            BigDecimal sale,
            Integer currentPage,
            Integer pageSize) {
        int offset = (currentPage - 1) * pageSize;
        Map<String, Object> queryObj = new HashMap<>();
        if (name != null && name.length() == 0) {
            name = null;
        }
        if (supplier != null && supplier.length() == 0) {
            name = null;
        }
        logger.debug(
                "\n↓↓↓↓↓↓↓↓↓↓↓↓\n" +
                        "Query parameters:\nName:  " +
                        name + "\nproductionDate:  " +
                        productionDate + "\nshelfLife:  " +
                        shelfLife + "\nshelfDate:  " +
                        shelfDate + "\nprice:  " +
                        price + "\nstock:  " +
                        stock + "\nsupplier:  " +
                        supplier + "\nsale:  " +
                        sale + "\ncurrentPage:  " +
                        currentPage + "\npageSize:  " +
                        pageSize +
                        "\n↑↑↑↑↑↑↑↑↑↑↑↑"
        );
        queryObj.put("name", name);
        queryObj.put("productionDate", productionDate);
        queryObj.put("shelfLife", shelfLife);
        queryObj.put("shelfDate", shelfDate);
        queryObj.put("price", price);
        queryObj.put("stock", stock);
        queryObj.put("supplier", supplier);
        queryObj.put("sale", sale);
        queryObj.put("offset", offset);
        queryObj.put("pageSize", pageSize);
        List<Vegetable> list = vegetableMapper.getVegetables(queryObj);
        Integer total = vegetableMapper.getVegetableCount(queryObj);
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}
