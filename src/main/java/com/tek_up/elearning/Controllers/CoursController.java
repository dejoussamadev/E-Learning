package com.tek_up.elearning.Controllers;

import com.tek_up.elearning.entities.Cours;
import com.tek_up.elearning.entities.CoursBooking;
import com.tek_up.elearning.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/courses")
public class CoursController {

    @Autowired
    CoursService coursService;
}
