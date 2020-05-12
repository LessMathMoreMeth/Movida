package movida.campomoritabanelli;

public class InsertionSort implements Sorting {

    public void sort(String field, MyComp A[]){
        this.insertionSort(field, A);
    }

    public void insertionSort(String field, MyComp A[]) {
        for (int k = 1; k <= A.length - 1; k++) {
            int j;
            MyComp x = A[k];
            for (j = 0; j < k; j++)
                if (A[j].compareChoice(field,x) > 0) break;
            if (j < k) {
                for (int t = k; t > j; t--) A[t] = A[t - 1];
                A[j] = x;
            }
        }
    }

}
