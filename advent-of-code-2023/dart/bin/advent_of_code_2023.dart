import 'challenge_day.dart';
import 'day01.dart';
import 'day02.dart';
import 'day03.dart';

void main(List<String> arguments) {
  void printResult(ChallengeDay day) {
    print(day.message());
    print('part1: ${day.part1()}');
    print('part2: ${day.part2()}');
  }

  final List<ChallengeDay> days = [
    Day01.using("input/day01-dr.txt"),
    Day02.using("input/day02-dr.txt"),
    Day03.using("input/day03-dr.txt")
  ];
  for (var day in days) {
    printResult(day);
  }
}
