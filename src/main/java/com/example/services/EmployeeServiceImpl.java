package com.example.services;

import com.example.constants.image.ImageContainerType;
import com.example.dao.employee.IEmployeeDAO;
import com.example.domain.employee.PlaceEmployee;
import com.example.pojo.dto.EmployeeDTO;
import com.example.services.imageservice.ImageService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 29.11.2015.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private ImageService imageService;
    private IEmployeeDAO eDao;

    @Override
    public PlaceEmployee registerEmployee(EmployeeDTO dto) {
        PlaceEmployee employee = new PlaceEmployee(dto);
        employee = eDao.registerEmployee(employee);
        try {
            imageService.upload(dto.getPhoto(), employee, ImageContainerType.PLACE_EMPLOYEE);
        } catch (BadHttpRequest badHttpRequest) {
            badHttpRequest.printStackTrace();
        }
        return employee;
    }

    /*
    @Override
    public List<EmployeeTimePeriod> findEmployeeFreeTime(UserOrders order) {
        ZoneOffset offset = ZoneOffset.ofHours(order.getPlace().getTimeOffset());
        PlaceEmployee employee = order.getEmployee();
        LocalDateTime n = LocalDateTime.now(offset);
        List<EmployeeTimePeriod> employeeTimePeriods;
        List<UserOrders> employeeTodayOrders = dao.getEmployeeTodayOrders(employee, n.toLocalDate());
        do {
            List<EmployeeTimePeriod> todayBreaks = unitePausesAndBreaks(employee, n, employeeTodayOrders);
            employeeTimePeriods = findEmployeeTodayFreeTime(todayBreaks, order, employee);
            if (employeeTimePeriods.isEmpty()) n.plusDays(1);
        }
        while (employeeTimePeriods.isEmpty());
        return employeeTimePeriods;
    }
    private List<EmployeeTimePeriod> findEmployeeTodayFreeTime(List<EmployeeTimePeriod> todayBreaks,
                                                               UserOrders order,
                                                               PlaceEmployee employee) {
        int lastPossibleTime = Inemployee.getStarts();
        List<EmployeeTimePeriod> timePeriods = new ArrayList<>();

        for (EmployeeTimePeriod breakPeriod : todayBreaks) {
            long menuTottalTime = getMenuTotalDurationMinutes(order);
            LocalTime accomplishmentTime = lastPossibleTime.plusMinutes(menuTottalTime);
            LocalTime breakStart = breakPeriod.getStarts();
            while (accomplishmentTime.compareTo(breakStart) >= 0) {
                LocalTime fromStartToBreak = lastPossibleTime
                        .plusMinutes(Duration.between(lastPossibleTime, breakStart).toMinutes());

                timePeriods.add(
                        new EmployeeTimePeriod(lastPossibleTime, fromStartToBreak.minusMinutes(menuTottalTime)));
            }
        }
        return timePeriods;
    }
        private List<EmployeeTimePeriod> unitePausesAndBreaks(PlaceEmployee employee, LocalDateTime n, List<UserOrders> orders) {
            List<EmployeePauses> employeeTodayPauses = dao.getEmployeeTodayPauses(employee, n);
            List<EmployeeBreaks> employeeBreaks = employee.getBreaks();
            List<EmployeeTimePeriod> durations = new ArrayList<>(employeeBreaks.size() + employeeTodayPauses.size());

            for (EmployeeBreaks b : employeeBreaks) {
                durations.add(new EmployeeTimePeriod(b.getStarts(), b.getEnds()));
            }

            for (EmployeePauses pause : employeeTodayPauses) {
                durations.add(new EmployeeTimePeriod(pause.getStart(), pause.getEnds()));
            }

            for (UserOrders o : orders) {
                LocalTime oT = o.getStartTime().toLocalTime();
                durations.add(new EmployeeTimePeriod(oT, oT.plusMinutes(getMenuTotalDurationMinutes(o))));
            }
            return durations;
        }

    private long getMenuTotalDurationMinutes(UserOrders o) {
        long menuTotalDurationMinutes = o.getMenu().getDurationMinutes();
        for (PlaceMenuOptionalService s : o.getServices())
            menuTotalDurationMinutes += s.getDurationMinutes();
        return menuTotalDurationMinutes;
    }
*/

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    public void seteDao(IEmployeeDAO eDao) {
        this.eDao = eDao;
    }
}
