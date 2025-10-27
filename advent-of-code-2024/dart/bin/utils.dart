extension NumberParsing on String {
  bool isDigit() => int.tryParse(this) != null;

  bool isLetter() {
    if (length != 1) {
      return false;
    }
    int rune = codeUnitAt(0);
    return (rune >= 0x41 && rune <= 0x5A) || (rune >= 0x61 && rune <= 0x7A);
  }

  bool isLetterOrDigit() => isLetter() || isDigit();
}
