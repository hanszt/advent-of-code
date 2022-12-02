package hzt.aoc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

import static hzt.Launcher.DOTTED_LINE;

public final class ChallengeDay {

    private static final Logger LOGGER = LogManager.getLogger(ChallengeDay.class);

    private final String textColor;
    private final String title;
    private final LocalDate date;
    private final Challenge[] challenges;

    public ChallengeDay(final String title, final String textColor, final LocalDate date, final Challenge... challenges) {
        this.title = title;
        this.textColor = textColor;
        this.date = date;
        this.challenges = challenges;
        for(final Challenge challenge : challenges) {
            challenge.setTitle(title);
        }
    }

    public void solveChallenges() {
        LOGGER.info(String.format("%n%n%s%s%nDay %d: %s%nDate: %s%n%s", textColor,
                DOTTED_LINE, date.getDayOfMonth(), title, date.format(DateTimeFormatter.ISO_DATE), DOTTED_LINE));
        for (final Challenge challenge : challenges) {
            challenge.solveChallenge();
        }
    }

    public long getSolveTime() {
        return Arrays.stream(challenges).map(Challenge::getSolveTime).reduce(0L, Long::sum);
    }

    public String getTitle() {
        return title;
    }

    public Stream<Challenge> challengesAsStream() {
        return Stream.of(challenges);
    }

    public int getDayOfMonth() {
        return date.getDayOfMonth();
    }
}
