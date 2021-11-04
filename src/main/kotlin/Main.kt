import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Book::class.java)
        .addAnnotatedClass(BookInfo::class.java)
        .addAnnotatedClass(Author::class.java)
        .addAnnotatedClass(Isbn::class.java)
        .buildSessionFactory()


    sessionFactory.use { sessionFactory ->
        val dao = BookDAO(sessionFactory)
        val author = Author(name = "Secret Author")
        val book1 = Book(
            name = "Война и мир",
            isbn = Isbn(isbn = "978-5-389-14702-7"),
            author = mutableListOf(Author(name = "Лев Николаевич Толстой"), author),
            bookInfo = BookInfo("война и мир", 80)
        )
        val book2 = Book(
            name = "Маленький принц",
            isbn = Isbn(isbn = "978-5-389-14702-8"),
            author = mutableListOf(Author(name = "Антуан де Сент-Экзюпери"), author),
            bookInfo = BookInfo("лил принц", 10)
        )

        dao.save(book1)

        dao.save(book2)

        val readBook = dao.find(book1.id)
        println(
            "Test read\n" +
                    "book: $readBook"
        )

        var book2Updated = book2
        book2Updated.isbn = Isbn(isbn = "88005553535")
        dao.update(book2Updated)

        dao.delete(book1)

        val allBooks = dao.findAll()
        println("все книги: $allBooks")
    }
}

class BookDAO(
    private val sessionFactory: SessionFactory
) {
    fun save(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(book)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Book? {
        val result: Book?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Book::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun find(name: String): Book? {
        val result: Book?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result =
                session.byNaturalId(Book::class.java).using("name", name).loadOptional().orElse(null)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Book> {
        val result: List<Book>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Book").list() as List<Book>
            session.transaction.commit()
        }
        return result
    }

    fun delete(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.delete(book)
            session.transaction.commit()
        }
    }

    fun update(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(book)
            session.transaction.commit()
        }
    }
}