/* *****************************************************************************
 *  Name: Tulip Pandey
 *  Date: 18th April
 *  Description: Assignment 4
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Objects;

public class Board {

    private int[][] tiles;
    private int z1;
    private int z2;

    private int n;

    private void swap(int i1,int i2,int j1,int j2){
        int temp1 = tiles[i1][j1];
        tiles[i1][j1] = tiles[i2][j2];
        tiles[i2][j2] = temp1;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if(tiles == null) throw new IllegalArgumentException();
        n = tiles.length;
        this.tiles  = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                this.tiles[i][j] = tiles[i][j];
                if(tiles[i][j]==0){
                    z1 = i;
                    z2 = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(tiles[i][j]!=0){
                    if((tiles[i][j]-n*(i)-j-1)!=0) res+=1;
                }

            }
        }

        return res;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(tiles[i][j]!=0){
                    res+=(Math.abs(i-(tiles[i][j]-1)/n)+Math.abs(j-(tiles[i][j]-1)%n));
                }

            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return manhattan()==0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        return Objects.equals(y.toString(), toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){

        Stack<Board> a = new Stack<Board>();
        int[] optsi = {z1-1,z1+1};
        int[] optsj = {z2-1,z2+1};

        for(int i=0;i<2;i++){
            if((optsi[i]<0)||(optsi[i]>=n)) continue;
            Board b = new Board(tiles);
            b.swap(z1,optsi[i],z2,z2);
            b.z1 = optsi[i];
            a.push(b);
        }

        for(int i=0;i<2;i++){
            if((optsj[i]<0)||(optsj[i]>=n)) continue;
            Board b = new Board(tiles);
            b.swap(z1,z1,z2,optsj[i]);
            b.z2 = optsj[i];
            a.push(b);
        }

        return a;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board res;
        int a,b,c,d;
        if(z1>0) a = z1-1 ;
        else a = z1+1;
        if(z2>0) b = z2-1 ;
        else b = z2+1;
        if(a>0) c=a-1;
        else c=a+1;
        d=b;
        res = new Board(tiles);
        res.swap(a,c,b,d);
        return res;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        // create initial board from file
        In in = new In("puzzle2x2-00.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        int[][] tiles2 = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles2[i][j] = tiles[i][j];
        Board initial = new Board(tiles);
        Board another = new Board(tiles2);
        if(initial.equals(another))  StdOut.println(initial);
        StdOut.println("manhattan= "+initial.manhattan());
        StdOut.println("twin-"+initial.twin());
        for(Board x:initial.neighbors()){
            StdOut.println(x);
            StdOut.println("manhattan = " + x.manhattan());
        }

        // solve the puzzle
      /*  Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

       */
    }

}