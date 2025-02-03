package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    // 根据菜品id查询套餐id
    List<Long> getSetMealDishByIds(List<Long> dishIds);
}
