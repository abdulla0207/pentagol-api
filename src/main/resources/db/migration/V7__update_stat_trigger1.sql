CREATE TRIGGER update_club_stats_trigger
    AFTER INSERT OR UPDATE ON match
                        FOR EACH ROW
                        EXECUTE FUNCTION update_club_stats()