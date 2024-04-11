import java.util.*;

public class SimilarArrays {
    private void swap (int i, int j, int arr[]) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int partition (int l, int r, int arr[]) {
        int pivot = l + (r - l) / 2, lessCount = 0;
        for (int i = l; i <= r; i++) {
            if (arr[i] <= arr[pivot]) lessCount++;
        }
        swap(pivot, l + lessCount - 1, arr);
        pivot = l + lessCount - 1;
        for (int i = l, j = pivot + 1; i < pivot && j <= r; ) {
            while (i < pivot && arr[i] <= arr[pivot]) i++;
            while (j <= r && arr[j] > arr[pivot]) j++;
            if (i == pivot || j == r + 1) break;
            swap(i, j, arr);
        }
        return pivot;
    }

    private void sort (int l, int r, int arr[]) {
        if (l >= r) return;
        int pivot = partition(l, r, arr);
        sort(l, pivot - 1, arr);
        sort(pivot + 1, r, arr);
    }

    // public int minSteps(int[] A, int[] target) {
    //     if (A.length != target.length) return -1;
    //     int dec = 0, inc = 0;
    //     boolean taken[] = new boolean[A.length];
    //     sort(0, A.length - 1, A);
    //     sort(0, A.length - 1, target);
    //     for (int i = 0; i < A.length; i++) taken[i] = false;
    //     for (int i = 0, j = 0, k; i < A.length; i++) {
    //         while (j < A.length && taken[j]) j++;
    //         k = j;
    //         while (k < A.length && (taken[k] || Math.abs(A[i] - target[k]) % 2 != 0)) k++;
    //         if (k == A.length) return -1;
    //         if (A[i] < target[k]) inc += (target[k] - A[i]) / 2;
    //         else dec += (A[i] - target[k]) / 2;
    //         taken[k] = true;
    //     }
    //     if (dec != inc) return -1;
    //     return dec;
    // }

    // simpler approach
    public int minSteps(int[] A, int[] target) {
        if (A.length != target.length) return -1;
        int dec = 0, inc = 0;
        Vector<Integer> evenA = new Vector<>(), oddA = new Vector<>(), evenTarget = new Vector<>(), oddTarget = new Vector<>();
        sort(0, A.length - 1, A);
        sort(0, A.length - 1, target);
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0) evenA.add(A[i]);
            else oddA.add(A[i]);
            if (target[i] % 2 == 0) evenTarget.add(target[i]);
            else oddTarget.add(target[i]);
        }
        if (evenA.size() != evenTarget.size()) return -1;
        for (int i = 0; i < evenA.size(); i++) {
            if (evenA.get(i) > evenTarget.get(i)) dec += (evenA.get(i) - evenTarget.get(i)) / 2;
            else inc += (evenTarget.get(i) - evenA.get(i)) / 2;
        }
        for (int i = 0; i < oddA.size(); i++) {
            if (oddA.get(i) > oddTarget.get(i)) dec += (oddA.get(i) - oddTarget.get(i)) / 2;
            else inc += (oddTarget.get(i) - oddA.get(i)) / 2;
        }
        if (dec != inc) return -1;
        return dec;
    }
}
