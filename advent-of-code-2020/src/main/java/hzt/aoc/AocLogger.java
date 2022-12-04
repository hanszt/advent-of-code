package hzt.aoc;


public interface AocLogger {

    static AocLogger getLogger(Class<?> aClass) {
        return new AocLoggerImpl(aClass.getSimpleName());
    }


    void info(Object s);

    void setLevel(System.Logger.Level level);

    void trace(Object s);

    void error(Object s);

    void error(Object s, Throwable throwable);

    boolean isTraceEnabled();

}
