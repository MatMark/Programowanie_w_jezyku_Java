package com.company;

import java.util.Arrays;
import java.util.Collections;

public class SortMain {
    static{ System.loadLibrary("SortMain");}
    public Double[] a = {1.4d, 1.3d, 13.1d, 22d, 2d, 9.9d };
    public Double[] b;
    public Boolean order = true;
    public native Double[] sort01(Double[] a, Boolean order);
    public native Double[] sort02(Double[] a);
    public native void sort03();
    public void sort04(){
        if(order) {
            b = a;
            Arrays.sort(b);
        }
        else{
            b = a;
            Arrays.sort(b, Collections.reverseOrder());
        }
    }


    public void setOrder(Boolean order) { this.order = order; }

    public static void main(String[] args) {
        SortMain sortMain = new SortMain();
        System.out.println("Sort1 rosnąco: "+Arrays.toString(sortMain.sort01(sortMain.a,true)));
        System.out.println("Sort1 malejąco: "+Arrays.toString(sortMain.sort01(sortMain.a,false)));
        System.out.println("Sort2 rosnąco: "+Arrays.toString(sortMain.sort02(sortMain.a)));
        sortMain.setOrder(false);
        System.out.println("Sort2 malejąco: "+Arrays.toString(sortMain.sort02(sortMain.a)));
        sortMain.sort03();
        System.out.println("Sort3 (Sort4): "+Arrays.toString(sortMain.a));
    }
}

