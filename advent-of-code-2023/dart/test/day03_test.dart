import 'package:test/test.dart';

import '../bin/day03.dart';

void main() {

  var day03 = Day03.using("../input/day03.txt");

  test('day 3 test part 1', () {
    expect(day03.part1(), 537832);
  });

  test('day 3 test part 2', () {
    expect(day03.part2(), 81939900);
  });
}
