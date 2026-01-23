-- Insert default users
INSERT INTO users (username, email, password, role, enabled, created_at, updated_at) VALUES
('admin', 'admin@library.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', true, NOW(), NOW()),
('librarian', 'librarian@library.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'LIBRARIAN', true, NOW(), NOW());

-- Insert sample books
INSERT INTO books (title, author, isbn, publisher, publication_year, total_copies, available_copies, description, created_at, updated_at) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 'Scribner', 1925, 5, 5, 'A classic American novel', NOW(), NOW()),
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 'J.B. Lippincott & Co.', 1960, 3, 2, 'A novel about racial injustice', NOW(), NOW()),
('1984', 'George Orwell', '978-0-452-28423-4', 'Secker & Warburg', 1949, 4, 3, 'A dystopian social science fiction novel', NOW(), NOW()),
('Pride and Prejudice', 'Jane Austen', '978-0-14-143951-8', 'T. Egerton', 1813, 2, 2, 'A romantic novel of manners', NOW(), NOW()),
('The Catcher in the Rye', 'J.D. Salinger', '978-0-316-76948-0', 'Little, Brown and Company', 1951, 3, 1, 'A controversial coming-of-age story', NOW(), NOW());

-- Insert sample members
INSERT INTO members (name, email, phone, address, membership_type, membership_date, expiry_date, active, created_at, updated_at) VALUES
('John Doe', 'john.doe@email.com', '555-0101', '123 Main St, City, State', 'Student', NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), true, NOW(), NOW()),
('Jane Smith', 'jane.smith@email.com', '555-0102', '456 Oak Ave, City, State', 'Faculty', NOW(), DATE_ADD(NOW(), INTERVAL 2 YEAR), true, NOW(), NOW()),
('Bob Johnson', 'bob.johnson@email.com', '555-0103', '789 Pine Rd, City, State', 'Staff', NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), true, NOW(), NOW()),
('Alice Brown', 'alice.brown@email.com', '555-0104', '321 Elm St, City, State', 'Student', NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), true, NOW(), NOW()),
('Charlie Wilson', 'charlie.wilson@email.com', '555-0105', '654 Maple Dr, City, State', 'External', NOW(), DATE_ADD(NOW(), INTERVAL 6 MONTH), true, NOW(), NOW());
