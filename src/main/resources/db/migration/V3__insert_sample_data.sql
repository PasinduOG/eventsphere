-- V3__insert_sample_data.sql
-- Insert Sample Data for Testing

-- 1. USERS (10 Records - Admins, Speakers, Attendees)
INSERT INTO users (id, full_name, email, password_hash, role, skills_and_interests) VALUES
    ('886a8539-5426-4c95-b50c-c54d72e55d28', 'Pasindu Owa Gamage', 'admin1@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ADMIN', 'System Architecture, Spring Boot, Angular'),
    ('eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'Kasun Perera', 'admin2@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ADMIN', 'Event Management, Marketing'),
    ('1ea3c9fd-ad68-4105-8220-3a15694bc083', 'Dr. Ruwan Kumara', 'speaker1@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'SPEAKER', 'AI, Machine Learning, Python'),
    ('94d5ca84-a17c-45ed-8506-cf4220f5fa80', 'Sarah Jenkins', 'speaker2@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'SPEAKER', 'Cloud Computing, AWS, DevOps'),
    ('7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'Nimali Silva', 'nimali@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Java, Spring Boot, Looking for Internships'),
    ('280ac445-578a-46c6-b879-7816c7c691bc', 'Chamara Fernando', 'chamara@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Angular, Frontend Development, UI/UX'),
    ('67f35b78-02fe-4879-8fe5-04d3633d5a56', 'Lahiru Senanayake', 'lahiru@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Microservices, Docker, Kubernetes'),
    ('f8bcd1ca-8ee9-4b29-a689-ee691614dd8d', 'Gayantha De Silva', 'gayantha@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Looking for open-source contributors, Java'),
    ('a3affe9d-1b0e-444e-a883-a24221f9aeb2', 'Sanduni Perera', 'sanduni@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Data Science, Gemini AI APIs'),
    ('5ff6e1d8-fa5c-4ff3-8e69-8d41ae1f728d', 'Tharindu Bandara', 'tharindu@gmail.com', '$2a$10$.g3Buxksd/EBNCE8UGoPA.1jCjrJJBspKoh3dfg7e5Tq2OIRF2emi', 'ATTENDEE', 'Spring Boot, Backend Development');

-- 2. EVENTS (10 Records)
INSERT INTO events (id, organizer_id, title, description, start_time, end_time, max_attendees, status) VALUES
    ('0a55db3f-f2bc-4c06-8a1f-10ba24747196', '886a8539-5426-4c95-b50c-c54d72e55d28', 'Sri Lanka Tech Summit 2026', 'The biggest tech summit in LK.', '2026-06-15 09:00:00', '2026-06-15 17:00:00', 500, 'UPCOMING'),
    ('63f69770-1b1f-4b98-8ba4-ca9a238a57e6', '886a8539-5426-4c95-b50c-c54d72e55d28', 'Spring Boot Masterclass', 'Deep dive into Spring WebFlux & Security.', '2026-05-20 10:00:00', '2026-05-20 12:00:00', 100, 'UPCOMING'),
    ('c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'AI & Machine Learning Expo', 'Future of AI with Gemini.', '2026-04-10 08:00:00', '2026-04-10 16:00:00', 300, 'LIVE'),
    ('1d2af9ec-78fa-4e2f-876e-dab07ce4a01c', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'Angular 18 Features', 'What is new in Angular 18.', '2026-04-15 14:00:00', '2026-04-15 16:00:00', 200, 'UPCOMING'),
    ('dbb370af-7b70-4afb-b01e-59d57de65383', '886a8539-5426-4c95-b50c-c54d72e55d28', 'Cloud Native Computing', 'AWS, GCP and Azure comparison.', '2026-07-01 09:00:00', '2026-07-01 13:00:00', 150, 'UPCOMING'),
    ('6f8eb473-b8fc-4e6c-af2f-f28ed0e09ce2', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'UI/UX Design Patterns', 'Modern design trends for web apps.', '2026-08-10 10:00:00', '2026-08-10 12:00:00', 50, 'UPCOMING'),
    ('c27a6a57-1f55-42d8-b915-e1aaae6ba671', '886a8539-5426-4c95-b50c-c54d72e55d28', 'Open Source Hackathon', 'Contribute to amazing projects.', '2026-09-01 00:00:00', '2026-09-02 23:59:00', 1000, 'UPCOMING'),
    ('6c9640c5-71e1-4d09-973c-3df63d71c6c0', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'DevOps Fundamentals', 'CI/CD with GitHub Actions.', '2026-03-01 09:00:00', '2026-03-01 11:00:00', 100, 'COMPLETED'),
    ('96f1f0e2-2552-44e6-826a-a88cbd58a817', '886a8539-5426-4c95-b50c-c54d72e55d28', 'Java 21 Features', 'Virtual Threads and Pattern Matching.', '2026-02-15 10:00:00', '2026-02-15 12:00:00', 80, 'COMPLETED'),
    ('84c01fd2-b755-4873-8e33-f5efb18741da', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'Startup Pitch Night', 'Pitch your ideas to investors.', '2026-05-25 18:00:00', '2026-05-25 21:00:00', 250, 'UPCOMING');

-- 3. EVENT REGISTRATIONS (10 Records - Users registering for events)
INSERT INTO event_registrations (id, event_id, user_id) VALUES
    ('0efaa622-7b58-4aea-8f22-8d995bf1d28d', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4'),
    ('331c5334-e568-44b8-af0c-01643b69e7c5', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '280ac445-578a-46c6-b879-7816c7c691bc'),
    ('d1d35cc5-4aec-42b3-bf25-9fc763eb3c9a', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '67f35b78-02fe-4879-8fe5-04d3633d5a56'),
    ('434a2f1a-435c-474f-bc34-353eccca78b8', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', 'f8bcd1ca-8ee9-4b29-a689-ee691614dd8d'),
    ('f6ceebc3-142f-4bd9-80a9-5bd4f8783bf2', '63f69770-1b1f-4b98-8ba4-ca9a238a57e6', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4'),
    ('dc150f97-0f09-458f-980b-b74032c44963', '63f69770-1b1f-4b98-8ba4-ca9a238a57e6', '5ff6e1d8-fa5c-4ff3-8e69-8d41ae1f728d'),
    ('412e44ab-28cf-4e78-b8b8-041ed7c4b3a5', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'a3affe9d-1b0e-444e-a883-a24221f9aeb2'),
    ('a02afb08-2be9-4b73-9a0e-9b276eda207b', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4'),
    ('a0abee79-6ad5-4463-bbe3-f92e9249695f', '1d2af9ec-78fa-4e2f-876e-dab07ce4a01c', '280ac445-578a-46c6-b879-7816c7c691bc'),
    ('23d3ffa3-7e5d-4d1e-b475-e4a41ec88fd2', 'c27a6a57-1f55-42d8-b915-e1aaae6ba671', 'f8bcd1ca-8ee9-4b29-a689-ee691614dd8d');

-- 4. AI MATCHMAKING SUGGESTIONS (10 Records)
INSERT INTO ai_match_suggestions (id, event_id, target_user_id, suggested_user_id, match_score, match_reason) VALUES
    ('b5537ed1-73bc-43e0-8e3f-e46fa6106948', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', '5ff6e1d8-fa5c-4ff3-8e69-8d41ae1f728d', 95, 'Both are interested in Spring Boot Backend Development.'),
    ('9c78f687-b195-4ef3-ae49-ed990744fe8b', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '280ac445-578a-46c6-b879-7816c7c691bc', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 80, 'You do Angular Frontend, and they do Spring Boot Backend. Great team fit!'),
    ('162f08ba-a25e-4a77-b6e5-2d4a75dfc712', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'f8bcd1ca-8ee9-4b29-a689-ee691614dd8d', 88, 'They are looking for open-source contributors in Java.'),
    ('49eee4b7-e345-40d3-b821-4782b9f5a5d0', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'a3affe9d-1b0e-444e-a883-a24221f9aeb2', '1ea3c9fd-ad68-4105-8220-3a15694bc083', 98, 'Dr. Ruwan is speaking on AI, which matches your Data Science interests.'),
    ('991b57f4-869c-477c-af07-b98d5059a192', '63f69770-1b1f-4b98-8ba4-ca9a238a57e6', '5ff6e1d8-fa5c-4ff3-8e69-8d41ae1f728d', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 90, 'Both are attending this Spring Boot masterclass and looking for roles.'),
    ('7eb2e6fb-a5d2-4a2c-87e9-7ab6db8db9fc', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '67f35b78-02fe-4879-8fe5-04d3633d5a56', '94d5ca84-a17c-45ed-8506-cf4220f5fa80', 85, 'Sarah Jenkins works in Cloud, which matches your Microservices/Docker skills.'),
    ('3d6a11f9-63e7-490c-b2c8-c5c9cf1e44ad', '1d2af9ec-78fa-4e2f-876e-dab07ce4a01c', '280ac445-578a-46c6-b879-7816c7c691bc', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 75, 'Kasun organizes Tech Events, good for networking in UI/UX.'),
    ('a5dff2a6-d79d-4cb3-9e6a-68f576f289a4', 'c27a6a57-1f55-42d8-b915-e1aaae6ba671', 'f8bcd1ca-8ee9-4b29-a689-ee691614dd8d', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 92, 'Nimali is an intern looking for Java projects to contribute to.'),
    ('fed3e047-e4bd-42a9-b51b-f1c22b5e8f60', '0a55db3f-f2bc-4c06-8a1f-10ba24747196', '5ff6e1d8-fa5c-4ff3-8e69-8d41ae1f728d', 'f8bcd1ca-8ee9-4b29-a689-ee691614dd8d', 85, 'Both are highly skilled in backend technologies.'),
    ('8d838064-7169-4a55-a307-bb0a046882fb', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'a3affe9d-1b0e-444e-a883-a24221f9aeb2', 70, 'Expand your horizons: Learn how Gemini AI can integrate with Spring Boot.');

-- 5. CHAT MESSAGES (10 Records - Simulating a live chat in c71703ca-5a81-4080-bc07-fb1889f7d0b2)
INSERT INTO chat_messages (id, event_id, sender_id, message) VALUES
    ('90f84a59-1dec-4f23-89b3-6e8dce7c3f61', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'Welcome everyone to the AI Expo! We will start in 5 minutes.'),
    ('d915dc9c-947a-4190-912e-72534dc68e29', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'Hi Kasun, excited for this session!'),
    ('6e15575d-deca-4568-8e8f-9a12de36a9e2', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'a3affe9d-1b0e-444e-a883-a24221f9aeb2', 'Will Dr. Ruwan be covering the new Gemini APIs?'),
    ('7146288b-20ca-407d-a385-2dd072e60bba', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '1ea3c9fd-ad68-4105-8220-3a15694bc083', 'Yes Sanduni, I will be doing a live demo on Gemini.'),
    ('d4dae440-33af-4f62-8a33-bbb30b663831', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'a3affe9d-1b0e-444e-a883-a24221f9aeb2', 'Awesome! Thank you.'),
    ('f29bb8b6-2350-43cf-93cd-86f86e60c32e', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', 'eb334f13-7461-4d28-9e0e-7b933a5e5e4e', 'Alright, we are live now. Over to you, Dr. Ruwan.'),
    ('2159e4da-4d69-43f7-b1ee-9ab94f7c7e07', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'Is the screen sharing working for everyone? It looks a bit blurry for me.'),
    ('35d547b2-ad09-42ef-81b8-00dbab162839', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '67f35b78-02fe-4879-8fe5-04d3633d5a56', 'It is clear on my end Nimali. Maybe check your connection?'),
    ('26f4c09b-13cb-4eea-8172-98d65c41ddbc', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '7743f6b1-d520-4a8f-b7a5-18e1d384e0f4', 'Ah yes, fixed it. Thanks!'),
    ('dbdab8b8-45c7-44f2-92c4-1715dddef3e7', 'c71703ca-5a81-4080-bc07-fb1889f7d0b2', '1ea3c9fd-ad68-4105-8220-3a15694bc083', 'Let us start by looking at how AI matchmaking algorithms work under the hood...');