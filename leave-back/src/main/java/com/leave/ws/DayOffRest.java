package com.leave.ws;

import com.leave.entity.DayOff;
import com.leave.service.DayOffService;
import com.leave.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/api/dayOff")
public class DayOffRest {
    private final static Logger LOGGER = Logger.getLogger(DayOffRest.class.getName());
    @Autowired
    private DayOffService dayOffService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<DayOff> get(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                            @RequestAttribute(Constants.USER_ID_ATTRIBUTE) Long userId) {
        return dayOffService.findByDate(beginDate, endDate, userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestAttribute(Constants.USER_ID_ATTRIBUTE) Long userId) {
        dayOffService.create(date, userId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, @RequestAttribute(Constants.USER_ID_ATTRIBUTE) Long userId) {
        dayOffService.delete(date, userId);
    }
}