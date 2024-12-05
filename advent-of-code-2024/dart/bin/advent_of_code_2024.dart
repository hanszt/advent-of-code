import 'challenge_day.dart';
import 'day04.dart';

void main(List<String> arguments) {
  void printResult(ChallengeDay day) {
    print(day.message());
    print('part1: ${day.part1()}');
    print('part2: ${day.part2()}');
  }

  final List<ChallengeDay> days = [
    Day04.using("input/day04.txt"),
  ];
  for (var day in days) {
    printResult(day);
  }
}
