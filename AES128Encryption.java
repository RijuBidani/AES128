package com.riju.AES128;

import java.util.Scanner;

public class AES128Encryption extends Methods {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Please enter the plaintext: ");
		String p = sc.nextLine();
		int[][] pint = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				pint[i][j] = p.charAt(i + 4 * j);
			}
		}

		System.out.print("Please enter the encryption key: ");
		String k0 = sc.nextLine();
		int[][] k0int = new int[4][4];
		int[][][] keys = new int[11][4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				k0int[i][j] = k0.charAt(i + 4 * j);
			}
		}
		
		keys[0] = k0int;
 		
		int[][] encrypted = encrypt(pint, keys);
		System.out.println();
		System.out.print("Encrypted text is: ");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print((char) encrypted[j][i]);
			}
		}
		
	}
	
	public static void shiftRows (int[][] arr) {
		rotWord(arr[1]);
		rotWord2(arr[2]);
		rotWord3(arr[3]);
	}
	
	public static void mixColumns (int[][] arr) {
		int[][] constMtrx = {{2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2}};
		int[][] newArr = new int[4][4];
		for (int j = 0; j < arr[0].length; j++) {
			for (int i = 0; i < constMtrx.length; i++) {
				for (int k = 0; k < arr.length; k++) {
						newArr[i][j] ^= multiply (arr[k][j], constMtrx[i][k]);
					}
				}
			}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr[i][j] = newArr[i][j];
			}
		}
	}
	
	public static void subBytes (int[][] arr) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int n = multInv(arr[i][j]);
				arr[i][j] = multiplyM257(n, 31) ^ 99;
			}
		}
	}
	
	public static int[][] encrypt (int[][] arr, int[][][] keys) {
		int[][] curArr = addRoundKey (arr, keys[0]);
		createKeys(keys, keys[0]);
		
		for (int i = 1; i < 11; i++) {
			subBytes(curArr);
			shiftRows(curArr);
			if (i < 10) {
				mixColumns(curArr);
			}
			curArr = addRoundKey(curArr, keys[i]);
		}
		
		return curArr;
	}
	
}
