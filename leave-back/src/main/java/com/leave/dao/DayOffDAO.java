package com.leave.dao;

import com.leave.entity.DayOff;

import java.util.Date;
import java.util.List;

public interface DayOffDAO extends PersistentDAO<Long, DayOff> {
	List<DayOff> findByDate(Date beginDate, Date endDate, Long userId);

	void deleteByDate(Date beginDate, Long userId);
}