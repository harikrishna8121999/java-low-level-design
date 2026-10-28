---
name: lld-notes
description: Build a one-page HTML revision guide for a low-level design (LLD) problem, in the AlgoMaster style — requirements, entities, Miro-style class and flow diagrams, working code, a code explorer with a recorded run, patterns, similar problems and a revision cheat sheet. Use when the user is studying an LLD problem (Snake and Ladder, Parking Lot, Splitwise, ATM, Rate Limiter, BookMyShow, etc.) and asks for notes, a revision page, or "the same HTML doc" for another problem.
---

# LLD revision notes page

Make one self-contained HTML file per LLD problem. The finished Snake and Ladder page is the template:
`template/reference-snake-and-ladder.html` (in this skill's folder; in the fork the live copy is `walkthroughs/snake-and-ladder.html`).

**Always start from a copy of the template.** It already has the layout, light/dark theme, table of contents with scrollspy, diagram engine (Miro-style SVG with PNG export and full screen), code explorer, flip cards and all CSS. Change the content, not the machinery.

## Workflow

1. **Read the code first.** Find the problem's package in the repo (for example `src/main/java/.../<problem>/`). Read every file. The page must describe the real code, not a generic design.
2. **Copy the template** to `<repo>/walkthroughs/<problem-slug>.html`.
3. **Site nav.** Set the brand emoji and the crumb level link (`../index.html?level=<easy|medium|medium-hard|hard>`). Keep the `problems.js` and `site.js` script tags before `</body>`. Add one entry to `walkthroughs/problems.js` (slug, title, icon, level, read, pkg, blurb, patterns) in difficulty order; remove it from `LLD_CODE_ONLY` if listed. The home page (`index.html`), the left "All problems" nav and prev / next links all read that list. Add the row to the README table too.
4. **Rewrite each section** (order below). Keep the HTML structure and class names. Replace the text, tables, diagram data and code blocks.
5. **Diagrams.** The SVGs are drawn by JS from data arrays (`boxes`, `E` edges, `N` nodes, `sticky(...)`). Change the coordinates and labels. Check that boxes, labels and lines do not overlap.
6. **Code explorer.** Compile and run the real entry point outside the repo (scratchpad), save stdout, then run:
   ```bash
   python <skill-dir>/scripts/refresh_explorer.py walkthroughs/<slug>.html <package_dir> \
     --output run.txt --answers "v1,v2,..." --main Main.java
   ```
   Java + Lombok: `javac -cp lombok.jar -processorpath lombok.jar -d <scratch>/build $(find <pkg> -name '*.java')`, then `java -cp <scratch>/build <main class> < input.txt`. Lombok jars are under `~/.m2/repository/org/projectlombok/lombok/`. Never write build output into the repo. Update the GitHub link in the section intro.
7. **Verify by screenshot.** Use headless Chrome. To view one section, make a temporary copy that hides the others:
   `sed 's#</head>#<style>main section:not(\#<id>){display:none}</style></head>#' page.html > _tmp.html`, take the screenshot, then delete `_tmp.html`. Check every diagram and the explorer. Fix overlaps, clipped text and broken grids.
8. Do **not** change the upstream problem code unless the user asks. Suggest improvements in the page instead.

## Sections (in this order)

| # | id | Content |
|---|----|---------|
| 0 | `overview` | Title, pills (difficulty, language, read time, package), 2-line lead. **LLD interview flow diagram** (`stepsSvg`): Understand → Requirement gathering → Core entities + breakdown → Class diagram with flow → Working code. Requirement gathering has 3 dashed branches: ① Extensibility ② Dynamic (user input) ③ High traffic (concurrency), each filled in for this problem. Side notes per step. LLD round vs machine coding round note. |
| ▶ | `warmup` | **Try it first**: if the problem allows it, add an interactive demo (Snake and Ladder has a playable board). Then 5 numbered tasks to do on paper, each with a "compare → §" link. |
| 1 | `requirements` | **Requirement gathering**: 1.1 candidate/interviewer chat; 1.2 core functional; 1.3 Extensibility (table: follow-up → what changes → pattern); 1.4 Dynamic (user input) + validation callout; 1.5 High traffic (say plainly if it is not needed and why; what an online/concurrent version needs); 1.6 non-functional. |
| 2 | `entities` | **Core entities + breakdown**: nouns highlighted in a paragraph, then entity/type/responsibility table, common-mistake callout. |
| 3 | `classes` | **Class diagram** (`classSvg`, Miro style: sticky-colour headers, dotted canvas, legend, 2 sticky notes). Then a relationships table. |
| 4 | `flow` | **Flow of the core operation** (`flowSvg` flowchart with decisions and a loop-back). |
| 5 | `code` | **Working code**: bottom-up, one block per class, with a short "why" note after the key ones. |
| 6 | `try` | **Run and Test**: the code explorer (file tree, tabs, line numbers, copy, full screen, ▶ Run plays the recorded output). |
| 7 | `patterns` | Cards tagged **used / would add / avoid**, a SOLID check, one example snippet. |
| 8 | `similar` | **Similar problems** (see rules). |
| 9 | `recap` | **Revision cheat sheet** (see rules). |

## Rules the user asked for (keep them)

- **Similar problems = same model only.** List a problem only if this design really carries over (same entities and core loop). Rank by how much you can reuse, and give "reuse" and "new part to learn" for each. Do **not** group problems by shared design pattern. Do not list problems from other families. For Snake and Ladder that meant board games only (Ludo, Monopoly, Tic-Tac-Toe, Connect Four, Minesweeper, Battleship, Chess), not BookMyShow, ATM or vending machine.
- **Edge labels on the class diagram use plain words:** "has a", "has many", "may have 1", "uses", "creates", "is a". Read each line as a sentence: "Game has a Board".
- **Keep the legend standard:** title "Legend", one line per type: Composition (owns, same lifetime), Aggregation (has, lives outside), Association (knows about), Dependency (uses / creates), Inheritance (is-a), plus the `- private + public # protected` key.
- **Cheat sheet is not a grid of equal cards.** Use this order:
  1. "In one breath" summary box.
  2. Vertical timeline of the 5 interview steps (Understand; Requirements as 4 buckets: Core / Extensibility / Dynamic / High traffic; entity chips; **step 4 is one simplified Miro-style SVG** — boxes with only the name and its job, grey structure lines, purple numbered arrows ①→⑥ for the call order of one operation, 2 sticky notes; step 5 is the ~10 lines of code that matter).
  3. Traps → fixes table.
  4. Patterns in one look (used / add / avoid).
  5. Flip cards (active recall) with a "Show all answers" button.
- **No quiz or practice section at the end.** The user replaced it with the Run and Test explorer. Active practice lives in "Try it first" and in the flip cards.
- **Code ligatures off** (`font-variant-ligatures:none`), so `==` and `>=` show as plain characters.
- Diagrams stay light (whiteboard look) in dark mode. They must export to PNG.

## Writing style

Write the prose about 80% of the way to ASD-STE100 (Simplified Technical English): short sentences, active voice, one meaning per word. Be honest: say which patterns the code uses and which you would add. If the code has bugs, list them in "Traps → fixes" with the fix.

## Page contract

- `<title>`: "<Problem> LLD".
- Colours are tokens on `:root`, with dark values under `prefers-color-scheme` and `[data-theme="dark"]`.
- External scripts are pinned: Prism 1.29.0 from cdnjs. Fonts come from Google Fonts. Everything else is inline.
- Layout works at phone width (sidebar hides, grids go to one column).
