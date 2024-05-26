package com.example.Spring.Security.implem;

import com.example.Spring.Security.models.Course;
import com.example.Spring.Security.repositories.CourseRepository;
import com.example.Spring.Security.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseImplem implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> filterPerWordInDescription(String word) {
        return courseRepository.findByDescriptionContaining(word);
    }

}
