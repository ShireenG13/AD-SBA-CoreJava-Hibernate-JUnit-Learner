/*package sba.sms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.models.Course;
import sba.sms.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentImpl implements StudentI {
    private final SessionFactory sessionFactory;

    public StudentImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).getResultList();
        }
    }

    public void createStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }
    }

    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, email);
        }
    }

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

    }

    public boolean validateStudent(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            return false;
        }
    }

    public List<Course> getStudentCourses(String email) {
        try (Session session = sessionFactory.openSession()) {
            List<Course> courses1 = new ArrayList<>();
            courses1 = List<Course> session.createQuery("SELECT s.courses FROM Student s LEFT JOIN FETCH s.courses WHERE s.email = :email", Student.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return courses1;
        }

    }

}

*/
