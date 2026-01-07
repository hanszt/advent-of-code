use aoc2021_rust::day01::Day01;
use aoc2021_rust::{day02, day15chiton, day18snailfish, day23amphipod};
use aoc2021_rust::{day13transparent_origami, AocDay};

fn main() {
    println!("\nDay 1");
    let input_day1 = include_str!("../../input/day1.txt");
    println!("part 1: {}", Day01::part_1(input_day1));
    println!("part 2: {}", Day01::part_2(input_day1));

    println!("\nDay 2");
    let input_day2 = include_str!("../../input/day2.txt");
    println!("part 1: {}", day02::part_1(input_day2));
    println!("part 2: {}", day02::part_2(input_day2));

    println!("\nDay 13 Transparent Origami");
    let input_day13 = include_str!("../../input/day13.txt");
    let (part_1, part_2) = day13transparent_origami::solve(input_day13);
    println!("part 1: {}", part_1);
    println!("part 2: {}", "\n".to_owned() + &*part_2);

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
