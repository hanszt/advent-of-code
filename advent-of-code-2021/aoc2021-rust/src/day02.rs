pub fn part_1(input: &str) -> i64 {
    let mut x = 0;
    let mut y = 0;

    for line in input.trim().split('\n') {
        let mut parts = line.split(' ');
        let direction = parts.next().unwrap();
        let amount: i64 = parts.next().unwrap().parse().unwrap();
        match direction {
            "forward" => x += amount,
            "down" => y += amount,
            "up" => y -= amount,
            _ => panic!(),
        }
    }
    x * y
}

pub fn part_2(input: &str) -> i64 {
    let mut x = 0;
    let mut y = 0;
    let mut aim = 0;

    for line in input.trim().split('\n') {
        let mut parts = line.split(' ');
        let direction = parts.next().unwrap();
        let amount: i64 = parts.next().unwrap().parse().unwrap();
        match direction {
            "forward" => {
                x += amount;
                y += aim * amount;
            }
            "down" => aim += amount,
            "up" => aim -= amount,
            _ => panic!(),
        }
    }
    x * y
}

#[cfg(test)]
mod tests {

    #[test]
    fn part_1() {
        assert_eq!(2_019_945, super::part_1(include_str!("../../input/day2.txt")));
    }

    #[test]
    fn part_2() {
        assert_eq!(1_599_311_480, super::part_2(include_str!("../../input/day2.txt")));
    }
}
