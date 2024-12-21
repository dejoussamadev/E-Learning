package com.tek_up.elearning.RestController;

import com.tek_up.elearning.dao.UserRepository;
import com.tek_up.elearning.entities.Cours;
import com.tek_up.elearning.entities.CoursBooking;
import com.tek_up.elearning.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CoursRestController {

    @Autowired
    CoursService coursService;



    // ADMIN
    @DeleteMapping("admin/deleteCours")
    public void deleteCours(@RequestParam(name = "cours_id") Long coursId){
        coursService.deleteCours(coursId);
    }

    // TEACHER
    @DeleteMapping("teacher/deleteCours")
    public void deleteTeacherCours(@RequestParam(name = "cours_id") Long coursId, @RequestParam(name = "teacher_id") Long teacherId){
        coursService.deleteTeacherCours(coursId,teacherId);
    }

    @PutMapping("teacher/updateCours")
    public Cours updateCours(@RequestBody Cours cours,@RequestParam(name = "teacher_id") Long teacherId, @RequestParam(name = "cours_id") Long coursId){
        return coursService.updateCours(cours, teacherId, coursId);
    }

    @PostMapping("teacher/createCours")
    public Cours createCours(@RequestParam(name = "owner_id") Long ownerId, @RequestBody Cours cours){
        return coursService.createCours(ownerId, cours);
    }

    @GetMapping("teacher/getAllTeacherCourses")
    public List<Cours> getAllTeacherCourses(@RequestParam(name = "owner_id") Long ownerId){
        return coursService.getAllTeacherCourses(ownerId);
    }

    // STUDENT
    @PostMapping("student/enrollCours")
    public CoursBooking enrollCours(@RequestParam(name = "student_id") Long userId, @RequestParam(name = "cours_id") Long coursId){
        return coursService.enrollCours(userId, coursId);
    }

    @DeleteMapping("student/unenrollCours")
    public CoursBooking unenrollCours(@RequestParam(name = "studentId") Long studentId, @RequestParam(name = "enroll_id") Long enrollId){
        return coursService.unenrollCours(studentId, enrollId);
    }

    // USER
    @GetMapping("user/getAllCourses")
    public List<Cours> getAllCourses(){
        return coursService.getAllCourses();
    }

    @GetMapping("user/getCoursById")
    public Cours getCategoryById(@RequestParam(name = "cours_id") Long id){
        return coursService.getCoursById(id);
    }

    // ------------- GENERATION -------------
    @PostMapping("user/generateCourses")
    public List<Cours> generateCourses(@RequestParam(name = "owner_id") Long ownerId, @RequestBody List<Cours> courses){
        return coursService.generateCourses(ownerId, courses);
    }
}
