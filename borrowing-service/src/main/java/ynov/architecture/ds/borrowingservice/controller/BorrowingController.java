package ynov.architecture.ds.borrowingservice.controller;


import ynov.architecture.ds.borrowingservice.entity.Borrowing;
import ynov.architecture.ds.borrowingservice.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @GetMapping
    public List<Borrowing> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/{id}")
    public Borrowing getBorrowingById(@PathVariable Long id) {
        return borrowingService.getBorrowingById(id);
    }

    @PatchMapping("/{id}")
    public Borrowing updateBorrowing(@PathVariable Long id, @RequestBody Borrowing borrowing) {
        return borrowingService.updateBorrowing(id, borrowing);
    }

    @PostMapping
    public Borrowing createBorrowing(@RequestBody Borrowing borrowing) {
        return borrowingService.createBorrowing(borrowing);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrowing(@PathVariable Long id) {
        borrowingService.deleteBorrowing(id);
    }

}
