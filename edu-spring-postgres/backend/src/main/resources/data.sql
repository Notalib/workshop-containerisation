INSERT INTO GREETINGS(name) VALUES ('Docker'), ('Workshop'), ('The Future') ON CONFLICT (name) DO NOTHING;
