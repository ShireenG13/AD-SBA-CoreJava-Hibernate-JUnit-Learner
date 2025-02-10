/*package sba.sms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.models.Course;
import sba.sms.models.Student;

import java.util.List;

public class CourseImpl implements CourseI {

    private final SessionFactory sessionFactory;

    public CourseImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Course", Course.class).getResultList();
        }
    }

    public void createCourse(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
        }
    }

    public Course getCourseById(int courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        }
    }


}
*/