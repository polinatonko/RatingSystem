CREATE TYPE user_role AS ENUM('ROLE_ADMIN', 'ROLE_SELLER');

CREATE TABLE user_details (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role user_role NOT NULL
);

CREATE TABLE users (
    id UUID PRIMARY KEY REFERENCES user_details (id) ON DELETE CASCADE,
    is_enabled BOOL NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE comment_details (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    rating INTEGER NOT NULL CHECK (rating > 0 AND rating < 6),
    message TEXT
);

CREATE TABLE comments (
    id UUID PRIMARY KEY REFERENCES comment_details ON DELETE CASCADE,
    seller_id UUID REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    author_id UUID REFERENCES users (id) ON DELETE SET NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TYPE request_status AS ENUM('APPROVED', 'REJECTED', 'WAITING');

CREATE TABLE submit_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment_details_id UUID REFERENCES comment_details ON DELETE SET NULL UNIQUE,
    user_details_id UUID REFERENCES user_details ON DELETE SET NULL UNIQUE,
    seller_id UUID REFERENCES users ON DELETE CASCADE,
    author_id UUID REFERENCES users ON DELETE CASCADE,
    status request_status NOT NULL DEFAULT 'WAITING',
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE games (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(100) NOT NULL,
    text TEXT,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE game_objects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    game_id UUID REFERENCES games (id) ON DELETE CASCADE NOT NULL,
    title VARCHAR(100) NOT NULL,
    text TEXT,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);