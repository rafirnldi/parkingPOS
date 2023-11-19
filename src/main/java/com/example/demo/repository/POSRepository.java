package com.example.demo.repository;


import com.example.demo.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
public interface POSRepository extends JpaRepository<Tickets, String> {

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO tickets " +
                    "(plat, kendaraan, entry)"+
                    "VALUES (:plat, :kendaraan, :entry)",
            nativeQuery = true
    )
    Optional<?> checkIn (String plat, String kendaraan, LocalDateTime entry);

    @Query(
            value = "SELECT * FROM tickets " +
            "WHERE plat =:plat",
            nativeQuery = true
    )
    Tickets queryTicketByPlate (String plat);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM tickets WHERE plat =:plat",
            nativeQuery = true
    )
    Optional<?> deleteTicket(String plat);
}
