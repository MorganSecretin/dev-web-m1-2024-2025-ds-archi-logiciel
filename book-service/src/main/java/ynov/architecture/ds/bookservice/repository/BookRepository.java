package ynov.architecture.ds.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ynov.architecture.ds.bookservice.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
