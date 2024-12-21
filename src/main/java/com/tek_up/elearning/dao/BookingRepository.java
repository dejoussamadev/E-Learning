package com.tek_up.elearning.dao;

import com.tek_up.elearning.entities.CoursBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<CoursBooking, Long> {
}
