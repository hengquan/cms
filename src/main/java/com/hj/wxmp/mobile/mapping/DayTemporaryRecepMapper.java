package com.hj.wxmp.mobile.mapping;

import java.util.List;

import com.hj.wxmp.mobile.entity.DayTemporaryRecep;

public interface DayTemporaryRecepMapper {
    int deleteByPrimaryKey(String id);

    int insert(DayTemporaryRecep record);

    int insertSelective(DayTemporaryRecep record);

    DayTemporaryRecep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DayTemporaryRecep record);

    int updateByPrimaryKey(DayTemporaryRecep record);

	List<DayTemporaryRecep> selectByProj(DayTemporaryRecep dayTemporaryRecep);

	List<DayTemporaryRecep> selectByUser(DayTemporaryRecep dayTemporaryRecep);

	List<DayTemporaryRecep> selectByCust(DayTemporaryRecep dayTemporaryRecep);

	List<DayTemporaryRecep> selectByProjAndUser(DayTemporaryRecep dayTemporaryRecep);

	List<DayTemporaryRecep> selectByprojAndCust(DayTemporaryRecep dayTemporaryRecep);

	List<DayTemporaryRecep> selectByprojAndUserAndCust(DayTemporaryRecep dayTemporaryRecep);
}