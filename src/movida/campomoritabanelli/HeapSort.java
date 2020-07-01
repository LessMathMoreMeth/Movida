package movida.campomoritabanelli;

public class HeapSort implements Sorting {

    public void sort(String field, MyComp A[]){
        this.heapSort(A, field);
    }

    private static void heapify(MyComp A[], int n, int i, String field){
        if(i > n) return;
        heapify(A, n, 2*i, field);
        heapify(A, n, 2*i+1, field);
        fixheap(A, n, i, field);
    }

    private static void fixheap(MyComp A[], int c, int i, String field){
        int max = 2*i;
        if(2*i  > c) return;
        if(2*i +1 <= c && A[2*i].compareChoice(field,A[2*i +1]) < 0) max = 2*i+1;
        if(A[i].compareChoice(field,A[max]) < 0){
            MyComp temp = A[max];
            A[max] = A[i];
            A[i] = temp;
            fixheap(A, c, max,field);
        }
    }
    private static void deleteMax(MyComp A[], int c, String field) {
        if (c <= 0) return;
        A[1] = A[c];
        c--;
        fixheap(A, c, 1, field);
    }
    public static void heapSort(MyComp A[], String field) {
        heapify(A, A.length - 1, 1, field);
        for (int c = (A.length - 1); c > 0; c--) {
            MyComp k = findMax(A);
            deleteMax(A, c, field);
            A[c] = k;
        }
    }

    private static MyComp findMax(MyComp A[]){
        return A[1];
    }


}
