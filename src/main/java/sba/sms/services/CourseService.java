package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public CourseService() {}

    @Override
    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Course", Course.class).getResultList();
        }
    }
    @Override
    public void createCourse(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
        }
    }
    @Override
    public Course getCourseById(int courseId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        }
    }
}






/*public class CourseService {
    private final CourseImpl courseRepository;

    public CourseService(CourseImpl courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void createCourse(Course course) {
        courseRepository.createCourse(course);
    }

    public Course getCourseById(int courseId){
        return courseRepository.getCourseById(courseId);
    }

    public List<Course> getAllCourses() {

    }



}*/
