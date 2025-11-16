# Property Listings Management System Conversion TODO

## Backend Conversion Steps

### 1. Update Project Metadata
- [x] Update pom.xml: Change artifactId to "property_listing_system", name to "property_listing_system", description to "Property Listings Management System"

### 2. Rename Main Package
- [x] Rename package from "task_management_system" to "property_listing_system"
- [x] Update TaskManagementSystemApplication.java to PropertyListingSystemApplication.java

### 3. Remove User-Related Code
- [x] Delete entire "users" package directory

### 4. Rename Tasks Package to Properties
- [x] Rename "tasks" package to "properties"

### 5. Update Property Entity and Related Files
- [ ] Rename Task.java to Property.java and update fields: remove assigneeId, priority, dueDate; add price (BigDecimal), location, bedrooms, bathrooms, size, status (PropertyStatus enum), category (ManyToOne Category, nullable)
- [ ] Create PropertyStatus.java enum with AVAILABLE, SOLD, RENTED
- [ ] Rename TaskRequestDto.java to PropertyRequestDto.java: update fields accordingly
- [ ] Rename TaskResponseDto.java to PropertyResponseDto.java: update fields accordingly
- [ ] Rename TaskController.java to PropertyController.java: change endpoints to /api/v1/properties, add PUT /api/v1/properties/{id}/status
- [ ] Rename TaskService.java to PropertyService.java: adjust method signatures
- [ ] Rename TaskServiceImpl.java to PropertyServiceImpl.java: update logic for new fields
- [ ] Rename TaskRepository.java to PropertyRepository.java
- [ ] Rename TaskMapper.java to PropertyMapper.java: adjust mappings

### 6. Create Categories Package
- [ ] Create Category.java entity: id, name
- [ ] Create CategoryRequestDto.java and CategoryResponseDto.java
- [ ] Create CategoryController.java with POST /api/v1/categories, GET /api/v1/categories, DELETE /api/v1/categories/{id}
- [ ] Create CategoryService.java and CategoryServiceImpl.java
- [ ] Create CategoryRepository.java
- [ ] Create CategoryMapper.java

### 7. Configuration
- [ ] Create application.properties with database configuration if missing

### 8. Cleanup
- [ ] Remove any remaining references to users or old task fields

## Followup Steps
- [ ] Run mvn clean compile to check for errors
- [ ] Create Flyway migrations for properties and categories tables
- [ ] Test endpoints with Postman
- [ ] Update frontend if needed
