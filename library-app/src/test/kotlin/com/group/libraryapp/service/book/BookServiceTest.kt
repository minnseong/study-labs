package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val bookService: BookService,
) {

    @AfterEach
    fun afterEach() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }

    @DisplayName("도서 저장이 정상적으로 동작한다.")
    @Test
    fun saveBookTest() {
 
        // given
        val request = BookRequest("A", BookType.COMPUTER)
        
        // when
        bookService.saveBook(request)

        // then
        val results = bookRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].type).isEqualTo(BookType.COMPUTER)
    }
    
    @DisplayName("도서 대출이 정상적으로 동작한다.")
    @Test
    fun loanBookTest() {

        // given
        bookRepository.save(Book.fixture("bookA"))
        userRepository.save(User("userA", 21))
        val request = BookLoanRequest("userA", "bookA")

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("bookA")
        assertThat(results[0].user.name).isEqualTo("userA")
        assertThat(results[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @DisplayName("이미 대출 중인 도서를 대출할 경우 예외가 발생한다.")
    @Test
    fun loanBookTest2() {

        // given
        val book = bookRepository.save(Book.fixture("bookA"))
        val userA = userRepository.save(User("userA", 21))
        val userB = userRepository.save(User("userB", 21))
        userLoanHistoryRepository.save(
            UserLoanHistory.fixture(userA, "bookA")
        )

        val request = BookLoanRequest(userB.name, "bookA")

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message

        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @DisplayName("도서 반납이 정상적으로 동작한다.")
    @Test
    fun returnBookTest() {

        // given
        val book = bookRepository.save(Book.fixture("bookA"))
        val userA = userRepository.save(User("userA", 21))
        userLoanHistoryRepository.save(
            UserLoanHistory.fixture(userA, "bookA")
        )
        val request = BookReturnRequest("userA", "bookA")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("bookA")
        assertThat(results[0].user.name).isEqualTo("userA")
        assertThat(results[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }

    @Test
    @DisplayName("책 대여 권수를 정상 확인한다.")
    fun countLoanBookTest() {

        // given
        val user = userRepository.save(User("유저A", null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(user, "A"),
            UserLoanHistory.fixture(user, "B", UserLoanStatus.RETURNED),
            UserLoanHistory.fixture(user, "C", UserLoanStatus.RETURNED),
        ))

        // when
        val result = bookService.countLoanedBook()

        // then
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("분야별 책 권수를 정상 확인한다.")
    fun getBookStatisticsTest() {

        // given
        bookRepository.saveAll(listOf(
            Book.fixture("A", BookType.COMPUTER),
            Book.fixture("B", BookType.COMPUTER),
            Book.fixture("C", BookType.SCIENCE),
        ))

        // when
        val results = bookService.getBookStatistics()

        // then
        assertThat(results).hasSize(2)
        assertCount(results, BookType.COMPUTER, 2)
        assertCount(results, BookType.SCIENCE, 1)
    }

    private fun assertCount(results: List<BookStatResponse>, type: BookType, count: Int) {
        assertThat(results.first { result -> result.type == type}.count).isEqualTo(count)
    }
}