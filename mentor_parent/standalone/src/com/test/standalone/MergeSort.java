package com.test.standalone;

/**
 * Created by roysha on 4/25/2016.
 */
public class MergeSort {
    public static void main(String args[]) {
        int[] n = {45, 23, 11, 9, 27, 65, 4, 3, 28};
        merge(n.length, n);
    }

    public static void merge(int length, int[] n) {
        int endIndex = n.length;
        int midpoint = endIndex / 2;
        sort(0, midpoint, n);
         sort(midpoint, n.length, n);
         int[] tempArray=n.clone();
        int count=0;
        for(int j=0;j<n.length;j++) {
            if(count< 8 && midpoint <8) {
                if (tempArray[count] > tempArray[midpoint]) {
                    tempArray[count] = tempArray[midpoint];
                    for (int i = count; i < n.length - 1; i++) {
                        if (i < midpoint)
                            tempArray[i + 1] = n[i];
                        else
                            tempArray[i + 1] = n[i + 1];
                    }
                    n = tempArray.clone();

                }
                count++;
                midpoint++;
            }
            count=endIndex/2;


        }
        for (int i = 0; i < n.length; i++) {
            System.out.println(tempArray[i]);
        }



}

    private static int[] sort(int start, int end, int[] n) {
        int temp;
        for (int i = start; i < end-1; i++) {
            if (n[i] > n[i + 1]) {
                temp = n[i + 1];
                n[i + 1] = n[i];
                n[i] = temp;
                i = start-1;
            }
        }
        return n;

    }
}