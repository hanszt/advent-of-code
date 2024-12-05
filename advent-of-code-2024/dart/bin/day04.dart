import 'dart:io';

import 'challenge_day.dart';

class Day04 implements ChallengeDay {
  final List<String> _lines;

  Day04(this._lines);

  Day04.using(String fileName) : _lines = File(fileName).readAsLinesSync();

  final List<Point> _kingDirs = [
    Point(0, -1),
    Point(1, -1),
    Point(1, 0),
    Point(1, 1),
    Point(0, 1),
    Point(-1, 1),
    Point(-1, 0),
    Point(-1, -1)
  ];

  @override
  int part1() {
    final String target = "XMAS";
    var result = 0;
    for (var y = 0; y < _lines.length; y++) {
      var line = _lines[y];
      var rowLength = line.length;
      for (var x = 0; x < rowLength; x++) {
        for (var dir in _kingDirs) {
          final StringBuffer buffer = StringBuffer();
          for (var k = 0; k < target.length; k++) {
            var dx = x + (dir.x * k);
            var dy = y + (dir.y * k);
            if (dx < 0 || dx >= rowLength) {
              break;
            }
            if (dy < 0 || dy >= _lines.length) {
              break;
            }
            buffer.write(_lines[dy][dx]);
          }
          var string = buffer.toString();
          if (string == target) {
            result++;
          }
        }
      }
    }
    return result;
  }

  final List<Point> _rookDirs = [
    Point(1, 1),
    Point(1, -1),
    Point(-1, 1),
    Point(-1, -1)
  ];

  @override
  int part2() {
    final String target = "MAS";
    var result = 0;
    for (var y = 0; y < _lines.length; y++) {
      var line = _lines[y];
      var rowLength = line.length;
      for (var x = 0; x < rowLength; x++) {
        var r = 0;
        for (var dir in _rookDirs) {
          if (r == 2) {
            break;
          }
          final StringBuffer buffer = StringBuffer();
          for (var k = -1; k <= 1; k++) {
            final int dx = x + (dir.x * k);
            final int dy = y + (dir.y * k);
            if (dx < 0 || dx >= rowLength) {
              break;
            }
            if (dy < 0 || dy >= _lines.length) {
              break;
            }
            buffer.write(_lines[dy][dx]);
          }
          var found = buffer.toString();
          if (found == target) {
            r++;
          }
        }
        if (r == 2) {
          result++;
        }
      }
    }
    return result;
  }

  @override
  String message() => 'day 04: ';
}

final class Point {
  final int x;
  final int y;

  Point(this.x, this.y);
}
