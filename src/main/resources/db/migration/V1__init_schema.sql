-- V1__init_schema.sql
-- Database: virtual_events_db (MySQL)

-- 1. USERS TABLE
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'ATTENDEE', 'SPEAKER') DEFAULT 'ATTENDEE',
    skills_and_interests TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. EVENTS TABLE
CREATE TABLE events (
    id VARCHAR(36) PRIMARY KEY,
    organizer_id VARCHAR(36) NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    max_attendees INT NOT NULL,
    status ENUM('UPCOMING', 'LIVE', 'COMPLETED') DEFAULT 'UPCOMING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. EVENT REGISTRATIONS (TICKETING)
CREATE TABLE event_registrations (
    id VARCHAR(36) PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_event (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. AI MATCHMAKING SUGGESTIONS
CREATE TABLE ai_match_suggestions (
    id VARCHAR(36) PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    target_user_id VARCHAR(36) NOT NULL,
    suggested_user_id VARCHAR(36) NOT NULL,
    match_score INT NOT NULL,
    match_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (target_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (suggested_user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. CHAT MESSAGES
CREATE TABLE chat_messages (
    id VARCHAR(36) PRIMARY KEY,
    event_id VARCHAR(36) NOT NULL,
    sender_id VARCHAR(36) NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE
);

-- INDEXES (For Performance)
CREATE INDEX idx_event_start_time ON events(start_time);
CREATE INDEX idx_chat_event_time ON chat_messages(event_id, sent_at);