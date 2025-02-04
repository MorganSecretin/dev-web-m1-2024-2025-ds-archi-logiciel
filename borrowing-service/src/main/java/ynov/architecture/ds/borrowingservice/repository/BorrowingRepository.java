package ynov.architecture.ds.borrowingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ynov.architecture.ds.borrowingservice.entity.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    void deleteAllByUserId(Long userId);

    void deleteAllByBookId(Long bookId);
}
