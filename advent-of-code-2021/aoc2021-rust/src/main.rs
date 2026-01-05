use aoc2021_rust::day02;
use aoc2021_rust::{day01, day18snailfish};

fn main() {
    println!("\nDay 1");
    let input_day1 = include_str!("../../input/day1.txt");
    println!("part 1: {}", day01::part_1(input_day1));
    println!("part 2: {}", day01::part_2(input_day1));

    println!("\nDay 2");
    let input_day2 = include_str!("../../input/day2.txt");
    println!("part 1: {}", day02::part_1(input_day2));
    println!("part 2: {}", day02::part_2(input_day2));

    println!("\nDay 18 Snailfish");
    let input_day18 = include_str!("../../input/day18.txt");
    println!("part 1: {}", day18snailfish::part_1(input_day18));
    println!("part 2: {}", day18snailfish::part_2(input_day18));
}
