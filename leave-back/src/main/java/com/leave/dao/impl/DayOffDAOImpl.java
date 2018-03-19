package com.leave.dao.impl;

import com.leave.dao.DayOffDAO;
import com.leave.entity.DayOff;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class DayOffDAOImpl extends PersistentDAOImpl<Long, DayOff> implements DayOffDAO {
    @Override
    public List<DayOff> findByDate(Date beginDate, Date endDate, Long userId) {
        if (endDate != null) {
            return (List<DayOff>) hibernateTemplate
                            .findByNamedQueryAndNamedParam(DayOff.QUERY_FIND_BETWEEN_TWO_DATE, new String[] { "userId", "beginDate", "endDate" }, new Object[] { userId, beginDate, endDate });
        }
        else {
            return (List<DayOff>) hibernateTemplate.findByNamedQueryAndNamedParam(DayOff.QUERY_FIND_BT_DATE, new String[] { "userId", "beginDate" }, new Object[] { userId, beginDate });
        }
    }

    @Override
    @Transactional
    public void deleteByDate(Date beginDate, Long userId) {
//        hibernateTemplate.bulkUpdate("DELETE DayOff day WHERE day.agenda.user.id = :userId and day.date = :beginDate", new String[] { "userId", "beginDate" }, new Object[] { userId, beginDate });
    }
}