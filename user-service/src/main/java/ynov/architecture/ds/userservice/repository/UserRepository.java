package ynov.architecture.ds.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ynov.architecture.ds.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
