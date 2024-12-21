package com.tek_up.elearning.services;

import com.tek_up.elearning.dao.BookingRepository;
import com.tek_up.elearning.dao.CoursRepository;
import com.tek_up.elearning.dao.UserRepository;
import com.tek_up.elearning.entities.Cours;
import com.tek_up.elearning.entities.CoursBooking;
import com.tek_up.elearning.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CoursService {
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Cours> getAllCourses() {
        return coursRepository.findAll();
    }

    public Cours getCoursById(Long id) {
        return coursRepository.getReferenceById(id);
    }

    public List<Cours> getCoursesByKeyword(String keyword) {
        return coursRepository.getCoursesByKeyword(keyword);
    }

    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }

    public Cours createCours(Long ownerId, Cours cours) {
        try{
            Cours coursToCreate = new Cours();
            coursToCreate.setName(cours.getName());
            coursToCreate.setDescription(cours.getDescription());
            coursToCreate.setCategory(cours.getCategory());
            coursToCreate.setOwner(userService.getUserById(ownerId));
            coursToCreate.setCreatedAt(new Date());
            return coursRepository.save(cours);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    public Cours updateCours(Cours cours, Long ownerId, Long coursId) {
        try {
            if(!coursRepository.existsById(coursId) && cours.getOwner().getId().equals(ownerId)) {
                throw new IllegalArgumentException("Cours id not found");
            }
            Cours coursToUpdate = coursRepository.getReferenceById(coursId);

            coursToUpdate.setName(cours.getName());
            coursToUpdate.setDescription(cours.getDescription());
            coursToUpdate.setCategory(cours.getCategory());

            return coursRepository.save(coursToUpdate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    public void deleteTeacherCours(Long coursId, Long teacherId) {
        try {
            Cours cours = getCoursById(coursId);
            if (cours.getOwner() != null && cours.getOwner().getId().equals(teacherId)) {
                coursRepository.deleteById(coursId);
            } else {
                throw new IllegalAccessException("Teacher with ID " + teacherId + " is not the owner of the course.");
            }
        } catch (Exception e) {
            System.out.println("Error while deleting the course: " + e.getMessage());
        }
    }

    public List<Cours> getAllTeacherCourses(Long ownerId) {
        try {
            User owner = userService.getUserById(ownerId);
            if (owner != null) {
                return coursRepository.findByOwner(owner);
            } else {
                throw new IllegalAccessException("Error finding the owner.");
            }
        } catch (Exception e) {
           throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    public CoursBooking enrollCours(Long userId, Long coursId) {
        try{
            if(!userRepository.existsById(userId)){
                throw new IllegalArgumentException("User not found");
            }
            if(!coursRepository.existsById(coursId)){
                System.out.println("//////////////// " + coursId);
                throw new IllegalArgumentException("Cours not found");
            }
            Cours cours = getCoursById(coursId);
            User user = userService.getUserById(userId);

            CoursBooking coursBooking = new CoursBooking();
            coursBooking.setUser(user);
            coursBooking.setCours(cours);
            coursBooking.setCreationDate(new Date());

            bookingRepository.save(coursBooking);
            return coursBooking;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    public CoursBooking unenrollCours(Long studentId, Long enrollId) {
        try{
            CoursBooking enroll = bookingRepository.getReferenceById(enrollId);
            if (enroll.getUser().getId().equals(studentId)) {
                bookingRepository.deleteById(enrollId);
            }
            return enroll;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    public List<Cours> generateCourses(Long ownerId, List<Cours> courses) {
        try{
            if (!userRepository.existsById(ownerId)){
                throw new IllegalArgumentException("User not found");
            }
            courses.forEach(cours -> {
                createCours(ownerId, cours);
            });
            return courses;
        }catch (Exception e){
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }
}
