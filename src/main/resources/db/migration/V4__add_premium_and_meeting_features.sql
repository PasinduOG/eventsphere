-- V4__add_premium_and_meeting_features.sql
-- Database: virtual_events_db (MySQL)

-- 1. Add Premium and AI Match limit for users table
ALTER TABLE users
    ADD COLUMN is_premium BOOLEAN DEFAULT FALSE AFTER role,
    ADD COLUMN ai_match_count INT DEFAULT 0 AFTER is_premium;

-- 2. Add Video Call Room ID for event table
ALTER TABLE events
    ADD COLUMN meeting_room_id VARCHAR(255) AFTER status;