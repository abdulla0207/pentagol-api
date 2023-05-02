CREATE FUNCTION decrease_update_club_stats()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.club_a_score > OLD.club_b_score THEN
        UPDATE club SET games_played = games_played - 1, point = point - 3 WHERE id = OLD.club_a_id;
        UPDATE club SET games_played = games_played - 1 WHERE id = OLD.club_b_id;
    ELSIF OLD.club_a_score < OLD.club_b_score THEN
        UPDATE club SET games_played = games_played - 1 WHERE id = OLD.club_a_id;
        UPDATE club SET games_played = games_played - 1, point = point - 3 WHERE id = OLD.club_b_id;
    ELSE
        UPDATE club SET games_played = games_played - 1, point = point - 1 WHERE id IN (OLD.club_a_id, OLD.club_b_id);
    END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql