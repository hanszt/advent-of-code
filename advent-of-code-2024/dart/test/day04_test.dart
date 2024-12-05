import 'package:test/test.dart';

import '../bin/day04.dart';

void main() {

  var day04 = Day04.using("../input/day04.txt");

  test('test day 4 part 1', () {
    expect(day04.part1(), 2662);
  });

  test('test day 4 part 2', () {
    expect(day04.part2(), 2034);
  });
}
