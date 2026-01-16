package com.aptechph.property_listing_system.properties.repository;

import com.aptechph.property_listing_system.properties.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
