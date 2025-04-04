package aoc.utils;

public class ColorTablePrinter {

    /**
     * Main class only works in the java source folder.
     * If a main class is created in the kotlin src folder, it complaints about not being able to find some package in the module.
     */
    public static void main(String[] args) {
        ColorsKt.printTable(Colors.INSTANCE.getBgColorTable16Bit$aoc_utils());
    }
}
