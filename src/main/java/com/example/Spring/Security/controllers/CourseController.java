package com.example.Spring.Security.controllers;

import com.example.Spring.Security.enums.RoleEnum;
import com.example.Spring.Security.models.Course;
import com.example.Spring.Security.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course, Authentication authentication){
        if (!hasRole(authentication, RoleEnum.PROF.name())){
            return new ResponseEntity<>(
                    "Unautorized",
                    HttpStatus.UNAUTHORIZED
            );
        }
        return new ResponseEntity<>(
                courseService.createCourse(course),
                HttpStatus.CREATED
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()){
            return new ResponseEntity<>(
                    course.get(),
                    HttpStatus.OK
            );
        }else{
            return new ResponseEntity<>(
                    "Course not foound",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(){

        return new ResponseEntity<>(
                courseService.getAllCourses(),
                HttpStatus.OK
        );
    }

    @GetMapping("filter/{word}")
    public ResponseEntity<?> filterPerWordInDescription(@PathVariable String word){
        return new ResponseEntity<>(
                courseService.filterPerWordInDescription(word),
                HttpStatus.OK
        );
    }

    public boolean hasRole(Authentication authentication, String roleName){
        for (GrantedAuthority authority : authentication.getAuthorities()){
            if(authority.getAuthority().equals(roleName))
                return true;
        }
        return false;
    }
}
