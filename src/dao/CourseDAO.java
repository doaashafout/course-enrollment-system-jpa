package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import models.Course;

public class CourseDAO {

    public List<Integer> getAllCourseids() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.createQuery("select c.courseId from Course c", Integer.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Course findById(int course_id) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            Course c = em.find(Course.class, course_id);
            return c;
        } finally {
            em.close();
        }
    }
}
