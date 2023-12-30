import 'dart:collection';
import 'dart:io';

import 'ChallengeDay.dart';
import 'utils.dart';

class Day03 implements ChallengeDay {
  final List<String> _lines;

  Day03(this._lines);

  Day03.using(String fileName) : _lines = File(fileName).readAsLinesSync();

  @override
  int part1() {
    var sum = 0;
    for (var y = 0; y < _lines.length; y++) {
      var line = _lines[y];
      var x = 0;
      while (x < line.length) {
        final StringBuffer buffer = StringBuffer();
        var cdx = line[x];
        while (cdx.isDigit()) {
          buffer.write(cdx);
          x++;
          if (x >= line.length) {
            break;
          }
          cdx = line[x];
        }
        var digit = buffer.toString();
        sum += toPartNrOrZero(digit, y, x);
        x++;
      }
    }
    return sum;
  }

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

  int toPartNrOrZero(String digit, int y, int x) {
    for (var i = 0; i < digit.length; i++) {
      for (var dir in _kingDirs) {
        var dy = y + dir.y;
        if (dy < 0 || dy >= _lines.length) {
          continue;
        }
        var line = _lines[dy];
        var dx = x - digit.length + i + dir.x;
        if (dx < 0 || dx >= line.length) {
          continue;
        }
        var c = line[dx];
        if (!c.isLetterOrDigit() && c != '.') {
          return int.parse(digit);
        }
      }
    }
    return 0;
  }

  @override
  int part2() {
    var sum = 0;
    for (var y = 0; y < _lines.length; y++) {
      var line = _lines[y];
      for (var x = 0; x < line.length; x++) {
        var c = line[x];
        if (c == '*') {
          var locations = findAdjacentNrLocations(x, y);
          var isGear = locations.length == 2;
          if (isGear) {
            sum += toNr(locations.first) * toNr(locations.last);
          }
        }
      }
    }
    return sum;
  }

  @override
  String message() => 'Day 03: ';

  List<Point> findAdjacentNrLocations(int x, int y) {
    final List<Point> points = [];
    var nrs = HashSet<int>();
    for (var dir in _kingDirs) {
      var dx = x + dir.x;
      var dy = y + dir.y;
      if (dy < 0 || dy >= _lines.length) {
        continue;
      }
      var line = _lines[dy];
      if (dx < 0 || dx >= line.length) {
        continue;
      }
      var c = line[dx];
      if (c.isDigit()) {
          var point = Point(dx, dy);
        var nr = toNr(point);
        if (!nrs.contains(nr)) {
          nrs.add(nr);
          points.add(point);

        }
      }
    }
    return points;
  }

  int toNr(Point point) {
    var nrLine = _lines[point.y];
    var dx = point.x;
    while (true) {
      if (dx < 0 || dx >= nrLine.length) {
        break;
      }
      if (nrLine[dx].isDigit()) {
        dx--;
      } else {
        break;
      }
    }
    var stringBuffer = StringBuffer();
    var cNr = nrLine[++dx];
    while (cNr.isDigit()) {
      stringBuffer.write(cNr);
      dx++;
      if (dx < 0 || dx >= nrLine.length) {
        break;
      }
      cNr = nrLine[dx];
    }
    return int.parse(stringBuffer.toString());
  }
}

final class Point {
  final int x;
  final int y;

  Point(this.x, this.y);
}
