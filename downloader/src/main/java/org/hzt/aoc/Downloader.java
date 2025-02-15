package org.hzt.aoc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Clock;
import java.time.Instant;
import java.util.stream.IntStream;

/**
 * A utility to download the inputs for the aoc challenges by year.
 */
public final class Downloader {

    private final String sessionId;
    private final int year;
    private final Clock clock;

    public Downloader(final String sessionId, final int year, final Clock clock) {
        this.sessionId = sessionId;
        this.year = year;
        this.clock = clock;
    }

    public void downloadInputs() {
        final var httpClient = createHttpClient();
        IntStream.rangeClosed(1, 25)
                .filter(i -> Instant.parse(String.format("%d-12-%02dT05:00:00Z", year, i)).isBefore(clock.instant()))
                .forEach(i -> download(i, httpClient));
    }

    private void download(final int i, final HttpClient httpClient) {
        try {
            final var target = new File(String.format("input-%02d.txt", i));
            if (!target.exists()) {
                System.out.println("Downloading Day " + i);
                final var httpRequest = HttpRequest.newBuilder().GET()
                        .uri(new URI(String.format("https://adventofcode.com/%d/day/", year) + i + "/input"))
                        .build();
                final var data = httpClient.send(httpRequest, BodyHandlers.ofString()).body();
                try (final var pw = new PrintWriter(target)) {
                    pw.print(data);
                }
                System.out.println("Saved: " + target.getAbsolutePath());
            } else {
                System.out.println("Input: " + target.getAbsolutePath() + " already exists.");
            }
        } catch (final IOException | InterruptedException | URISyntaxException e) {
            throw new IllegalStateException("Could not download input: " + i, e);
        }
    }

    private HttpClient createHttpClient() {
        try {
            final var sessionCookie = new HttpCookie("session", sessionId);
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);
            final var cookieManager = new CookieManager();
            cookieManager.getCookieStore().add(new URI("https://adventofcode.com/"), sessionCookie);
            return HttpClient.newBuilder().cookieHandler(cookieManager).build();
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(final String[] args) {
        new Downloader("hzt", 2020, Clock.systemUTC()).downloadInputs();
    }

}