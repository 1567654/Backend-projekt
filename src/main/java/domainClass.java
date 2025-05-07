import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class domainClass {
    @Id
    @GeneratedValue
    private int id;

}
