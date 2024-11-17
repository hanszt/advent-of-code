import 'dart:io';
import 'dart:math';

import 'challenge_day.dart';

class Day02 implements ChallengeDay {
  final List<String> _lines;

  Day02(this._lines);

  Day02.using(String fileName) : _lines = File(fileName).readAsLinesSync();

  @override
  int part1() => _lines
      .map((line) => Game.parse(line))
      .where((game) => game.isPossible())
      .fold(0, (sum, game) => sum + game._id);

  @override
  int part2() => _lines
      .map((line) => Game.parse(line))
      .fold(0, (sum, game) => sum + game.toPowerFewestCubes());

  @override
  String message() => 'Day 02: ';
}

class Game {
  final int _id;
  final List<String> _rounds;

  Game(this._id, this._rounds);

  int toPowerFewestCubes() {
    int redCount = 1;
    int blueCount = 1;
    int greenCount = 1;
    for (var round in _rounds) {
      final List<List<String>> actions = toGameActions(round);
      for (final List<String> action in actions) {
        final int count = int.parse(action.first);
        final String color = action.last;
        switch (color) {
          case "red":
            redCount = max(count, redCount);
          case "green":
            greenCount = max(count, greenCount);
          case "blue":
            blueCount = max(count, blueCount);
        }
      }
    }
    return redCount * blueCount * greenCount;
  }

  bool isPossible() {
    for (var round in _rounds) {
      var redCount = 0;
      var blueCount = 0;
      var greenCount = 0;
      final List<List<String>> actions = toGameActions(round);
      for (var action in actions) {
        final int count = int.parse(action.first);
        final String color = action.last;
        switch (color) {
          case "red":
            redCount += count;
          case "green":
            greenCount += count;
          case "blue":
            blueCount += count;
        }
        if (redCount > 12 || greenCount > 13 || blueCount > 14) {
          return false;
        }
      }
    }
    return true;
  }

  static List<List<String>> toGameActions(String round) =>
      round.split(", ").map((a) => a.split(" ")).toList();

  static Game parse(String line) {
    final List<String> split = line.split(": ");
    final int id = int.parse(split.first.substring("Game ".length));
    final List<String> subSets = split.last.split("; ").toList();
    return Game(id, subSets);
  }
}
