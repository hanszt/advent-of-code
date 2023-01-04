module advent.of.code.utils {

    requires kotlin.stdlib;
    requires org.jetbrains.annotations;
    requires hzt.utils.core;

    exports aoc.utils;
    exports aoc.utils.model;

    opens aoc.utils;
    opens aoc.utils.model;

}
