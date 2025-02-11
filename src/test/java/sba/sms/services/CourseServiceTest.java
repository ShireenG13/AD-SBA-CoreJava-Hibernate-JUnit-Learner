package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {
    @Mock
    CourseI courseI;

    @InjectMocks
    private CourseService courseService;

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
        sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        courseService = new CourseService();
    }

    @AfterEach
    public void tearDown(){
        if(transaction != null){
            transaction.rollback();
        }
        if(session != null){
            session.close();
        }
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateCourse(){
        Course course = new Course();
        course.setName("Java Programming");
        course.setInstructor("Sanaria Darboe");
        courseService.createCourse(course);

        //Course retrievedCourse = courseService.getCourseById(course.getCourseId());
        assertNotNull(course, "Course should be created successfully.");
        assertEquals("Java Programming", course.getName());
        assertEquals("Sanaria Darboe", course.getInstructor());
    }

    @Test
    public void testGetCourseById(){
        Course course = new Course();
        course.setName("JUnit Testing");
        course.setInstructor("Jennifer Alejo");
        courseService.createCourse(course);

        Course retrievedCourse = courseService.getCourseById(course.getCourseId());
        assertNotNull(retrievedCourse, "Course should exist in database.");
        assertEquals(course.getCourseId(), retrievedCourse.getCourseId());
        assertEquals("JUnit Testing", retrievedCourse.getName());
        assertEquals("Jennifer Alejo", retrievedCourse.getInstructor());
    }

    @Test
    public void testGetAllCourses(){
        Course course1 = new Course();
        course1.setName("Object Oriented Programming");
        course1.setInstructor("Charles Dickens");
        courseService.createCourse(course1);

        Course course2 = new Course();
        course2.setName("Object Oriented Programming");
        course2.setInstructor("Charles Dickens");
        courseService.createCourse(course2);
        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses, "Course list should not be null.");
        assertFalse(courses.isEmpty(), "Course list should not be empty.");
        assertTrue(courses.size() >= 2, "At least two courses should be present.");
    }

}
