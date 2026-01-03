use aoc2021_rust::day01;
use aoc2021_rust::day02;

fn main() {
    let input_day1 = include_str!("../../input/day1.txt");
    let input_day2 = include_str!("../../input/day2.txt");

    println!("Day 1, part 1: {}", day01::part_1(input_day1));
    println!("Day 1, part 2: {}", day01::part_2(input_day1));

    println!("Day 2, part 1: {}", day02::part_1(input_day2));
    println!("Day 2, part 2: {}", day02::part_2(input_day2));
}