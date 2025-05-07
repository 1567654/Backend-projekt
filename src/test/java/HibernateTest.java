import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public static EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("databaseConfig");

public static void main(String[] args) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    tx.commit();
    em.close();
}
