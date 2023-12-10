package ajie.wiki.MarketManager.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LevelMapper {
    Integer getUserLevel();
    Integer getVIPLevel();
    Integer getManagerLevel();
    Integer getOwnerLevel();
}
