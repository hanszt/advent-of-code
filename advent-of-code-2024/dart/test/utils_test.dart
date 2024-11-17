import 'package:test/expect.dart';
import 'package:test/scaffolding.dart';

import '../bin/utils.dart';

void main() {

  for (int i = 0; i <= 100; i++) {

    test('test $i is digit', () {
      expect("$i".isDigit(), true);
    });
  }

  for (int c = 'A'.codeUnitAt(0); c <= 'Z'.codeUnitAt(0); c++) {
    var letter = String.fromCharCode(c);

    test('test `$letter` is a letter', () {
      expect(letter.isLetter(), true);
    });
  }

  for (int c = 'a'.codeUnitAt(0); c <= 'z'.codeUnitAt(0); c++) {
    var letter = String.fromCharCode(c);

    test('test `$letter` is a letter', () {
      expect(letter.isLetter(), true);
    });
  }

  final List<String> characters = ['!', '@', '#', '\$', '%', '^', '&', '*', '(', ')', '-', '+',];
  for (final String c in characters) {

    test('test $c is not a letter or digit', () {
      expect(c.isLetterOrDigit(), false);
    });
  }
}
