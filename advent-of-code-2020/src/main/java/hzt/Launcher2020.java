package hzt;

import hzt.aoc.AocLogger;
import hzt.aoc.Challenge;
import hzt.aoc.ChallengeDay;
import hzt.aoc.day01.Part1ReportRepair;
import hzt.aoc.day01.Part2ReportRepair;
import hzt.aoc.day02.Part1PasswordPhilosophy;
import hzt.aoc.day02.Part2PasswordPhilosophy;
import hzt.aoc.day03.TreesEncounteredPart1;
import hzt.aoc.day03.TreesEncounteredPart2;
import hzt.aoc.day04.PassportProcessingPart1;
import hzt.aoc.day04.PassportProcessingPart2;
import hzt.aoc.day05.Part1BinaryBoarding;
import hzt.aoc.day05.Part2BinaryBoarding;
import hzt.aoc.day06.Part1CustomCustoms;
import hzt.aoc.day06.Part2CustomCustoms;
import hzt.aoc.day07.Part1HandyHaversacks;
import hzt.aoc.day07.Part2HandyHaversacks;
import hzt.aoc.day08.Part1HandheldHalting;
import hzt.aoc.day08.Part2HandheldHalting;
import hzt.aoc.day09.Part1EncodingError;
import hzt.aoc.day09.Part2EncodingError;
import hzt.aoc.day10.Part1AdaptorArray;
import hzt.aoc.day10.Part2AdaptorArrayWithCaching;
import hzt.aoc.day10.Part2AdaptorArrayWithCachingLongs;
import hzt.aoc.day10.Part2AdaptorArrayWithoutCaching;
import hzt.aoc.day11.Part1SeatingSystem;
import hzt.aoc.day11.Part2SeatingSystem;
import hzt.aoc.day12.Part1RainRisk;
import hzt.aoc.day12.Part2RainRisk;
import hzt.aoc.day13.Part1ShuttleSearch;
import hzt.aoc.day13.Part2ShuttleSearch;
import hzt.aoc.day13.Part2ShuttleSearchOwnImpl;
import hzt.aoc.day14.Part1DockingData;
import hzt.aoc.day14.Part2DockingData;
import hzt.aoc.day15.Part1RambunctiousRecitation;
import hzt.aoc.day15.Part2RambunctiousRecitation;
import hzt.aoc.day16.Part1TicketTranslation;
import hzt.aoc.day16.Part2TicketTranslation;
import hzt.aoc.day17.Part1ConwayCubesByArrays;
import hzt.aoc.day17.Part2ConwayCubesByArrays;
import hzt.aoc.day18.Part1OperationOrder;
import hzt.aoc.day18.Part2OperationOrder;
import hzt.aoc.day19.Part1MonsterMessages;
import hzt.aoc.day19.Part2MonsterMessages;
import hzt.aoc.day20.Part1JurassicJigsaw;
import hzt.aoc.day20.Part2JurassicJigsaw;
import hzt.aoc.day21.Part1AllergenAssessment;
import hzt.aoc.day21.Part2AllergenAssessment;
import hzt.aoc.day22.Part1CrabCombat;
import hzt.aoc.day22.Part2CrabCombat;
import hzt.aoc.day23.Part1CrabCups;
import hzt.aoc.day23.Part2CrabCups;
import hzt.aoc.day24.Part1LobbyLayout;
import hzt.aoc.day24.Part2LobbyLayout;
import hzt.aoc.day25.Part1ComboBreaker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.*;

/**
 * This is a project to participate in the Advent of code 2020.
 * This event was held between 01-12-2020 and 25-12-2020
 *
 * @author Hans Zuidervaart,
 */
public class Launcher2020 implements Runnable {

    private static final Pattern NUMBER_LENGTH_ONE_OR_MORE = Pattern.compile("\\d+");
    public static final String DOTTED_LINE = "___________________________________________________________________";
    private static final AocLogger LOGGER = AocLogger.getLogger(Launcher2020.class);
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String TITTLE = RED + loadBanner() + RESET;

    private static final String EXIT = "e";
    private static final String ALL = "a";
    private static final String CLEAR = "c";
    private static final String TRACE = "t";
    private static final String INFO = "i";

    private final Map<Integer, ChallengeDay> challengeDays = new LinkedHashMap<>();


    public Launcher2020() {
        populateChallengeDaysMap(challengeDays);
    }

    private static String loadBanner() {
        final var name = "/banner.txt";
        final var path = Optional.ofNullable(Launcher2020.class.getResource(name))
                .map(URL::getFile)
                .map(File::new)
                .map(File::toPath)
                .orElseThrow(() -> new IllegalStateException("Could not load " + name));
        try (final var lines = Files.lines(path)) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


    private static LocalDate dateOfDay(final int day) {
        return LocalDate.of(2020, 12, day);
    }

    private static void populateChallengeDaysMap(final Map<Integer, ChallengeDay> challengeDays) {
        int day = 0;
        challengeDays.put(++day, new ChallengeDay("Report Repair", BRIGHT_BLUE, dateOfDay(day),
                new Part1ReportRepair(), new Part2ReportRepair()));
        challengeDays.put(++day, new ChallengeDay("Password Philosophy", GREEN, dateOfDay(day),
                new Part1PasswordPhilosophy(), new Part2PasswordPhilosophy()));
        challengeDays.put(++day, new ChallengeDay("Toboggan Trajectory", CYAN, dateOfDay(day),
                new TreesEncounteredPart1(), new TreesEncounteredPart2()));
        challengeDays.put(++day, new ChallengeDay("Passport Processing", YELLOW, dateOfDay(day),
                new PassportProcessingPart1(), new PassportProcessingPart2()));
        challengeDays.put(++day, new ChallengeDay("Binary Boarding", RED, dateOfDay(day),
                new Part1BinaryBoarding(), new Part2BinaryBoarding()));
        challengeDays.put(++day, new ChallengeDay("Custom Customs", BRIGHT_BLUE, dateOfDay(day),
                new Part1CustomCustoms(), new Part2CustomCustoms()));
        challengeDays.put(++day, new ChallengeDay("Handy Haversacks", GREEN, dateOfDay(day),
                new Part1HandyHaversacks(), new Part2HandyHaversacks()));
        challengeDays.put(++day, new ChallengeDay("Handheld Halting", CYAN, dateOfDay(day),
                new Part1HandheldHalting(), new Part2HandheldHalting()));
        challengeDays.put(++day, new ChallengeDay("Encoding Error", YELLOW, dateOfDay(day),
                new Part1EncodingError(), new Part2EncodingError()));
        challengeDays.put(++day, new ChallengeDay("Adaptor Array", RED, dateOfDay(day),
                new Part1AdaptorArray(), new Part2AdaptorArrayWithCaching(),
                new Part2AdaptorArrayWithCachingLongs(), new Part2AdaptorArrayWithoutCaching()));
        challengeDays.put(++day, new ChallengeDay("Seating System", BRIGHT_BLUE, dateOfDay(day),
                new Part1SeatingSystem(), new Part2SeatingSystem()));
        challengeDays.put(++day, new ChallengeDay("Rain Risk", GREEN, dateOfDay(day),
                new Part1RainRisk(), new Part2RainRisk()));
        challengeDays.put(++day, new ChallengeDay("Shuttle Search", CYAN, dateOfDay(day),
                new Part1ShuttleSearch(), new Part2ShuttleSearchOwnImpl(), new Part2ShuttleSearch()));
        challengeDays.put(++day, new ChallengeDay("Docking Data", YELLOW, dateOfDay(day),
                new Part1DockingData(), new Part2DockingData()));
        challengeDays.put(++day, new ChallengeDay("Rambunctious Recitation", RED, dateOfDay(day),
                new Part1RambunctiousRecitation(), new Part2RambunctiousRecitation()));
        challengeDays.put(++day, new ChallengeDay("Ticket Translation", BRIGHT_BLUE, dateOfDay(day),
                new Part1TicketTranslation(), new Part2TicketTranslation()));
        challengeDays.put(++day, new ChallengeDay("Conway Cubes", GREEN, dateOfDay(day),
                new Part1ConwayCubesByArrays(), new Part2ConwayCubesByArrays()));
        challengeDays.put(++day, new ChallengeDay("Operation Order", CYAN, dateOfDay(day),
                new Part1OperationOrder(), new Part2OperationOrder()));
        challengeDays.put(++day, new ChallengeDay("Monster Messages", YELLOW, dateOfDay(day),
                new Part1MonsterMessages(), new Part2MonsterMessages()));
        challengeDays.put(++day, new ChallengeDay("Jurassic Jigsaw", RED, dateOfDay(day),
                new Part1JurassicJigsaw(), new Part2JurassicJigsaw()));
        challengeDays.put(++day, new ChallengeDay("Allergen Assessment", BRIGHT_BLUE, dateOfDay(day),
                new Part1AllergenAssessment(), new Part2AllergenAssessment()));
        challengeDays.put(++day, new ChallengeDay("Crab Combat", GREEN, dateOfDay(day),
                new Part1CrabCombat(), new Part2CrabCombat()));
        challengeDays.put(++day, new ChallengeDay("Crab Cups", CYAN, dateOfDay(day),
                new Part1CrabCups(), new Part2CrabCups()));
        challengeDays.put(++day, new ChallengeDay("Lobby Layout", YELLOW, dateOfDay(day),
                new Part1LobbyLayout(), new Part2LobbyLayout()));
        challengeDays.put(++day, new ChallengeDay("Combo Breaker", RED, dateOfDay(day),
                new Part1ComboBreaker()));
    }

    public static void main() {
        new Launcher2020().run();
    }

    public static void runOnce() {
        final long startTime = nanoTime();
        LOGGER.info(String.format("%n%s", TITTLE));
        new Launcher2020().challengeDays.values().forEach(ChallengeDay::solveChallenges);
        final long runtime = nanoTime() - startTime;
        out.printf("%s%nRuntime: %2.3f seconds%n%s%n", GREEN, runtime / 1e9, DOTTED_LINE);
    }

    public void run() {
        String userInput = ALL;
        final long startTime = nanoTime();
        LOGGER.info(String.format("%n%s", TITTLE));
        while (!userInput.equals(EXIT)) {
            pressEnterToContinue();
            out.print(menuAsString());
            userInput = execute(new Scanner(in).next());
        }
        final long runtime = nanoTime() - startTime;
        out.printf("%s%nRuntime: %2.3f seconds%n%s%n", GREEN, runtime / 1e9, DOTTED_LINE);
    }

    private void executeAllAndPrintSummary() {
        challengeDays.values().forEach(ChallengeDay::solveChallenges);
        final long totalSolveTime = challengeDays.values().stream()
                .map(ChallengeDay::getSolveTime).reduce(Long::sum).orElseThrow();
        LOGGER.info(sortedSolveTimesAsString(new ArrayList<>(challengeDays.values())));
        LOGGER.info(String.format("%s%nTotal solve time: %.2f seconds%n%s%n",
                DOTTED_LINE, totalSolveTime / 1e9, DOTTED_LINE));
    }

    private String execute(final String input) {
        if (input.equals(EXIT)) {
            return EXIT;
        } else if (input.equals(ALL)) {
            executeAllAndPrintSummary();
        } else if (input.equals(CLEAR)) {
            clearAnswers();
        } else if (input.equals(INFO)) {
            setChallengeLoggerToLevel(Logger.Level.INFO);
        } else if (input.equals(TRACE)) {
            setChallengeLoggerToLevel(Logger.Level.TRACE);
        } else if (NUMBER_LENGTH_ONE_OR_MORE.matcher(input).matches()) {
            executeByChallengeNumber(input);
        } else {
            LOGGER.info("You didn't enter a valid option...");
        }
        return input;
    }

    private void clearAnswers() {
        challengeDays.values().forEach(day -> day.challengesAsStream().forEach(Challenge::clearAnswer));
        out.println("Answers cleared");
    }

    private void setChallengeLoggerToLevel(final Logger.Level level) {
        Challenge.LOGGER.setLevel(level);
        clearAnswers();
        out.println("Challenge Logger level set to " + level.toString());
    }

    private void executeByChallengeNumber(final String input) {
        final int dayNr = Integer.parseInt(input);
        if (challengeDays.containsKey(dayNr)) {
            challengeDays.get(dayNr).solveChallenges();
        } else {
            out.println("The selected number is not in the challenge list...");
        }
    }

    private String menuAsString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%n"));
        challengeDays.forEach((dayNr, day) -> sb.append(menuOption(dayNr, day.getTitle())));
        sb.append(String.format("Enter '%s'  and press 'Enter' to execute all challenges at once.%n", ALL));
        sb.append(String.format("Enter '%s'  and press 'Enter' to clear answers.%n", CLEAR));
        sb.append(String.format("Enter '%s'  and press 'Enter' to set the logging level to 'INFO'.%n", INFO));
        sb.append(String.format("Enter '%s'  and press 'Enter' to set the logging level to 'TRACE'.%n", TRACE));
        sb.append(String.format("Enter '%s'  and press 'Enter' to exit the program.%n", EXIT));
        sb.append("Your input: ");
        return sb.toString();
    }

    private static String menuOption(final int dayNr, final String title) {
        return String.format("Enter '%2d' and press 'Enter' to execute day %2d %s.%n", dayNr, dayNr, title);
    }

    private static String sortedSolveTimesAsString(final List<ChallengeDay> challengeDays) {
        record Pair<A, B>(A left, B right) {
        }
        final var challenges = challengeDays.stream()
                .map(day -> day.challengesAsStream().map(c -> new Pair<>(c, day)))
                .flatMap(Stream::distinct)
                .sorted(Comparator.comparing(pair -> pair.left.getSolveTime()))
                .toList();

        final StringBuilder sb = new StringBuilder();
        sb.append(RESET);
        sb.append(String.format("%nChallenges sorted by solve time:%n"));
        for (final var p : challenges) {
            final var challenge = p.left;
            final var day = p.right;
            sb.append(String.format("Day %2d Challenge: %-50s, answer: %-50s, solve time: %8.3f milliseconds%n",
                    day.getDayOfMonth(),
                    day.getTitle() + " " + challenge.getPart(),
                    challenge.getAnswer(),
                    challenge.getSolveTime() / 1e6));
        }
        return sb.toString();
    }

    private static void pressEnterToContinue() {
        out.printf("%n%sPress Enter key to continue...%s", GREEN, RESET);
        new Scanner(in).nextLine();
    }
}
