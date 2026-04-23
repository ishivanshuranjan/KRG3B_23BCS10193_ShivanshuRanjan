-- data.sql for src/main/resources/data.sql

INSERT INTO candidates (id, name, party, vote_count, bio, slogan, status, email, avatar_color) VALUES
(1, 'Ruchi Sharma', 'Bharatiya Vikas Party', 0, 'University student focused on technology.', 'Vikas har ghar tak', 'APPROVED', 'ruchi.sharma@example.com', '#1a2744'),
(2, 'Tanya Verma', 'Progressive India Alliance', 0, 'Tech enthusiast and entrepreneur.', 'Digital India, Strong India', 'APPROVED', 'tanya.verma@example.com', '#7c3aed'),
(3, 'Gunjan Kamboj', 'Aam Janta Dal', 0, 'Activist for grassroots development.', 'Janta ki awaaz, Janta ka raj', 'APPROVED', 'gunjan.kamboj@example.com', '#0d9488'),
(4, 'Aarav Mehta', 'National Reform Front', 0, 'Public policy graduate working on civic engagement.', 'Nayi soch, naya Bharat', 'APPROVED', 'aarav.mehta@example.com', '#f59e0b'),
(5, 'Meera Nair', 'Green Future Movement', 0, 'Environmental advocate promoting sustainable cities.', 'Harit kal, behtar kal', 'APPROVED', 'meera.nair@example.com', '#16a34a'),
(6, 'Kabir Singh', 'Youth Progress Party', 0, 'Community organizer supporting jobs and education.', 'Yuva shakti, desh ki pragati', 'APPROVED', 'kabir.singh@example.com', '#dc2626')
ON CONFLICT (id) DO NOTHING;

-- Add these lines to application.properties to enable this file:
--   spring.sql.init.mode=always
--   spring.jpa.defer-datasource-initialization=true
-- After first run, change init.mode to 'never' to skip on future restarts.
