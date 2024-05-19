package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
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
        assertThat(results[0].isReturn).isEqualTo(false)
    }

    @DisplayName("이미 대출 중인 도서를 대출할 경우 예외가 발생한다.")
    @Test
    fun loanBookTest2() {

        // given
        val book = bookRepository.save(Book.fixture("bookA"))
        val userA = userRepository.save(User("userA", 21))
        val userB = userRepository.save(User("userB", 21))
        userLoanHistoryRepository.save(
            UserLoanHistory(
                userA,
                book.name,
                false
            )
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
            UserLoanHistory(
                userA,
                book.name,
                false
            )
        )
        val request = BookReturnRequest("userA", "bookA")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("bookA")
        assertThat(results[0].user.name).isEqualTo("userA")
        assertThat(results[0].isReturn).isEqualTo(true)
    }
}