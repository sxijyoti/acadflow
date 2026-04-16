-- Initialize Academic Workflow Database
-- This script runs automatically when the MySQL container starts

USE academic_workflow;

-- Create USERS table (Module1)
CREATE TABLE IF NOT EXISTS user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create SUBJECT table (Module1)
CREATE TABLE IF NOT EXISTS subject (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  code VARCHAR(20) NOT NULL UNIQUE,
  credits INT NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create ENROLLMENT table (Module1)
CREATE TABLE IF NOT EXISTS enrollment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  subject_id BIGINT NOT NULL,
  status ENUM('ACTIVE', 'COMPLETED', 'WITHDRAWN', 'INACTIVE') DEFAULT 'ACTIVE',
  enrollment_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
  UNIQUE KEY unique_enrollment (user_id, subject_id)
);

-- Create ASSIGNMENT table (Module2)
CREATE TABLE IF NOT EXISTS assignment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  due_date DATETIME NOT NULL,
  max_marks INT,
  created_by BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
  FOREIGN KEY (created_by) REFERENCES user(id) ON DELETE SET NULL
);

-- Create SUBMISSION table (Module2)
CREATE TABLE IF NOT EXISTS submission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  assignment_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  marks_obtained INT,
  status ENUM('PENDING', 'SUBMITTED', 'GRADED', 'LATE') DEFAULT 'PENDING',
  submission_date DATETIME,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (assignment_id) REFERENCES assignment(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create RESOURCE table (Module2)
CREATE TABLE IF NOT EXISTS resource (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  url VARCHAR(500),
  resource_type VARCHAR(50),
  uploaded_by BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
  FOREIGN KEY (uploaded_by) REFERENCES user(id) ON DELETE SET NULL
);

-- Create SYLLABUS table (Module2)
CREATE TABLE IF NOT EXISTS syllabus (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  duration_weeks INT,
  learning_outcomes TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE
);

-- Create TIMETABLE table (Module4)
CREATE TABLE IF NOT EXISTS timetable (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT NOT NULL,
  day_of_week VARCHAR(20) NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  location VARCHAR(255),
  classroom VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE
);

-- Create EXAM table (Module4)
CREATE TABLE IF NOT EXISTS exam (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT NOT NULL,
  exam_date DATE NOT NULL,
  exam_type VARCHAR(50) NOT NULL,
  location VARCHAR(255),
  instructions TEXT,
  max_marks INT,
  duration_minutes INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE
);

-- Create EVENT table (Module3)
CREATE TABLE IF NOT EXISTS event (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  event_date DATETIME NOT NULL,
  event_type VARCHAR(50),
  created_by BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (created_by) REFERENCES user(id) ON DELETE SET NULL
);

-- Create HOLIDAY table (Module3)
CREATE TABLE IF NOT EXISTS holiday (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  holiday_date DATE NOT NULL,
  description TEXT,
  is_recurring BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO user (first_name, last_name, email, password, role) VALUES
('Admin', 'User', 'admin@acadflow.com', 'admin123', 'ADMIN'),
('Teacher', 'User', 'teacher@acadflow.com', 'teacher123', 'TEACHER'),
('Student', 'User', 'student@acadflow.com', 'password123', 'STUDENT');

INSERT INTO subject (name, code, credits, description) VALUES
('Mathematics', 'MATH101', 4, 'Introduction to Calculus and Linear Algebra'),
('Physics', 'PHYS101', 4, 'Classical Mechanics and Thermodynamics'),
('Computer Science', 'CS101', 4, 'Introduction to Programming and Data Structures');

-- Create indexes for better performance
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_enrollment_user ON enrollment(user_id);
CREATE INDEX idx_enrollment_subject ON enrollment(subject_id);
CREATE INDEX idx_assignment_subject ON assignment(subject_id);
CREATE INDEX idx_submission_user ON submission(user_id);
CREATE INDEX idx_timetable_subject ON timetable(subject_id);
CREATE INDEX idx_exam_subject ON exam(subject_id);
CREATE INDEX idx_exam_date ON exam(exam_date);
