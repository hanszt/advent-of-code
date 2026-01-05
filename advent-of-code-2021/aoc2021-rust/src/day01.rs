pub fn part_1(input: &str) -> usize {
    to_depths(input)
        .windows(2)
        .filter(|window| window[1] > window[0])
        .count()
}

pub fn part_2(input: &str) -> i64 {
    let depths = to_depths(input);
    let mut increases = 0;
    for window in depths.windows(4) {
        if window[3] > window[0] {
            increases += 1;
        }
    }
    increases
}

fn to_depths(input: &str) -> Vec<i64> {
    input
        .trim()
        .split('\n')
        .map(|l| l.parse::<i64>().unwrap())
        .collect()
}

#[cfg(test)]
mod tests {
    #[test]
    fn part_1() {
        assert_eq!(super::part_1(include_str!("../../input/day1.txt")), 1_722);
    }

    #[test]
    fn part_2() {
        assert_eq!(super::part_2(include_str!("../../input/day1.txt")), 1_748);
    }
}
