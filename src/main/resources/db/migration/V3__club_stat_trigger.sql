CREATE OR REPLACE FUNCTION update_club_stats()
    RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT match.match_date FROM match WHERE match.id = NEW.id) <= NOW() THEN
        IF NEW.club_a_score > NEW.club_b_score THEN
UPDATE club SET games_played = games_played + 1, point = point + 3 WHERE id = NEW.club_a_id;
UPDATE club SET games_played = games_played + 1 WHERE id = NEW.club_b_id;
ELSIF NEW.club_a_score < NEW.club_b_score THEN
UPDATE club SET games_played = games_played + 1 WHERE id = NEW.club_a_id;
UPDATE club SET games_played = games_played + 1, point = point + 3 WHERE id = NEW.club_b_id;
ELSE
UPDATE club SET games_played = games_played + 1, point = point + 1 WHERE id IN (NEW.club_a_id, NEW.club_b_id);
END IF;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql