package com.tek_up.elearning.services;

import com.tek_up.elearning.dao.BookingRepository;
import com.tek_up.elearning.dao.CoursRepository;
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
        Cours coursToCreate = new Cours();
        coursToCreate.setName(cours.getName());
        coursToCreate.setDescription(cours.getDescription());
        coursToCreate.setCategory(cours.getCategory());
        coursToCreate.setOwner(userService.getUserById(ownerId));
        coursToCreate.setCreatedAt(new Date());

        return coursRepository.save(cours);
    }

    public Cours updateCours(Cours cours, Long id) {
        if(!coursRepository.existsById(id)) {
            throw new IllegalArgumentException("Cours id not found");
        }
        Cours coursToUpdate = coursRepository.getReferenceById(id);

        coursToUpdate.setName(cours.getName());
        coursToUpdate.setDescription(cours.getDescription());
        coursToUpdate.setCategory(cours.getCategory());

        return coursRepository.save(coursToUpdate);
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
}
