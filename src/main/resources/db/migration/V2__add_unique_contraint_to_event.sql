-- For restrict creating duplicate events for organizer
ALTER TABLE events ADD CONSTRAINT unique_organizer_title UNIQUE (organizer_id, title);