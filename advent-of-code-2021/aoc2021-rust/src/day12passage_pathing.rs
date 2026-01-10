use std::collections::{HashMap, HashSet};

fn is_small(cave: &str) -> bool {
    cave.chars().next().unwrap().is_lowercase()
}

fn enumerate<'a>(
    system: &'a HashMap<&'a str, Vec<&'a str>>,
    mut path: Vec<&'a str>,
    from: &'a str,
    visited: HashSet<&'a str>,
    supports_dup_small_cave: bool,
) -> usize {
    path.push(from);
    if from == "end" {
        // eprintln!("{:?}", path);
        return 1;
    }

    let mut count = 0;
    for to in system.get(from).unwrap() {
        let mut visited = visited.clone();
        let mut supports_dup_small_cave = supports_dup_small_cave;
        if is_small(to) {
            if visited.contains(to) {
                if supports_dup_small_cave && *to != "start" && *to != "end" {
                    supports_dup_small_cave = false;
                } else {
                    continue;
                }
            } else {
                visited.insert(*to);
            }
        }

        count += enumerate(system, path.clone(), to, visited, supports_dup_small_cave);
    }
    count
}

pub fn part_1(input: &str) -> usize {
    solve(input, false)
}
pub fn part_2(input: &str) -> usize {
    solve(input, true)
}

fn solve(input: &str, part_b: bool) -> usize {
    let mut system: HashMap<&str, Vec<&str>> = HashMap::new();

    for line in input.trim().split('\n') {
        let (from, to) = line.split_once('-').unwrap();
        system.entry(from).or_default().push(to);
        system.entry(to).or_default().push(from);
    }

    let mut visited = HashSet::new();
    visited.insert("start");
    enumerate(&system, Vec::new(), "start", visited, part_b)
}

#[cfg(test)]
mod tests {
    #[test]
    fn example1() {
        let input = "start-A
start-b
A-c
A-b
b-d
A-end
b-end";
        assert_eq!(super::part_1(input), 10);
    }

    #[test]
    fn part_1() {
        let input = super::part_1(include_str!("../../input/day12.txt"));
        assert_eq!(input, 3_298);
    }

    #[test]
    fn example2() {
        let input = "dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc";
        assert_eq!(super::part_2(input), 103);
    }

    #[test]
    fn part_2() {
        let input = super::part_2(include_str!("../../input/day12.txt"));
        assert_eq!(input, 93_572);
    }
}
