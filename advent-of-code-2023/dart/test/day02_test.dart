import 'package:test/test.dart';

import '../bin/day02.dart';

void main() {

  var day02 = Day02.using("../input/day02-dr.txt");

  test('day 2 test part 1', () {
    expect(day02.part1(), 2679);
  });

  test('day 2 test part 2', () {
    expect(day02.part2(), 77607);
  });
}
