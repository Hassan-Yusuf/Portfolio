package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    private double adj[][];
    private double adj2[][];
    private int width,height;
    private int numVertices;
    private final boolean isDirected=false;
    private int array[];


    public Maze(int width,int height){
        this.width = width;
        this.height = height;
        this.numVertices=width*height;
        adj = new double[numVertices][numVertices];
        for(int x = 0; x<numVertices;x++){
            Arrays.fill(adj[x],Double.NaN);
        }
        forEachAddEdges();
        spanningTree();
    }
    public void addEdge (int x, int y, double weight) {
        if (!isValidEdge (x, y, weight))
            throw new IllegalArgumentException();

        adj[x][y] = weight;
        if (!isDirected)
            adj[y][x] = weight;
    }
    public void addEdge(int x,int y){
        addEdge(x,y,1);
    }
    public void deleteEdge (int x, int y) {
        if (!isValidEdge (x, y))
            throw new IllegalArgumentException();

        adj[x][y] = Double.NaN;
        if (!isDirected)
            adj[y][x] = Double.NaN;
    }
    public boolean isDirected () {
        return isDirected;
    }
    public boolean isEdge (int x, int y) {
        return isValidEdge (x,y) && !Double.isNaN (adj[x][y]);
    }
    public double weight (int x, int y) {
        if (!isValidEdge (x,y))
            throw new IllegalArgumentException ();
        return adj[x][y];
    }
    public int inDegree (int x) {
        if (!isValidVertex (x))
            throw new IllegalArgumentException ();

        int count = 0;
        for (int i = 0; i < numVertices; i++)
            if (!Double.isNaN (adj[i][x]))
                count++;
        return count;
    }
    public int outDegree (int x) {
        if (!isValidVertex (x))
            throw new IllegalArgumentException ();

        int count = 0;
        for (int i = 0; i < numVertices; i++)
            if (!Double.isNaN (adj[x][i]))
                count++;
        return count;
    }
    public int degree (int x) {
        if (!isValidVertex(x))
            throw new IllegalArgumentException();

        if (isDirected())
            return inDegree (x) + outDegree (x);
        else
            return outDegree (x);
    }
    public int numVertices () {
        return numVertices;
    }
    public int[] neighbours (int x) {
        if (!isValidVertex (x))
            throw new IllegalArgumentException ();

        int[] result = new int[degree (x)];

        int[] outNeighbours = outNeighbours(x);
        System.arraycopy(outNeighbours, 0, result, 0, outNeighbours.length);
        if (isDirected()) {
            int[] inNeighbours = inNeighbours(x);
            System.arraycopy(inNeighbours, 0, result, outNeighbours.length,
                    inNeighbours.length);
        }
        return result;
    }
    public int[] inNeighbours (int x) {
        int[] result = new int[inDegree (x)];
        int i = 0;
        for (int y = 0; y < numVertices; y++)
            if (!Double.isNaN (adj[x][y]))
                result[i++] = y;
        return result;
    }
    public int[] outNeighbours (int x) {
        int[] result = new int[outDegree (x)];
        int i = 0;
        for (int y = 0; y < numVertices; y++)
            if (!Double.isNaN (adj[y][x]))
                result[i++] = y;
        return result;
    }
    protected boolean isValidVertex (int x) {
        return x >= 0 && x < numVertices;
    }
    protected void forEachAddEdges(){
        for(int x = 0;x<numVertices;x++){
            addEdges(x);
        }
    }
    protected void addEdges(int x){
        int left,right,down,up,randomWeight;
        int max=50,min=0;
        left=-1;right=1;down=width;up=-width;
        int[] moves;
        if(x%width==0){
            left=0;
        }
        if((x%(width)==(width-1))){
            right=0;
        }
        if(x>=width*(height-1)){
            down=0;
        }
        if(x<=width-1){
            up=0;
        }
        moves = new int[]{left,right,down,up};
        for (int move:moves) {
            randomWeight = (int)(Math.random() * (max - min + 1) + min);
            if (move != 0) {
                addEdge(x, x + move,randomWeight);
            }
        }
    }
    protected boolean isValidEdge (int x, int y) {
        return x != y && isValidVertex (x) && isValidVertex(y);
    }
    protected boolean isValidEdge (int x, int y, double weight) {
        return isValidEdge (x,y) && !Double.isNaN (weight);
    }
    void print(){
        int randomEntrance= (int)(Math.random() * ((height-1) - 0 + 1) + 0)*width;
        int randomExit= (int)(Math.random() * ((height-1) - 0 + 1) + 0)*width+(width-1);
        for(int x=0;x<numVertices;x++){
            List<Integer> listNeighbours = new ArrayList<>();
            for(int y:neighbours(x)){
                listNeighbours.add(y);
            }
            if(x==randomEntrance||x==randomExit){
                System.out.print("*");
            }
            else{
                System.out.print("+");
            }
            if(listNeighbours.contains(x+1)){
                System.out.print("-");
            }
            else{
                System.out.print(" ");
            }
            if(x%width==(width-1)){
                System.out.print("\n");
                for(int y=x-(width-1);y<x+1;y++){
                    listNeighbours.clear();
                    for(int z:neighbours(y)){
                        listNeighbours.add(z);
                    }
                    if(listNeighbours.contains(y+width)){
                        System.out.print("| ");
                    }
                    else{
                        System.out.print("  ");
                    }
                }
                System.out.print("\n");
            }
        }
    }
    public void initializeComponents(){
        array = new int[numVertices]; //initialises array
        for(int x=0;x<numVertices;x++) {           //so each vertex has its own component to start with as its representative
            array[x]=x;                            //i.e. 1 stores 1 2 stores 2
        }
    }
    public void mergeComponents(int x,int y){
        for(int z=0;z<numVertices;z++){
            if(array[z]==y){
                array[z]=x;
            }
        }
    }
    public void spanningTree(){
        adj2 = adj;
        adj = new double[numVertices][numVertices];
        for(int x = 0; x<numVertices;x++) {
            Arrays.fill(adj[x], Double.NaN);
        }
        initializeComponents();
        double lowestWeightEdge;int storeYForMerge;
        for(int x=0;x<numVertices;x++){
            lowestWeightEdge=10000000;
            storeYForMerge = 0;
            for(int y=0;y<numVertices;y++){
                if(adj2[x][y]<lowestWeightEdge&&Double.isNaN(adj[y][x])){ //add && later to make sure its only 1 way endpoint.
                    System.out.println(adj[x][y]+" "+adj[y][x]);
                    lowestWeightEdge= adj2[x][y];
                    storeYForMerge=y;
                }
            }
            addEdge(x, storeYForMerge,lowestWeightEdge);
            mergeComponents(x,storeYForMerge);
        }
        for (int x=0;x<numVertices;x++) {
            //List<Integer> listNeighbours = new ArrayList<>();
            //listNeighbours.add(neighbours(array[x]));
            if(neighbours(array[x]).length==1){

            }
        }
        System.out.println(Arrays.toString(array));


    }
    public static void main (String[] args) {
        Maze g = new Maze(10, 10);
        g.print();
    }
}
