package com.tek_up.elearning.services;

import com.tek_up.elearning.dao.BookingRepository;
import com.tek_up.elearning.entities.CoursBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<CoursBooking> getAllCategories() {
        return bookingRepository.findAll();
    }

    public CoursBooking getCategoryById(Long id) {
        return bookingRepository.getReferenceById(id);
    }

    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }
}
