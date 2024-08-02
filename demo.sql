SET DEFINE OFF;

DROP TABLE ratings;

DROP TABLE bookings;

DROP TABLE tokens;

DROP TABLE rooms;

DROP TABLE users;

CREATE TABLE users (
    id NVARCHAR2 (255) NOT NULL PRIMARY KEY,
    phone_number NVARCHAR2 (255),
    password NVARCHAR2 (255),
    name NVARCHAR2 (255),
    role NVARCHAR2 (255),
    created_at DATE,
    is_active NUMBER (1) DEFAULT 1,
    CONSTRAINT chk_users_role CHECK (
        role IN (
            'admin',
            'user',
            'receptionist'
        )
    )
);

INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E36A1A0E','000000001', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'admin', 'admin');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E36A2A0E','000000002', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 1', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E36A3A0E','000000003', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 2', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E36A4A0E','000000004', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 3', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E36A5A0E','000000005', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'receptionist 1', 'receptionist');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E31A4A0E','000000006', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 4', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E32A4A0E','000000007', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 5', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E33A4A0E','000000008', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 6', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E34A4A0E','000000009', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 7', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E35A4A0E','000000010', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 8', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E37A4A0E','000000011', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'user 9', 'user');
INSERT INTO users(id,phone_number, password, name, role) VALUES ('F0C8CB35F1434C8BABE8E736E39A4A0E','000000012', '$2a$10$OK0gQxDSMaBXN6f8rdIqYu8U/ebzue7Px8tRhRQ0xYS6vpA0bNWH6', 'receptionist 2', 'receptionist');


CREATE TABLE tokens (
    id NVARCHAR2 (255) NOT NULL PRIMARY KEY,
    token VARCHAR2 (255) UNIQUE NOT NULL,
    token_type VARCHAR2 (255) NOT NULL,
    expiration_date DATE,
    revoked NUMBER (1) NOT NULL,
    expired NUMBER (1) NOT NULL,
    refresh_token VARCHAR2 (255) NOT NULL,
    refresh_expiration_date DATE,
    user_id NVARCHAR2 (255),
    CONSTRAINT fk_user_id_tokens FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE rooms (
    id NVARCHAR2 (255) NOT NULL PRIMARY KEY,
    room_number NUMBER,
    room_type NVARCHAR2 (255),
    room_price FLOAT,
    room_status NVARCHAR2 (255) DEFAULT 'available',
    rating FLOAT DEFAULT 0,
    rating_quantity NUMBER DEFAULT 0,
    CONSTRAINT chk_rooms_room_status CHECK (
        room_status IN (
            'available',
            'booked',
            'cleaning',
            'deleted'
        )
    )
);


INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1A0E', 101, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2A0E', 102, 'couple', 200, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3A0E', 103, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4A0E', 104, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5A0E', 105, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6A0E', 106, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7A0E', 107, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8A0E', 108, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9A0E', 109, 'single', 100, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36AAA0E', 110, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1B0E', 201, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2B0E', 202, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3B0E', 203, 'single', 100, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4B0E', 204, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5B0E', 205, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6B0E', 206, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7B0E', 207, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8B0E', 208, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9B0E', 209, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36AA0E', 210, 'single', 100, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1C0E', 301, 'single', 100, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2C0E', 302, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3C0E', 303, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4C0E', 304, 'couple', 200, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5C0E', 305, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6C0E', 306, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7C0E', 307, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8C0E', 308, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9C0E', 309, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36AAC0E', 310, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1D0E', 401, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2D0E', 402, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3D0E', 403, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4D0E', 404, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5D0E', 405, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6D0E', 406, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7D0E', 407, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8D0E', 408, 'couple', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9D0E', 409, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36AAD0E', 410, 'single', 100, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A1D0E', 501, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A2D0E', 502, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A3D0E', 503, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A4D0E', 504, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A5D0E', 505, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A6D0E', 506, 'couple', 400, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A7D0E', 507, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A8D0E', 508, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36A9D0E', 509, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C9CB35F1434C8BABE8E736E36BAD0E', 510, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1E0E', 601, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2E0E', 602, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3E0E', 603, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4E0E', 604, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5E0E', 605, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6E0E', 606, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7E0E', 607, 'single', 200, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8E0E', 608, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9E0E', 609, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36AAE0E', 610, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A1F0E', 701, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A2F0E', 702, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A3F0E', 703, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A4F0E', 704, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A5F0E', 705, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A6F0E', 706, 'couple', 400, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A7F0E', 707, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A8F0E', 708, 'couple', 400, 'booked');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36A9F0E', 709, 'single', 200, 'available');
INSERT INTO rooms(id, room_number, room_type, room_price, room_status) VALUES ('F0C8CB35F1434C8BABE8E736E36AAF0E', 710, 'single', 200, 'available');

CREATE TABLE bookings (
    id NVARCHAR2 (255) NOT NULL PRIMARY KEY,
    user_phonenumber NVARCHAR2 (255),
    room_number NUMBER,
    booking_date DATE DEFAULT SYSDATE,
    checkin DATE,
    checkout DATE,
    total_price FLOAT DEFAULT 0,
    status NVARCHAR2 (255) DEFAULT 'pending',
    note NVARCHAR2 (255),
    confirmed_by NVARCHAR2 (255),
    is_rating NUMBER (1) DEFAULT 0,
    CONSTRAINT chk_bookings_status CHECK (
        status IN (
            'pending',
            'confirmed',
            'operational',
            'completed',
            'cancelled'
        )
    )
);

INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A2A0E', '000000002', 102, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A3A0E', '000000003', 203, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A4A0E', '000000004', 304, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A6A0E', '000000006', 506, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A7A0E', '000000007', 607, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A8A0E', '000000008', 708, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A9A0E', '000000009', 109, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36AAA0E', '000000010', 210, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date) VALUES ('F0C8CB35F1434C8BABE8E736E36A1B0E', '000000011', 301, TO_DATE('2024-07-30', 'YYYY-MM-DD'));
INSERT INTO bookings(id, user_phonenumber, room_number, booking_date,checkin, status, confirmed_by) VALUES ('F0C9CB35F1434C8BABE8E736E36A2A0E', '000000002', 110, TO_DATE('2024-07-30', 'YYYY-MM-DD'), TO_DATE('2024-07-30', 'YYYY-MM-DD'), 'operational', 'admin');

CREATE TABLE ratings (
    id NVARCHAR2(255) NOT NULL PRIMARY KEY,
    booking_id NVARCHAR2(255),
    user_phonenumber NVARCHAR2(255),
    user_name NVARCHAR2(255),
    room_number NUMBER,
    rating NUMBER(5, 1),
    message NVARCHAR2(255),
    created_at DATE DEFAULT SYSDATE
);