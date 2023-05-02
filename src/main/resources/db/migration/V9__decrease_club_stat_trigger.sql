CREATE TRIGGER decrease_club_stats_trigger
    AFTER DELETE ON match
                        FOR EACH ROW
                        EXECUTE FUNCTION decrease_update_club_stats()