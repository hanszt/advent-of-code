use aoc2021_rust::{day01, day15chiton, day18snailfish};
use aoc2021_rust::{day02, day23amphipod};

fn main() {
    println!("\nDay 1");
    let input_day1 = include_str!("../../input/day1.txt");
    println!("part 1: {}", day01::part_1(input_day1));
    println!("part 2: {}", day01::part_2(input_day1));

    println!("\nDay 2");
    let input_day2 = include_str!("../../input/day2.txt");
    println!("part 1: {}", day02::part_1(input_day2));
    println!("part 2: {}", day02::part_2(input_day2));

    println!("\nDay 15 chiton");
    let input_day15 = include_str!("../../input/day15.txt");
    println!("part 1: {}", day15chiton::part_1(input_day15));
    println!("part 2: {}", day15chiton::part_2(input_day15));

    println!("\nDay 18 Snailfish");
    let input_day18 = include_str!("../../input/day18.txt");
    println!("part 1: {}", day18snailfish::part_1(input_day18));
    println!("part 2: {}", day18snailfish::part_2(input_day18));

    println!("\nDay 23 Amphipod");
    let input_day23 = include_str!("../../input/day23.txt");
    println!("part 1: {}", day23amphipod::part_1(input_day23));
    println!("part 2: {}", day23amphipod::part_2(input_day23));
}
