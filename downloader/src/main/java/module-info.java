module org.hzt.aoc.downloader {
    exports org.hzt.aoc;
    opens org.hzt.aoc;
    requires static java.net.http;
}