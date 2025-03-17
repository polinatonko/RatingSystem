ALTER TABLE users
ADD COLUMN rating REAL NOT NULL DEFAULT 0;

CREATE OR REPLACE FUNCTION update_rating() RETURNS TRIGGER AS $update_rating$
BEGIN
    IF TG_OP = 'UPDATE' AND OLD.seller_id <> NEW.seller_id OR TG_OP = 'DELETE' THEN
        WITH res AS (
            SELECT COALESCE(AVG(rating), 0) as new_rating
            FROM comment_details d
            JOIN comments c USING (id)
            WHERE d.id = OLD.id AND c.seller_id = OLD.seller_id
        )
        UPDATE users
        SET rating = res.new_rating
        FROM res
        WHERE id = OLD.seller_id;

        IF TG_OP = 'DELETE' THEN
            RETURN OLD;
        END IF;
    END IF;

    WITH res AS (
        SELECT COALESCE(AVG(rating), 0) AS new_rating
        FROM comment_details d
        JOIN comments c USING (id)
        WHERE d.id = NEW.id AND c.seller_id = NEW.seller_id
    )
    UPDATE users
    SET rating = res.new_rating
    FROM res
    WHERE id = NEW.seller_id;

    RETURN NEW;
END;
$update_rating$ LANGUAGE plpgsql;

CREATE TRIGGER update_rating_trigger
    AFTER INSERT OR UPDATE OR DELETE ON comments
    FOR EACH ROW
    EXECUTE FUNCTION update_rating();
