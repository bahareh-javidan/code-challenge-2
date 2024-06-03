package com.apromore.challenge.repository;

import com.apromore.challenge.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.name = :name, s.age = :age WHERE s.id = :id")
    void updateStudentDetails(@Param("id") Long id, @Param("name") String name, @Param("age") Integer age);
}
