package com.sup1x.api.repository;

import com.sup1x.api.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("pinRepository")
public interface PinRepository extends JpaRepository<Pin, Long> {

//    @Query("SELECT p.pin FROM Pin p WHERE p.pin = :pinValue")
//    Pin findByValue(@Param("pinValue") int pin);

        Pin findByPin(int pin);
}
