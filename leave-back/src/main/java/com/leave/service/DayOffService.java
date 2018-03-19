package com.leave.service;

import com.leave.entity.DayOff;
import java.util.Date;
import java.util.List;

public interface DayOffService {

    List<DayOff> findByDate(Date beginDate, Date endDate, Long userId);

    void create(Date date, Long userId);

    void delete(Date date, Long userId);
}