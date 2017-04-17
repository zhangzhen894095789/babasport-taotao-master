package com.zhangzhen.core.dao.product;

import com.zhangzhen.core.bean.product.Type;
import com.zhangzhen.core.bean.product.TypeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeDao {
    int countByExample(TypeQuery example);

    int deleteByExample(TypeQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeQuery example);

    Type selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeQuery example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeQuery example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}