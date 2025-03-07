CREATE TYPE user_role AS ENUM('ADMIN', 'SELLER');

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role user_role NOT NULL,
    is_enabled BOOL NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TYPE comment_status AS ENUM('APPROVED', 'REJECTED', 'WAITING');

CREATE TABLE comments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seller_id UUID REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    author_id UUID REFERENCES users (id) ON DELETE SET NULL,
    rating INTEGER NOT NULL CHECK (rating > 0 AND rating < 6),
    message TEXT,
    status comment_status NOT NULL DEFAULT 'WAITING',
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