package hzt.aoc;


import java.util.function.Supplier;

public interface AocLogger {

    static AocLogger getLogger(final Class<?> aClass) {
        return new AocLoggerImpl(aClass.getSimpleName());
    }


    void info(Object s);

    void setLevel(System.Logger.Level level);

    void trace(Supplier<Object> s);

    void error(Object s);

    void error(Object s, Throwable throwable);

}
