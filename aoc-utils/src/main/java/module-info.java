module advent.of.code.utils {

    requires kotlin.stdlib;
    requires org.jetbrains.annotations;

    exports aoc.utils;
    exports aoc.utils.grid2d;
    exports aoc.utils.grid3d;
    exports aoc.utils.graph;

    opens aoc.utils;
    opens aoc.utils.grid2d;
    opens aoc.utils.grid3d;

}
