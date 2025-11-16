package com.aptechph.property_listing_system.properties.repository;

import com.aptechph.property_listing_system.properties.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
