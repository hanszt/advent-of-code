pub mod day01sonar_sweep;
pub mod day02dive;
pub mod day15chiton;
pub mod day18snailfish;
pub mod day23amphipod;
pub mod day13transparent_origami;
pub mod day21dirac_dice;

pub trait AocDay<R1, R2> {
    fn part_1(input: &str) -> R1;
    fn part_2(input: &str) -> R2;
}
