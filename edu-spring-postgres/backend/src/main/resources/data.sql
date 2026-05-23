INSERT INTO GREETINGS(id, name)
VALUES (gen_random_uuid(), 'Docker container'),
       (gen_random_uuid(), 'An awesome workshop'),
       (gen_random_uuid(), 'The Future') ON CONFLICT (name) DO NOTHING;
