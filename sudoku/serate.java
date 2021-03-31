/*
 * Project: Sudoku Explainer
 * Copyright (C) 2006-2009 Nicolas Juillerat
 * Available under the terms of the Lesser General Public License (LGPL)
 * this entry point by gsf @ www.sudoku.com/boards (The Player's Forum)
 */
package sudoku;

import java.io.*;
import java.util.*;

import sudoku.*;

public class serate {
    static String FORMAT = "%g ED=%r/%p/%d";
    static String RELEASE = "2021-3-30";
    static String VERSION = "2021.3.30";

    /**
     * Solve input puzzles and print results according to the output format.
     * @param args 81-char puzzles
     */
    public static void main(String[] args) {
        String          format = FORMAT;
        String          input = null;
        String          output = "-";
        String          a;
        String          s;
        String          v;
        String          puzzle;
        BufferedReader  reader = null;
        PrintWriter     writer = null;
        int             arg;
        char            c;
        try {
            for (arg = 0; arg < args.length; arg++) {
                a = s = args[arg];
                if (s.charAt(0) != '-')
                    break;
                v = null;
                if (s.charAt(1) == '-') {
                    if (s.length() == 2) {
                        arg++;
                        break;
                    }
                    s = s.substring(2);
                    for (int i = 2; i < s.length(); i++)
                        if (s.charAt(i) == '=') {
                            v = s.substring(i+1);
                            s = s.substring(0, i);
                        }
                    if (s.equals("format"))
                        c = 'f';
                    else if (s.equals("in") || s.equals("input"))
                        c = 'i';
                    else if (s.equals("out") || s.equals("output"))
                        c = 'o';
                    else
                        c = '?';
                }
                else {
                    c = s.charAt(1);
                    if (s.length() > 2)
                        v = s.substring(2);
                    else if (++arg < args.length)
                        v = args[arg];
                }
                switch (c) {
                case 'f':
                    format = v;
                    break;
                case 'i':
                    input = v;
                    break;
                case 'o':
                    output = v;
                    break;
                default:
                    break;
                }
            }
            if (input != null) {
                if (input.equals("-")) {
                    InputStreamReader reader0 = new InputStreamReader(System.in);
                    reader = new BufferedReader(reader0);
                }
                else {
                    Reader reader0 = new FileReader(input);
                    reader = new BufferedReader(reader0);
                }
            }
            if (output.equals("-")) {
                OutputStreamWriter writer0 = new OutputStreamWriter(System.out);
                BufferedWriter writer1 = new BufferedWriter(writer0);
                writer = new PrintWriter(writer1);
            }
            else {
                Writer writer0 = new FileWriter(output);
                BufferedWriter writer1 = new BufferedWriter(writer0);
                writer = new PrintWriter(writer1);
            }
            for (;;) {
                if (reader != null) {
                    puzzle = reader.readLine();
                    if (puzzle == null)
                        break;
                }
                else if (arg < args.length)
                    puzzle = args[arg++];
                else
                    break;
                if (puzzle.length() >= 81) {
                    Grid grid = new Grid();
                    for (int i = 0; i < 81; i++) {
                        char ch = puzzle.charAt(i);
                        if (ch >= '1' && ch <= '9') {
                            int value = (ch - '0');
                            grid.setCellValue(i % 9, i / 9, value);
                        }
                    }
                    Solver solver = new Solver(grid);
                    solver.rebuildPotentialValues();
                    try {
                        solver.getDifficulty();
                    } catch (UnsupportedOperationException ex) {
                        solver.difficulty = solver.pearl = solver.diamond = 0.0;
                    }
                    s = "";
                    for (int i = 0; i < format.length(); i++) {
                        int     w;
                        int     p;
                        char    f = format.charAt(i);
                        if (f != '%' || ++i >= format.length())
                            s += f;
                        else
                            switch (format.charAt(i)) {
                            case 'g':
                                s += puzzle;
                                break;
                            case 'r':
                                w = (int)((solver.difficulty + 0.05) * 10); p = w % 10; w /= 10;
                                s += w + "." + p;
                                break;
                            case 'p':
                                w = (int)((solver.pearl + 0.05) * 10); p = w % 10; w /= 10;
                                s += w + "." + p;
                                break;
                            case 'd':
                                w = (int)((solver.diamond + 0.05) * 10); p = w % 10; w /= 10;
                                s += w + "." + p;
                                break;
                            default:
                                s += f;
                                break;
                            }
                    }
                    writer.println(s);
                    writer.flush();
                }
            }
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (writer != null)
                    writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
