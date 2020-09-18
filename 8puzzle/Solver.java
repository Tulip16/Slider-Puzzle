/* *****************************************************************************
 *  Name: Tulip Pandey
 *  Date: 18th April
 *  Description: Assignment 4
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode b;
    private Board initial;

    private class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private int moves;
        private SearchNode prev;
        private int priority;

        public SearchNode(Board board,int moves){
            this.board = board;
            this.moves = moves;
            priority = moves+board.manhattan();
            prev = null;
        }

        public int compareTo(SearchNode searchNode) {
            if(priority<searchNode.priority) return -1;
            if(priority>searchNode.priority) return 1;
            if(moves > searchNode.moves) return -1;
            if(moves<searchNode.moves) return 1;
            return 0;
        }
    }

    public Solver(Board initial){
        if(initial == null) throw new IllegalArgumentException();
        this.initial = initial;
        MinPQ<SearchNode> a = new MinPQ<>();
        int moves = 0;
        b = new SearchNode(initial,moves);
        a.insert(b);
        if(isSolvable()) {
            while (!b.board.isGoal()) {
                b = a.delMin();

                moves++;
                for (Board x : b.board.neighbors()) {
                    if (b.prev == null) {
                        SearchNode temp = new SearchNode(x, moves);
                        temp.prev = b;
                        a.insert(temp);
                    }
                    else if (!x.equals(b.prev.board)) {
                        SearchNode temp = new SearchNode(x, moves);
                        temp.prev = b;
                        a.insert(temp);
                    }
                }

                moves = a.min().moves;
            }
        }

    }




    // is the initial board solvable? (see below)
    public boolean isSolvable(){

        int moves1 = 0;
        int moves2 =0;
        SearchNode jk1;
        SearchNode jk2;
        MinPQ<SearchNode> a1 = new MinPQ<>();
        MinPQ<SearchNode> a2 = new MinPQ<>();
        jk1 = new SearchNode(initial,moves1);
        jk2 = new SearchNode(initial.twin(),moves2);
        a1.insert(jk1);
        a2.insert(jk2);


        while ((!jk1.board.isGoal())&&(!jk2.board.isGoal())){
            jk1 = a1.delMin();
            jk2 = a2.delMin();

            moves1++;
            moves2++;
            for (Board x : jk1.board.neighbors()) {
                if (jk1.prev == null) {
                    SearchNode temp = new SearchNode(x, moves1);
                    temp.prev = jk1;
                    a1.insert(temp);
                }
                else if (!x.equals(jk1.prev.board)) {
                    SearchNode temp = new SearchNode(x, moves1);
                    temp.prev = jk1;
                    a1.insert(temp);
                }
            }

            for (Board x : jk2.board.neighbors()) {
                if (jk2.prev == null) {
                    SearchNode temp = new SearchNode(x, moves2);
                    temp.prev = jk2;
                    a2.insert(temp);
                }
                else if (!x.equals(jk2.prev.board)) {
                    SearchNode temp = new SearchNode(x, moves2);
                    temp.prev = jk2;
                    a2.insert(temp);
                }
            }

            moves1 = a1.min().moves;
            moves2 = a2.min().moves;
        }

        return (jk1.board.isGoal());
    }

    // min number of moves to solve initial board
    public int moves(){
        return b.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        SearchNode now = b;
        Stack<Board> c = new Stack<Board>();
        while (now != null) {
            c.push(now.board);
            now = now.prev;
        }
        return c;
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In("puzzle43.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }

    }

}