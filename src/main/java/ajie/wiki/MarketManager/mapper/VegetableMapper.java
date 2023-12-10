package ajie.wiki.MarketManager.mapper;

import ajie.wiki.MarketManager.entity.sql.Vegetable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VegetableMapper {
    List<Vegetable> getVegetables(Map<String, Object> params);
    int insertVegetable(Vegetable vegetable);

    int deleteVegetable(Integer vegetableId);

    Integer getVegetableCount(Map<String, Object> params);
}
