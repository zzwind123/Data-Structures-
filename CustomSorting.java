import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

public class CustomSorting {
    public static void main(String[] args) throws FileNotFoundException {
        generateAndExportResults();
    }

    public static void swapValues(int[] array, int firstIndex, int secondIndex) {
         int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    public static int performPartition(int[] array, int low, int high, int[] actionCount) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            actionCount[0]++;
            if (array[j] < pivot) {
                actionCount[0]++;
                i++;
                swapValues(array, i, j);
                actionCount[1]++;
            }
        }
        swapValues(array, i + 1, high);
        actionCount[1]++;
        return i + 1;
    }

    public static HashMap<String, Integer> customSort1(int[] array, int low, int high) {
        HashMap<String, Integer> result = new HashMap<>();
        int[] actionCount = {0, 0};
        if (low < high) {
            actionCount[0]++;
            int pivotIndex = performPartition(array, low, high, actionCount);
            customSort1(array, low, pivotIndex - 1);
            customSort1(array, pivotIndex + 1, high);
        }

        result.put("Swaps", actionCount[1]);
        result.put("Comparisons", actionCount[0] + 1);
        result.put("ArraySize", array.length);
        return result;
    }

    public static HashMap<String, Integer> customSort2(int[] array) {
        HashMap<String, Integer> result = new HashMap<>();
        int comparisonCount = 0, swapCount = 0;
        int length = array.length;
        for (int i = 1; i < length; ++i) {
            comparisonCount++;
            int j = i;
            while (j > 0 && array[j - 1] > array[j]) {
                comparisonCount += 2;
                swapValues(array, j - 1, j);
                swapCount++;
                j = j - 1;
            }
        }
        result.put("Comparisons", comparisonCount + 1);
        result.put("Swaps", swapCount);
        result.put("ArraySize", length);
        return result;
    }

    public static int[] mergeArrays(int[] array, int left, int middle, int right) {
        int[] actions = {0, 0}; // Comparisons, Swaps
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
            actions[0]++;
        }
        for (int i = 0; i < n2; ++i) {
            rightArray[i] = array[middle + i + 1];
            actions[0]++;
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            actions[0] += 2;
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
                actions[0]++;
                actions[1]++;
            } else {
                array[k] = rightArray[j];
                j++;
                actions[1]++;
            }
            k++;
        }

        while (i < n1) {
            actions[0]++;
            array[k] = leftArray[i];
            actions[1]++;
            i++;
            k++;
        }

        while (j < n2) {
            actions[0]++;
            array[k] = rightArray[j];
            actions[1]++;
            j++;
            k++;
        }
        return actions;
    }

    public static HashMap<String, Integer> customSort3(int[] array, int left, int right, int comparisons, int swaps) {
        int[] actions = {comparisons, swaps};
        if (left < right) {
            int middle = left + (right - left) / 2;
            customSort3(array, left, middle, actions[0] + 1, actions[1]);
            customSort3(array, middle + 1, right, actions[0] + 1, actions[1]);
            actions = mergeArrays(array, left, middle, right);
        }
        HashMap<String, Integer> result = new HashMap<>();
        result.put("Swaps", actions[1]);
        result.put("Comparisons", actions[0] + 1);
        result.put("ArraySize", array.length);
        return result;
    }

    public static HashMap<String, HashMap<String, Integer>> runCustomSortTests1() {
        int[] bestCase = {1, 2, 3, 4, 5, 7, 9, 11, 22, 33, 35};
        int[] worstCase = {30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15};

        HashMap<String, Integer> customSort1Best = customSort1(bestCase.clone(), 0, bestCase.length - 1);
        HashMap<String, Integer> customSort1Worst = customSort1(worstCase.clone(), 0, worstCase.length - 1);

        HashMap<String, HashMap<String, Integer>> output = new HashMap<>();
        output.put("BestCase", customSort1Best);
        output.put("WorstCase", customSort1Worst);
        return output;
    }

    public static HashMap<String, HashMap<String, Integer>> runCustomSortTests2() {
        int[] bestCase = {1, 2, 3, 4, 5, 7, 9, 11, 22, 33, 35};
        int[] worstCase = {30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15};

        HashMap<String, Integer> customSort2Best = customSort2(bestCase.clone());
        HashMap<String, Integer> customSort2Worst = customSort2(worstCase.clone());

        HashMap<String, HashMap<String, Integer>> output = new HashMap<>();
        output.put("BestCase", customSort2Best);
        output.put("WorstCase", customSort2Worst);
        return output;
    }

    public static HashMap<String, HashMap<String, Integer>> runCustomSortTests3() {
        int[] bestCase = {1, 2, 3, 4, 5, 7, 9, 11, 22, 33, 35};
        int[] worstCase = {30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15};

        HashMap<String, Integer> customSort3Best = customSort3(bestCase.clone(), 0, bestCase.length - 1, 0, 0);
        HashMap<String, Integer> customSort3Worst = customSort3(worstCase.clone(), 0, worstCase.length - 1, 0, 0);

        HashMap<String, HashMap<String, Integer>> output = new HashMap<>();
        output.put("BestCase", customSort3Best);
        output.put("WorstCase", customSort3Worst);
        return output;
    }

    public static void generateAndExportResults() throws FileNotFoundException {
        PrintStream outputFile = new PrintStream(new File("CustomSorting_Results.csv"));

        HashMap<String, HashMap<String, Integer>> customSortTests1 = runCustomSortTests1();
        HashMap<String, HashMap<String, Integer>> customSortTests2 = runCustomSortTests2();
        HashMap<String, HashMap<String, Integer>> customSortTests3 = runCustomSortTests3();

        outputFile.println("Custom Sorting Algorithm 1," + customSortTests1.keySet() + "," +
                customSortTests1.get("WorstCase") + "," + customSortTests1.get("BestCase"));
        outputFile.println("Custom Sorting Algorithm 2," + customSortTests2.keySet() + "," +
                customSortTests2.get("WorstCase") + "," + customSortTests2.get("BestCase"));
        outputFile.println("Custom Sorting Algorithm 3," + customSortTests3.keySet() + "," +
                customSortTests3.get("WorstCase") + "," + customSortTests3.get("BestCase"));
    }
}