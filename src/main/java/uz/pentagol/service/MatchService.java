package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.MatchDTO;
import uz.pentagol.entity.MatchEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppForbiddenException;
import uz.pentagol.exceptions.MatchDateException;
import uz.pentagol.repository.MatchRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository){
        this.matchRepository = matchRepository;
    }
    public List<MatchEntity> getPreviousWeekGames(int leagueId){
        LocalDateTime currentDate = LocalDateTime.now();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int numOfWeeks = currentDate.get(weekFields.weekOfWeekBasedYear());

        LocalDateTime firstDateTimeOfWeek = LocalDateTime.now()
                .with(weekFields.weekOfYear(), numOfWeeks-1)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIN);

        LocalDateTime lastDateTimeOfWeek = LocalDateTime.now()
                .with(weekFields.weekOfYear(), numOfWeeks-1)
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .with(LocalTime.MAX);


        List<MatchEntity> matchEntities = matchRepository.getPreviousWeekMatches(firstDateTimeOfWeek, lastDateTimeOfWeek, leagueId);

        return matchEntities;
    }

    public List<MatchEntity> getNextWeekGames(int leagueId) {
        LocalDateTime currentDate = LocalDateTime.now();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int numOfWeeks = currentDate.get(weekFields.weekOfWeekBasedYear());

        LocalDateTime firstDateTimeOfWeek = LocalDateTime.now()
                .with(weekFields.weekOfYear(), numOfWeeks)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIN);

        LocalDateTime lastDateTimeOfWeek = LocalDateTime.now()
                .with(weekFields.weekOfYear(), numOfWeeks)
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .with(LocalTime.MAX);

        return matchRepository.getNextWeekMatches(firstDateTimeOfWeek, lastDateTimeOfWeek, leagueId);
    }

    public MatchDTO create(MatchDTO matchDTO, JwtDTO jwtDTO) {
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime matchDateDTO = LocalDateTime.parse(matchDTO.getMatchDate(), formatter);

        String splitToDate = matchDTO.getMatchDate().split(" ")[0];

        Optional<MatchEntity> getByMatchDate = matchRepository.findByMatchDate(matchDateDTO, matchDTO.getClubAId(), matchDTO.getClubBId());

        if(getByMatchDate.isPresent() &&
                getByMatchDate.get().getMatchDate().format(DateTimeFormatter.ISO_LOCAL_DATE).equals(splitToDate))
            throw new MatchDateException("Wrong match date for these clubs");

        MatchEntity save = matchRepository.save(toEntity(matchDTO));

        matchDTO.setId(save.getId());
        return matchDTO;
    }

    private MatchEntity toEntity(MatchDTO matchDTO) {
        MatchEntity entity = new MatchEntity();

        entity.setClubAId(matchDTO.getClubAId());
        entity.setClubBId(matchDTO.getClubBId());
        entity.setLeagueId(matchDTO.getLeagueId());
        entity.setClubAScore(matchDTO.getClubAScore());
        entity.setClubBScore(matchDTO.getClubBScore());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime matchDateDTO = LocalDateTime.parse(matchDTO.getMatchDate(), formatter);
        entity.setMatchDate(matchDateDTO);

        return entity;
    }

    public boolean delete(int id) {
        Optional<MatchEntity> byId = matchRepository.findById(id);

        if (byId.isEmpty())
            return false;

        matchRepository.delete(byId.get());
        return true;
    }

    public List<MatchEntity> getPrevGames(LocalDateTime now) {
        List<MatchEntity> prevMatches = matchRepository.getPrevMatches(now);

        return prevMatches;
    }
}
