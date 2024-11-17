import 'dart:io';

import 'challenge_day.dart';
import 'utils.dart';

class Day01 implements ChallengeDay {
  final List<String> _lines;

  Day01(this._lines);

  Day01.using(String fileName) : _lines = File(fileName).readAsLinesSync();

  @override
  int part1() {
    return _lines
        .map((e) => toFirstAndLastDigitInt(e))
        .reduce((value, element) => value + element);
  }

  @override
  int part2() {
    var digitNames = [
      "one",
      "two",
      "three",
      "four",
      "five",
      "six",
      "seven",
      "eight",
      "nine"
    ];
    var sum = 0;
    for (var line in _lines) {
      final List<int> digits = [];
      for (int i = 0; i < line.length; i++) {
        var char = line[i];
        if (char.isDigit()) {
          digits.add(int.parse(char));
        }
        for (int j = 0; j < digitNames.length; j++) {
          var name = digitNames[j];
          if (line.substring(i).startsWith(name)) {
            digits.add(j + 1);
          }
        }
      }
      sum += int.parse("${digits.first}${digits.last}");
    }
    return sum;
  }

  int toFirstAndLastDigitInt(String line) {
    var chars = line.split("");
    var first = chars.firstWhere((char) => char.isDigit());
    var last = chars.lastWhere((char) => char.isDigit());
    return int.parse(first + last);
  }

  @override
  String message() => 'day 01: ';
}
