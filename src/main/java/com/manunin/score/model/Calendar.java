package com.manunin.score.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "calendars")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String description;

    @Column(columnDefinition = "jsonb")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String data;

//    {
//        "calendar_name": "Основной календарь",
//            "dates": [
//        {
//            "date": "2023-09-10",
//                "day_type": "Рабочий день",
//                "work_hours": {
//            "start_time": "09:00",
//                    "end_time": "17:00"
//        },
//            "non_work_hours": [
//            {
//                "start_time": "12:00",
//                    "end_time": "13:00"
//            }
//            ]
//        },
//        {
//            "date": "2023-09-11",
//                "day_type": "Выходной"
//        },
//        {
//            "date": "2023-09-12",
//                "day_type": "Рабочий день",
//                "work_hours": {
//            "start_time": "08:00",
//                    "end_time": "16:30"
//        }
//        }
//    ]
//    }


    private boolean isSystem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
