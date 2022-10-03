package com.example.pmswebportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.example.pmswebportal.model.Employee;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

        /**
         * Check authenticated
         * 
         * @param loginId
         * @return
         */
        @Query("select empl from Employee empl"
                        + " where empl.fldEmpLoginID = :loginId"
                        + " and empl.fldEmpStatus = 'Active'")
        Employee checkAuthenticated(@Param("loginId") String loginId);

        /**
         * Get Employee by EmpNo(key)
         * 
         * @param fldEmpNo
         * @return
         */
        Optional<Employee> findByFldEmpNo(String fldEmpNo);

}
