package com.manunin.score.service;

import com.manunin.score.model.Schedule;
import com.manunin.score.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

        private final ScheduleRepository scheduleRepository;

        public ScheduleService(final ScheduleRepository groupRepository) {
            this.scheduleRepository = groupRepository;
        }

        public List<Schedule> getSchedules() {
            return scheduleRepository.findAll();
        }
}
