package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public StudentService() {}

    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).getResultList();
        }
    }

    @Override
    public void createStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(student);
            tx.commit();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, email);
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Student student = (Student) session.get(Student.class, email);
        if (student == null) {
            System.out.println("Student not found with email " + email);
        }
        // Fetch course by courseId
        Course course = (Course) session.get(Course.class, courseId);
        if (course == null) {
            System.out.println("Course not found with ID " + courseId);
            tx.commit();
            session.close();
            return;
        }
        if(student.getCourses().contains(course)) {
            System.out.println("Student is already enrolled to the course");
        }
        if (student != null && student.getEmail().equals(email) && !student.getCourses().contains(email)) {
            student.getCourses().add(course);
            session.persist(student);
            System.out.println("Student is valid and added to the course");
        }
        tx.commit();
    }


    /*@Override
    public void registerStudentToCourse(String email, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            Student student = session.get(Student.class, email);
            if (student != null && !student.getCourses().contains(course)) {
                student.getCourses().add(course);
                session.merge(student);
            }
            transaction.commit();
        }

    }*/

    /*@Override
    public boolean validateStudent(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery(
                    "SELECT s FROM Student s JOIN FETCH s.courses WHERE s.email = :email AND s.password = :password",
                    Student.class);
            query.setParameter("email", email);
            query.setParameter("password", password);

            Student student = query.uniqueResult();

            // If a matching student is found, return true

            if(student != null) {
                return true;
            } else{
                return false;
            }

        }
    } */

    public boolean validateStudent(String email, String password) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Student student;
        try {
            student = (Student) session.get(Student.class, email);

            if (student == null) {
                System.out.println("Student not found with email " + email);
                return false;
            }

            if (email == null || password == null) {
                System.out.println("Email or password can not be null!");
                return false;

            }
            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                System.out.println("Student is valid and login successful");
                return true;
            } else
                System.out.println("Student is not valid and login failed and password does not match");
            return false;
        } catch (Exception e) {
            System.out.println("Error while validating student " + e.getMessage());
            return false;
        } finally {
            tx.commit();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try  (Session session = sessionFactory.openSession()) {
            Student student = session.createQuery(
                            "SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResult();

            return student != null ? new ArrayList<>(student.getCourses()) : Collections.emptyList();
        }

    }
}












    /*SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    //Repositories

    private final StudentImpl studentRepository;

    public StudentService() {
        studentRepository = new StudentImpl();

    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public void createStudent(Student student) {
            studentRepository.createStudent(student);
    }

    public Student getStudentByEmail(String email) {
            return studentRepository.getStudentByEmail(email);
    }

    public void registerStudentToCourse(String email, int courseId) {
        studentRepository.registerStudentToCourse(email, courseId);
    }

    public boolean validateStudent(String email, String password) {
        return studentRepository.validateStudent(email, password);
    }

    public List<Course> getStudentCourses(String email) {
        return studentRepository.getStudentCourses(email);
        }



    }*/
