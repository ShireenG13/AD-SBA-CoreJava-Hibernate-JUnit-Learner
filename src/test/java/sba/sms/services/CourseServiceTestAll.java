package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sba.sms.models.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ensures tests run in order

public class CourseServiceTestAll {

        private CourseService courseService;
        private SessionFactory sessionFactory;
        private Session session;
        private Transaction transaction;

        private static Course course; // Shared patient instance
        private static int courseId; // Shared patient ID

        @BeforeAll
        public void setUp() {
            sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            courseService = new CourseService();
        }

        @AfterAll
        public void tearDown() {
            if (transaction != null) {
                transaction.rollback(); // Ensures test data isn't persisted
            }
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }

        @Test
        @Order(1)
        public void testCreateCourse() {
            // Arrange
            course = new Course();

            course.setName("JavaScript Programming");
            course.setInstructor("Tiara Morton");



            // Act
            courseService.createCourse(course);
            courseId = course.getCourseId(); // Store for future tests

            // Assert
            assertTrue(courseId > 0, "Course should be created with a valid ID.");
        }

        @Test
        @Order(2)
        public void testGetCourseById() {
            // Act
            Course retrievedCourse = courseService.getCourseById(courseId);

            // Assert
            assertNotNull(retrievedCourse, "Course should exist in the database.");
            assertEquals("JavaScript Programming", retrievedCourse.getName());
        }

    @Test
    @Order(3)
    public void testGetAllCourses() {
        // Arrange
        Course course1 = new Course();
        courseService.createCourse(course1);

        Course course2 = new Course();
        course2.setName("C++ Programming");
        course2.setInstructor("Miller");
        courseService.createCourse(course2);

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertNotNull(courses, "Courses list should not be null.");
        assertFalse(courses.isEmpty(), "Courses list should not be empty.");
        assertTrue(courses.size() >= 2, "At least two patients should exist.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Michael", "Emma", "James", "Olivia"})
    @Order(4)
    public void testCreateCourseWithDifferentInstructors(String instructor) {
        // Arrange
        Course course = new Course();
        course.setName("Hugh Lewis");
        course.setInstructor(instructor);

        // Act
        courseService.createCourse(course);
        Course retrievedCourse = courseService.getCourseById(course.getCourseId());

        // Assert
        assertNotNull(retrievedCourse, "Course should be created.");
        assertEquals(instructor, retrievedCourse.getInstructor(), "Instructor name should match.");
    }
}
