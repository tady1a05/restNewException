package com.toby.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toby.exception.StudentNotFound;
import com.toby.model.*;

@RestController
@RequestMapping("/api")
public class RestApi {
	private ArrayList<Student> studentList = null;
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFound exc) {
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
		StudentErrorResponse error = new StudentErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@PostConstruct
	public void loadData() {
		studentList = new ArrayList<Student>();

		Student a = new Student("Toby", "Cheung");
		Student b = new Student("Peter", "Cheung");
		Student c = new Student("Kitty", "Wong");

		studentList.add(a);
		studentList.add(b);
		studentList.add(c);
	}

	@GetMapping("/students")
	public ArrayList<Student> test() {
		return studentList;
	}

	@GetMapping("/students/{studentId}")
	public Student test(@PathVariable int studentId) {
		if(studentId < 0 || studentId >= studentList.size()) {
			throw new StudentNotFound("student id " + studentId + " not found.");
		}
		return studentList.get(studentId);
	}
}
