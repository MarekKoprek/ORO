package com.example.oro1.Repo;

import com.example.oro1.Model.Category;
import com.example.oro1.Model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepo extends JpaRepository<Part, Long> {
    List<Part> findAllByPriceBetween(double min, double max);
    List<Part> findAllByCategory(Category category);
}
