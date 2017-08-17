package com.dbbyte;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dbbyte.model.Employee;
import com.dbbyte.repository.EmployeeRepository;

@SpringBootApplication
public class SbOracleRestApplication implements CommandLineRunner {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource dataSource;

    @Autowired
    EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(SbOracleRestApplication.class, args);
	}
	
    @Transactional
    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

        System.out.println("\n1.findAll()...");
        for (Employee employee : employeeRepository.findAll()) {
            System.out.println(employee);
        }

        System.out.println("\n2.findByEmail(String email)...");
        for (Employee employee : employeeRepository.findByEmail("222@yahoo.com")) {
            System.out.println(employee);
        }

        System.out.println("\n3.findByDate(Date date)...");
        for (Employee employee : employeeRepository.findByDate(sdf.parse("2017-02-12"))) {
            System.out.println(employee);
        }

        // For Stream, need @Transactional
        System.out.println("\n4.findByEmailReturnStream(@Param(\"email\") String email)...");
        try (Stream<Employee> stream = employeeRepository.findByEmailReturnStream("333@yahoo.com")) {
            stream.forEach(x -> System.out.println(x));
        }

        System.out.println("Done!");

    }

}
