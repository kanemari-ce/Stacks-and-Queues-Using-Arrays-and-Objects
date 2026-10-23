# Stacks and Queues Using Arrays and Objects

CpE211 - Lab 5. A menu-driven, plain-Java program that implements an array-backed **stack (LIFO)** and a **circular queue (FIFO)**, drives them through an **Undo simulation** and a **customer-service demo**, and ships a self-checking **T1-T5 test script** that prints PASS/FAIL evidence for every required rubric case.

## Features

- **ArrayStack** — `push(int x)`, `pop()`, `peek()`, `isEmpty()`, `isFull()`, `display()`
- **ArrayQueue** — `enqueue(int x)`, `dequeue()`, `peek()`, `isEmpty()`, `isFull()`, `display()` — circular: `front`/`rear`/`count` with the `(index + 1) % capacity` wrap
- **State checks** — every mutation is guarded by `isEmpty()`/`isFull()` first; overflow is rejected with a message, underflow warns and returns `-1`
- **Undo simulation** — numbers typed are pushed; `UNDO` pops the most recent one (LIFO in its natural habitat)
- **Customer-service demo** — arrivals are enqueued; serving dequeues the first customer in line (FIFO fairness)
- **Test script (menu 9)** — automated PASS/FAIL evidence; one screenshot covers the whole rubric

## Installation & Usage

Requires JDK 8 or newer (verified on JDK 25). No external dependencies.

```
git clone <your-repo-url>
cd <project folder>
javac Lab5.java
java Lab5
```

| Input | Action |
|-------|--------|
| `1 <n>` | Type a number — stack push |
| `2` | UNDO — stack pop |
| `3` | Peek last typed (stack) |
| `4` | Display typed history (stack) |
| `5 <n>` | Customer arrives — queue enqueue |
| `6` | Serve customer — queue dequeue |
| `7` | Peek next in line (queue) |
| `8` | Display queue |
| `9` | Run test script T1-T5 |
| `0` | Exit |

## Test Evidence

| # | Case | Expected result |
|---|------|-----------------|
| T1 | PUSH 10, PUSH 20, POP / ENQUEUE 1, ENQUEUE 2, DEQUEUE | pop returns 20 (LIFO); dequeue returns 1 (FIFO) |
| T2 | pop/dequeue on empty structure | warning message, returns `-1`, state unchanged |
| T3 | Fill to capacity | 6th push/enqueue rejected (overflow); after two dequeues, `rear` wraps 4 -> 0 and enqueues reuse the freed slots |
| T4 | Push/enqueue then inspect | `peek()` output is repeatable and does not modify the structure |
| T5 | Remove all items | stack drains `5 4 3 2 1`, queue drains `1 2 3 4 5`, both end empty |

## Design Notes

- Stack: `top` starts at `-1`; `push` increments before storing, `pop` reads before decrementing.
- Queue: `count` distinguishes empty from full — after wrapping, indices alone look identical in both states.
- `CAPACITY` is a single constant (5) at the top of `Lab5.java`.

## Tech Stack

- **Java SE 8+** (verified on JDK 25) — standard library only, zero external dependencies
- **java.util.Scanner** console I/O
- **Markdown + HTML-to-PDF** (Edge headless) for lab documentation
