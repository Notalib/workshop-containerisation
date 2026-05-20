INSERT INTO GREETINGS(id, name)
VALUES (gen_random_uuid(), 'Docker'),
       (gen_random_uuid(), 'Workshop'),
       (gen_random_uuid(), 'The Future') ON CONFLICT (name) DO NOTHING;
