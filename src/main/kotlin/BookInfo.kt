import javax.persistence.Embeddable


@Embeddable
class BookInfo(
    var synopsis: String,
    var avgHoursToRead: Int
)