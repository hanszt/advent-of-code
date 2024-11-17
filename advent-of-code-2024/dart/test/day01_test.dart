import 'package:test/test.dart';

import '../bin/day01.dart';

void main() {

  var day01 = Day01.using("../input/day01.txt");

  test('test day 1 part 1', () {
    expect(day01.part1(), 55172);
  });

  test('test day 1 part 2', () {
    expect(day01.part2(), 54925);
  });
}
