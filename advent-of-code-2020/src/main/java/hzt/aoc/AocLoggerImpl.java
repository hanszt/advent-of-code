package hzt.aoc;

import java.util.function.Supplier;

@SuppressWarnings(AocLoggerImpl.STANDARD_OUTPUTS_SHOULD_NOT_BE_USED_FOR_LOGGING)
public final class AocLoggerImpl implements AocLogger {

    static final String STANDARD_OUTPUTS_SHOULD_NOT_BE_USED_FOR_LOGGING = "squid:S106";
    private final String simpleName;

    private System.Logger.Level level = System.Logger.Level.INFO;

    AocLoggerImpl(final String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public void trace(final Supplier<Object> supplier) {
        if (level.getSeverity() <= System.Logger.Level.TRACE.getSeverity()) {
            System.out.println(supplier.get());
        }
    }

    @Override
    public void info(final Object s) {
        if (level.getSeverity() <= System.Logger.Level.INFO.getSeverity()) {
            System.out.println(s);
        }
    }

    @Override
    public void error(final Object s) {
        if (level.getSeverity() <= System.Logger.Level.ERROR.getSeverity()) {
            System.err.println(simpleName + " - " + s);
        }
    }

    @Override
    public void error(final Object s, final Throwable throwable) {
        if (level.getSeverity() <= System.Logger.Level.ERROR.getSeverity()) {
            System.err.println(simpleName + " - " + s);
            throwable.printStackTrace();
        }
    }

    @Override
    public void setLevel(final System.Logger.Level level) {
        this.level = level;
    }
}
