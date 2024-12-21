package com.tek_up.elearning.Controllers;


import com.tek_up.elearning.entities.Category;
import com.tek_up.elearning.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

}
