package hzt.aoc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

import static hzt.Launcher2020.DOTTED_LINE;

public final class ChallengeDay {

    private static final AocLogger LOGGER = AocLogger.getLogger(ChallengeDay.class);

    private final String textColor;
    private final String title;
    private final LocalDate date;
    private final Challenge[] challenges;

    public ChallengeDay(final String title, final String textColor, final LocalDate date, final Challenge... challenges) {
        this.title = title;
        this.textColor = textColor;
        this.date = date;
        this.challenges = Arrays.copyOf(challenges, challenges.length);
        for(final var challenge : challenges) {
            challenge.setTitle(title);
        }
    }

    public void solveChallenges() {
        LOGGER.info(String.format("%n%n%s%s%nDay %d: %s%nDate: %s%n%s", textColor,
                DOTTED_LINE, date.getDayOfMonth(), title, date.format(DateTimeFormatter.ISO_DATE), DOTTED_LINE));
        for (final var challenge : challenges) {
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
