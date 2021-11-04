import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Book(
    @Id
    @GeneratedValue
    var id: Long = 0,
    @Column(name = "book_name")
    @NaturalId
    var name: String,

    @ManyToMany(
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH],
        fetch = FetchType.EAGER
    )
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    var author: MutableList<Author>,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var isbn: Isbn,

    var bookInfo: BookInfo,
    @CreationTimestamp
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedTime: LocalDateTime? = null
) {
    override fun toString(): String {
        return "Book(id=$id, name='$name', ISBN='$isbn', Authors=$author)\n"
    }
}