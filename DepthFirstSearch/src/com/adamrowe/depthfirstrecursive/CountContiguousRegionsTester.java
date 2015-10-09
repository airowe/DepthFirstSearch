package com.adamrowe.depthfirstrecursive;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to count a 2D Matrix recursively
 * using Depth First Search
 * @author ARo
 *
 */
public class CountContiguousRegionsTester
{
   private final static int rowNeighbors[] = {-1, 0, 0, 1};
   private final static int colNeighbors[] = {0, -1, 1, 0};
   
   //instance variable to keep track of current sum
   private int currentSum = 0;
   
   //instance variable to keep track of list of sums
   private final List<Integer> sums = new ArrayList<Integer>();
   
   //two-dimensional dataMatrix matrix
   private final int[][] dataMatrix;
   
   //convenience variables for size of matrix's rows and columns
   private final int dataRowCount;
   private final int dataColumnCount;
   
   public CountContiguousRegionsTester(int[][] data)
   {
      this.dataMatrix = data;
      dataRowCount = data.length;
      dataColumnCount = data[0].length;
   }
   
   // checks to make sure we're within boundaries, value is one and it hasn't been visited yet
   private final boolean isValidNode(int row, int col, boolean visited[][])
   {
       return (row >= 0) && (row < dataRowCount) &&     // verify that we're within number of rows
              (col >= 0) && (col < dataColumnCount) &&     // verify that we're within number of columns
              (dataMatrix[row][col] == 1 && !visited[row][col]); // value is 1 and hasn't been visited yet
   }
 
   //recursively search through neighbors of given data item
   private final void depthFirstSearch(int row, int col, boolean visited[][])
   {
       // Mark this dataMatrix item as visited
       visited[row][col] = true;
    
       // Recursively loop through all neighbors
       for(int neighborIndex = 0; neighborIndex < 4; neighborIndex++)
       {
          //Check first to make sure the node is within the matrix, then whether its value is 1 and hasn't been visited
          if(isValidNode(row + rowNeighbors[neighborIndex], col + colNeighbors[neighborIndex], visited))
          {
             currentSum++; //increment sum
             depthFirstSearch(row + rowNeighbors[neighborIndex], col + colNeighbors[neighborIndex], visited); //go recursive on neighbors
          }
       }
   }
   
   //sums "1-value" connections in a given 2D Matrix
   private final void sumConnections()
   {
      //Set 2D array to keep track of visited dataMatrix items
      boolean visitedMatrix[][] = new boolean[dataRowCount][dataColumnCount];

      //iterate through rows
      for(int rowIndex = 0; rowIndex < dataRowCount; rowIndex++)
      {
         //iterate through columns
         for(int columnIndex = 0; columnIndex < dataColumnCount; columnIndex++)
         {
            //check to see if the value of the dataMatrix item is 1 and it hasn't been visited yet
            if(dataMatrix[rowIndex][columnIndex] == 1 && !visitedMatrix[rowIndex][columnIndex])
            {
               //because we've found a match, the current sum is 1
               currentSum = 1;
               
               //check all the neighbors of this dataMatrix item recursively to see if their value is 1
               depthFirstSearch(rowIndex, columnIndex, visitedMatrix);
               
               //add this sum to the list of sums
               sums.add(currentSum);
            }
         }
       }         
   }
   
   //getter to access the list of sums
   public List<Integer> getSums()
   {
      return sums;
   }
   
   //main to run the test
   public static void main(String[] args) 
   {
      //given 2D dataMatrix matrix
      int[][] data = new int[][]{
         { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
         { 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
         { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
         { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
         { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 }
       };
      
      //Instance of the tester
      CountContiguousRegionsTester tester = new CountContiguousRegionsTester(data);
       
      //sum the connections
      tester.sumConnections();
      
      //iterate through sums and print them out
      for(int sum : tester.getSums())
      {
         System.out.println("Sum: " + sum);
      }
   }
}