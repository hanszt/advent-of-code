pub mod day01;
pub mod day02;
pub mod day15chiton;
pub mod day18snailfish;
pub mod day23amphipod;

pub trait AocDay<R1, R2> {
    fn part_1(input: &str) -> R1;
    fn part_2(input: &str) -> R2;
}
