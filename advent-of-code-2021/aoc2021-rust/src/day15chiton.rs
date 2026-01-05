use std::{
    cmp::Reverse,
    collections::{BinaryHeap, HashMap},
};

pub fn part_1(input: &str) -> i32 {
    let (graph, goal) = process_input(input);
    dijkstra(graph, goal)
}

pub fn part_2(input: &str) -> i32 {
    let (mut graph, (max_x, max_y)) = process_input(input);
    let goal = magnify(&mut graph, 5, max_x, max_y);
    dijkstra(graph, goal)
}

fn process_input(input: &str) -> (HashMap<(usize, usize), i32>, (usize, usize)) {
    let mut graph = HashMap::new();
    let mut max_x = 0;
    let mut max_y = 0;
    for (y, line) in input.trim().split('\n').enumerate() {
        for (x, c) in line.chars().enumerate() {
            graph.insert((y, x), c.to_digit(10).unwrap() as i32);
            max_x = x;
        }
        max_y = y;
    }
    (graph, (max_x, max_y))
}

fn wrap(i: i32) -> i32 {
    let i = i % 10;
    if i == 0 { 1 } else { i }
}

fn dijkstra(graph: HashMap<(usize, usize), i32>, (max_x, max_y): (usize, usize)) -> i32 {
    let mut best = graph
        .iter()
        .map(|(k, _)| (*k, i32::MAX))
        .collect::<HashMap<(usize, usize), i32>>();
    let mut visit = BinaryHeap::new();
    visit.push((Reverse(0), (0, 0)));
    while let Some((Reverse(cost), (y, x))) = visit.pop() {
        if cost < best[&(y, x)] {
            best.insert((y, x), cost);
            for (dy, dx) in [(1isize, 0), (-1, 0), (0, 1), (0, -1)] {
                let y = (y as isize) + dy;
                let x = (x as isize) + dx;
                if y >= 0 && x >= 0 && y <= max_y as isize && x <= max_x as isize {
                    let p2 = (y as usize, x as usize);
                    visit.push((Reverse(cost + graph[&p2]), p2));
                }
            }
        }
    }
    best[&(max_y, max_x)]
}

fn magnify(
    graph: &mut HashMap<(usize, usize), i32>,
    magnification: usize,
    max_x: usize,
    max_y: usize,
) -> (usize, usize) {
    let tile_width = max_x + 1;
    let tile_height = max_y + 1;
    for y_tile in 0..magnification {
        for x_tile in 0..magnification {
            if y_tile == 0 && x_tile == 0 {
                continue;
            }
            for y in 0..=max_y {
                for x in 0..=max_x {
                    let x1 = x + x_tile * tile_width;
                    let y1 = y + y_tile * tile_height;
                    let p2n = if x_tile == 0 {
                        &(y + (y_tile - 1) * tile_height, x1)
                    } else {
                        &(y1, x + (x_tile - 1) * tile_width)
                    };
                    let cost = wrap(graph[p2n] + 1);
                    graph.insert((y1, x1), cost);
                }
            }
        }
    }
    let max_x = tile_width * magnification - 1;
    let max_y = tile_height * magnification - 1;
    (max_x, max_y)
}

#[cfg(test)]
mod tests {
    const EXAMPLE_INPUT: &str = "1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581";

    #[test]
    fn example1() {
        assert_eq!(super::part_1(EXAMPLE_INPUT), 40);
    }

    #[test]
    fn part_1() {
        assert_eq!(super::part_1(include_str!("../../input/day15.txt")), 592);
    }

    #[test]
    fn example2() {
        assert_eq!(super::part_2(EXAMPLE_INPUT), 315);
    }

    #[test]
    fn part_2() {
        assert_eq!(super::part_2(include_str!("../../input/day15.txt")), 2897);
    }
}
