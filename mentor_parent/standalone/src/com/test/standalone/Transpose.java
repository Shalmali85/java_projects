package com.test.standalone;

import java.util.Scanner;

/**
 * Created by roysha on 4/25/2016.
 */
public class Transpose {

public static void main (String srags[])
{
    System.out.println("Enter no of rows");
    Scanner sc=new Scanner(System.in);
    int n=0;
    int number=0;
    n=sc.nextInt();
    System.out.println("Enter no of columns");
    int m=0;
    m=sc.nextInt();
   int[][] arr=new int[n][m];
    for(int i=0;i<n;i++){
        for (int j=0;j<m;j++)
        {
            System.out.println("Enter the matrix at "+j);
            number=sc.nextInt();
            arr[i][j]=number;

        }
    }
    displayOriginal(n,m,arr);
    displayTranspose(n,m,arr);
}

    private static  void displayOriginal(int n,int m,int arr[][]){
        StringBuilder str=new StringBuilder();
        System.out.println("The output is ");
        for(int i=0;i<n;i++){
            for (int j=0;j<m;j++)
            {
                str.append(arr[i][j]).append("\t");

            }
            System.out.println(str);
            str=new StringBuilder();

        }

    }
    private static  void displayTranspose(int n,int m,int arr[][]){
        StringBuilder str=new StringBuilder();
        System.out.println("The transpose is ");
        int [][]tansposeArr=new int[m][n];
        for(int i=0;i<n;i++){
            for (int j=0;j<m;j++)
            {
                tansposeArr[j][i]=arr[i][j];

            }

        }
        for(int i=0;i<m;i++){
            for (int j=0;j<n;j++)
            {
                str.append(tansposeArr[i][j]).append("\t");

            }
            System.out.println(str);
            str=new StringBuilder();

        }
    }


}
