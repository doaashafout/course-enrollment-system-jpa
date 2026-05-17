package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import models.Student;

public class StudentDAO {

    public List<Integer> getAllStudentsids() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            return em.createQuery("select s.studentId from Student s", Integer.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Student findById(int student_id) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            Student s = em.find(Student.class, student_id);
            return s;
        } finally {
            em.close();
        }
    }
}
