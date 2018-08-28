import java.util.*;
import java.io.*;

public class MaxBinHeap<T extends Comparable<T>> {
  private T[] arrHeap;
  private int nObj;

  public CreateEmptyMaxBinHeap() {
    arrHeap = (T[])new Comparable[1];
    nObj = 0;
  }

  public insert(T obj) {
    nObj++;
    arrHeapNew = (T[])new Comparable[nObj];
    for (i=0; i<nObj-1; i++) {
      arrHeapNew[i] = arrHeap[i];
    }
    arrHeapNew[nObj] = obj;
    this.arrHeap = arrHeapNew;
    shiftUp(nObj);
  }

  private shiftUp(int i) {
    while (i > 1 && (arrHeap[parent(i)].compareTo(arrHeap[i]) == -1)) {
      swap(arrHeap[i], arrHeap[parent(i)]);
      i = parent(i);
    }
  }

}
