CREATE DATABASE "event";
CREATE USER event_user PASSWORD 'pa55w0rd';
GRANT ALL PRIVILEGES ON DATABASE "event" TO event_user;
\c "event"
GRANT ALL ON SCHEMA public TO event_user;
