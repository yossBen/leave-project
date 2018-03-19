package com.leave.service.impl;

import com.leave.dao.DayOffDAO;
import com.leave.entity.DayOff;
import com.leave.entity.User;
import com.leave.service.DayOffService;
import com.leave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DayOffServiceImpl implements DayOffService {
    @Autowired
    private DayOffDAO dayOffDAO;
    @Autowired
    private UserService userService;

    @Override
    public List<DayOff> findByDate(Date beginDate, Date endDate, Long userId) {
        return dayOffDAO.findByDate(beginDate, endDate, userId);
    }

    @Override
    public void create(Date date, Long userId) {
        DayOff dayOff = new DayOff(date);
        User user = userService.laod(userId);
        dayOff.setAgenda(user.getAgenda());
        dayOffDAO.save(dayOff);
    }

    @Override
    public void delete(Date date, Long userId) {
//        dayOffDAO.deleteByDate(date, userId);
        List<DayOff> days = dayOffDAO.findByDate(date, null, userId);
        for (DayOff dayOff : days) {
            dayOffDAO.delete(dayOff);
        }
    }
}