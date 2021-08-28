package Arrays_md_mas10;

import Common.General;

public class Main {
	public static void main(String[] args) {
		int[][] arr = new int[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++) arr[i][j] = General.random(0, 40); 
		}
		double[] avg = new double[10];
		for(int i = 0; i < 10; i++){
			int sum = 0;
			for(int el : arr[i]) sum += el;
			avg[i] = sum / 10.0;
		}
		System.out.println("Оригинальный массив:");
		for(int line = 0; line < 10; line++){
			System.out.print(""+ (line+1) + ": ");
			for(int character : arr[line]) System.out.print(character + " ");
			System.out.println();
		}
		System.out.println("Средние значения:");
		for(int average = 0; average < 10; average++){
			System.out.println(""+ (average+1) + ": " + avg[average]);
		}
	}
}
