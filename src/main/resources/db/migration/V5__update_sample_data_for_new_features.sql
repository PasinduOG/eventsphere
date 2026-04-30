-- V5__update_sample_data_for_new_features.sql
-- Database: virtual_events_db (MySQL)
-- Description: Updating V3 sample data to include Premium status, AI Match limits, and Video Call Room IDs.

-- 1. UPDATE USERS (Premium Status & AI Match Counts)

-- Automatically upgrade Admin and Organizer roles to Premium
UPDATE users
SET is_premium = TRUE, ai_match_count = 10
WHERE role IN ('ADMIN', 'ORGANIZER');

-- Test Case 1: Set Match Count to 3 for "Nimali Silva" (Free User).
-- (This allows you to test the "Free limit reached" Exception logic).
UPDATE users
SET is_premium = FALSE, ai_match_count = 3
WHERE email = 'nimali@gmail.com';

-- Test Case 2: Upgrade "Sanduni Perera" to Premium.
-- (She should be able to generate more than 3 AI Matches without errors).
UPDATE users
SET is_premium = TRUE, ai_match_count = 5
WHERE email = 'sanduni@gmail.com';

-- Keep all other Attendees as Free Users (their ai_match_count is currently around 0-1)
UPDATE users
SET is_premium = FALSE, ai_match_count = 1
WHERE email IN ('chamara@gmail.com', 'lahiru@gmail.com');


-- 2. UPDATE EVENTS (Add Meeting Room IDs)

-- Add a Room ID to the "AI & Machine Learning Expo" (LIVE event)
UPDATE events
SET meeting_room_id = 'room-ai-expo-live-2026'
WHERE id = 'c71703ca-5a81-4080-bc07-fb1889f7d0b2';

-- Add a Room ID to "Sri Lanka Tech Summit 2026"
UPDATE events
SET meeting_room_id = 'room-sl-tech-summit-2026'
WHERE id = '0a55db3f-f2bc-4c06-8a1f-10ba24747196';

-- Add a Room ID to "Spring Boot Masterclass"
UPDATE events
SET meeting_room_id = 'room-spring-boot-masterclass'
WHERE id = '63f69770-1b1f-4b98-8ba4-ca9a238a57e6';

-- Assign random Room IDs (UUIDs) to all remaining events
UPDATE events
SET meeting_room_id = UUID()
WHERE meeting_room_id IS NULL;