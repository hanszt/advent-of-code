module advent.of.code.utils {

    requires kotlin.stdlib;
    requires org.jetbrains.annotations;

    exports aoc.utils;
    exports aoc.utils.model;

    opens aoc.utils;
    opens aoc.utils.model;

}
