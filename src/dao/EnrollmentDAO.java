package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import models.Enrollment;

public class EnrollmentDAO {

    public List<Enrollment> findAll() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.createQuery("select e from Enrollment e", Enrollment.class).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean isDuplicate(Enrollment e) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            Long count = em.createQuery(
                    "SELECT COUNT(e) FROM Enrollment e WHERE e.student.studentId = :sid AND e.course.courseId = :cid",
                    Long.class)
                    .setParameter("sid", e.getStudent().getStudentId())
                    .setParameter("cid", e.getCourse().getCourseId())
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public boolean insertOne(Enrollment e) {
        if (isDuplicate(e)) {
            return false;
        }
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return true;
        } catch (Exception c) {
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateOne(Enrollment e) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.merge(e);
            em.getTransaction().commit();
            return true;
        } catch (Exception c) {
            return false;
        } finally {
            em.close();
        }
    }

    public boolean deleteOne(Enrollment e) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Enrollment managedEnrollment = em.merge(e);
            em.remove(managedEnrollment);
            em.getTransaction().commit();
            return true;
        } catch (Exception c) {
            return false;
        } finally {
            em.close();
        }
    }

    public List<Enrollment> findByStudentId(int studentId) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.createQuery(
                    "SELECT e FROM Enrollment e WHERE e.student.studentId = :sid", Enrollment.class)
                    .setParameter("sid", studentId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
