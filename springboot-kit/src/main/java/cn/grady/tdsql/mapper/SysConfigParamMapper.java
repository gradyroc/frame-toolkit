package cn.grady.tdsql.mapper;

import cn.grady.tdsql.domain.SysConfigParam;
import cn.grady.tdsql.domain.SysConfigParamCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysConfigParamMapper {
    int insert(SysConfigParam row);

    int insertSelective(SysConfigParam row);

    List<SysConfigParam> selectByExample(SysConfigParamCondition example);

    int updateByExampleSelective(@Param("row") SysConfigParam row, @Param("example") SysConfigParamCondition example);

    int updateByExample(@Param("row") SysConfigParam row, @Param("example") SysConfigParamCondition example);
}