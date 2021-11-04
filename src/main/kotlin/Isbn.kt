import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Isbn(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var isbn: String
) {
    override fun toString(): String {
        return "isbn =$isbn"
    }
}