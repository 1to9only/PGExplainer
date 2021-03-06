# PGExplainer

PGExplainer is a Minimal SudokuExplainer, to rate (ER/EP/ED) sudoku puzzles.

It is Nicolas Juillerat's SudokuExplainer 1.2.1, with Glenn Fowler's serate code (SE 1.2.1.3), with the multi-threaded chaining code from [SukakuExplainer](https://github.com/1to9only/SukakuExplainer), and with a number of additional code changes by me.

All code not needed in the serate process have also been removed.

## Usage - serate
```
java.exe -Xrs -Xmx500m -cp PGExplainer.jar sudoku.serate --input=puzzles.txt --output=output.txt
```
## Options
Only these options are supported:
```
-f, --format=FORMAT
-i, --input=FILE
-o, --output=FILE
```
The default FORMAT is "%g ED=%r/%p/%d", so this does not have to be specified.

## Options - for sudokus with multiple solutions
Only these options are supported:
```
-M  for sudokus with multiple solutions

-N  use with -M, disable Nested Forcing Chains checks
-D  use with -M, disable Dynamic Forcing Chains checks (implies -N)
```

#### Comparison
Comparison of time taken to rate all sudokus posted in [Patterns Game 415](http://forum.enjoysudoku.com/post302459.html#p302459) (87 sudokus, including more's):
```
                        Version     Size            Rating Time

SudokuExplainer.jar   - 1.2.1.3   - 304,777 bytes - 10 m 34 s

PGExplainer.jar       - 2021.3.30 - 56,712 bytes  -  2 m 45 s
```

