package hzt.aoc;

import hzt.aoc.io.IOController2;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import static hzt.Launcher.DOTTED_LINE;

public abstract class Challenge {

    @SuppressWarnings("squid:S1312")
    public static final Logger LOGGER = LogManager.getLogger(Challenge.class);

    protected static final Pattern COMMA_PATTERN = Pattern.compile(",");
    protected static final Pattern NUMBER_LENGTH_ONE_OR_MORE = Pattern.compile("\\d+");
    protected static final Pattern NOT_DIGIT_LENGTH_ONE_OR_MORE = Pattern.compile("\\D+");

    private String title;
    private final String part;
    private final String description;
    private final String inputFileName;
    private long solveTime = 0;
    private ZonedDateTime startTimeSolve;
    private String answer;

    protected Challenge(final String part, final String description, final String inputFileName) {
        this.title = "test";
        this.part = part;
        this.description = description;
        this.inputFileName = inputFileName;
    }

    public void solveChallenge() {
        LOGGER.info(String.format("%n%s %s%nInput: %s%nChallenge: %s%n%s",
                title, part, inputFileName, description, DOTTED_LINE));
        final List<String> inputList = loadInputList();
        final long startTime = System.nanoTime();
        if (!inputList.isEmpty()) {
            if (answer == null) {
                startTimeSolve = ZonedDateTime.now();
                answer = solve(inputList);
                final long endTime = System.nanoTime();
                solveTime = endTime - startTime;
            }
            logResult(answer);
            final String message = String.format("%nSolved at %s%nSolved in %5.5f ms%n",
                    startTimeSolve.format(DateTimeFormatter.ofPattern("HH:mm:ss VV")), solveTime / 1e6);
            LOGGER.info(message + DOTTED_LINE);
        }
    }

    protected List<Integer> commaSeparatedStringToIntegerList(final String s) {
        return COMMA_PATTERN.splitAsStream(s).map(Integer::parseInt).toList();
    }

    protected String listOfStringListsAsString(final List<List<String>> listOfStringLists) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%n"));
        for (final List<String> row : listOfStringLists) {
            for (final String s : row) {
                sb.append(s).append(", ");
            }
            sb.append(String.format("%n"));
        }
        sb.append(String.format("%n"));
        return sb.toString();
    }

    protected String booleanGrid2DAsString(final List<List<Boolean>> grid) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%n"));
        for (final List<Boolean> row : grid) {
            for (final boolean active : row) {
                sb.append(active ? 1 : 0).append(", ");
            }
            sb.append(String.format("%n"));
        }
        sb.append(String.format("%n"));
        return sb.toString();
    }

    protected String booleanGrid2DAsString(final boolean[][] grid) {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%n"));
        for (final boolean[] row : grid) {
            for (final boolean active : row) {
                sb.append(active ? 1 : 0).append(", ");
            }
            sb.append(String.format("%n"));
        }
        sb.append(String.format("%n"));
        return sb.toString();
    }

    protected List<String> loadInputList() {
        return new IOController2().readInputFileByLine(inputFileName);
    }

    protected abstract String solve(List<String> inputList);

    protected String getMessage(final String result) {
        return result;
    }

    protected void logResult(final String result) {
        LOGGER.info(String.format("%s%nAnswer:%n%s", DOTTED_LINE, getMessage(result)));
    }

    public long getSolveTime() {
        return solveTime;
    }

    public String getPart() {
        return part;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void clearAnswer() {
        answer = null;
    }

    public String getAnswer() {
        return answer;
    }
}
