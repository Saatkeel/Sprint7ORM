import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Author(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
) {
    override fun toString(): String {
        return name
    }
}