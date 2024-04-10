public class Sort {

    public static void merge (int left, int mid, int right, int H[], int W[]) {
        int n = mid + 1 - left, m = right - mid;
        int l1[] = new int[n], l2[] = new int[n], r1[] = new int[m], r2[] = new int[m];
        for (int i = 0, j = left; j <= mid; i++, j++) {
            l1[i] = H[j];
            l2[i] = W[j];
        }
        for (int i = 0, j = mid + 1; j <= right; i++, j++) {
            r1[i] = H[j];
            r2[i] = W[j];
        }
        int i = left, l = 0, r = 0;
        while (l < n || r < m) {
            if (l < n && r < m) {
                if (l1[l] < r1[r]) {
                    H[i] = l1[l];
                    W[i] = l2[l];
                    l++;
                }
                else if (l1[l] > r1[r]) {
                    H[i] = r1[r];
                    W[i] = r2[r];
                    r++;
                }
                else {
                    if (l2[l] < r2[r]) {
                        H[i] = l1[l];
                        W[i] = l2[l];
                        l++;
                    }
                    else {
                        H[i] = r1[r];
                        W[i] = r2[r];
                        r++;
                    }
                }
            }
            else if (l < n) {
                H[i] = l1[l];
                W[i] = l2[l];
                l++;
            }
            else if (r < m) {
                H[i] = r1[r];
                W[i] = r2[r];
                r++;
            }
            i++;
        }
    }

    public static void mergeSort (int left, int right, int H[], int W[]) {
        if (left == right) return;
        int mid = left + (right - left) / 2;
        mergeSort(left, mid, H, W);
        mergeSort(mid + 1, right, H, W);
        merge(left, mid, right, H, W);
    }

    static void swap (int i, int j, int arr[]) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    static int partition (int left, int right, int H[], int W[]) {
        int mid = left + (right - left) / 2, lessCount = 0, pivot;
        for (int i = left; i <= right; i++) {
            if (H[i] < H[mid] || (H[i] == H[mid] && W[i] <= W[mid])) lessCount++;
        }
        pivot = left + lessCount - 1;
        swap(mid, pivot, H);
        swap(mid, pivot, W);
        int i = left, j = pivot + 1;
        while (true) {
            while (i < pivot && (H[i] < H[pivot] || (H[i] == H[pivot] && W[i] <= W[pivot]))) i++;
            while (j <= right && (H[j] > H[pivot] || (H[j] == H[pivot] && W[j] > W[pivot]))) j++;
            if (i >= pivot || j > right) break;
            swap(i, j, H);
            swap(i, j, W);
        }
        return pivot;
    } 
    
    static void quickSort(int left, int right, int H[], int W[]) {
        if (left >= right) return;
        int pivot = partition(left, right, H, W);
        quickSort(left, pivot - 1, H, W);
        quickSort(pivot + 1, right, H, W);
    }

    public static void outputArray (int arr[]) {
        for (int i = 0; i < arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println();
    }
    public static void main(String[] args) {
        int H[] = new int[]{3,4,1,7,8,4}, W[] = new int[]{2,5,6,3,7,1};
        System.out.println("original");
        outputArray(H);
        outputArray(W);
        if (H.length == W.length) quickSort(0, H.length - 1, H, W);
        else System.out.println("H and W are of different length");
        System.out.println("sorted");
        outputArray(H);
        outputArray(W);
    }
}
