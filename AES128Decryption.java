package com.riju.AES128;

import java.util.Scanner;

public class AES128Decryption extends Methods {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Please enter the ciphertext: ");
		String c = sc.nextLine();
		int[][] cint = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cint[i][j] = c.charAt(i + 4 * j);
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
		
		int[][] decrypted = decrypt(cint, keys);
		
		System.out.println();
		System.out.print("Decrypted text is: ");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print((char) decrypted[j][i]);
			}
		}
		
	}
	
	public static void invShiftRows (int[][] arr) {
		rotWord3(arr[1]);
		rotWord2(arr[2]);
		rotWord(arr[3]);
	}
	
	public static void invMixColumns (int[][] arr) {
		int[][] constMtrx = {{14, 11, 13, 9}, {9, 14, 11, 13}, {13, 9, 14, 11}, {11, 13, 9, 14}};
		int[][] newArr = new int[4][4];
		
		for (int j = 0; j < arr[0].length; j++) {
			for (int i = 0; i < constMtrx.length; i++) {
				for (int k = 0; k < arr.length; k++) {
					newArr[i][j] ^= multiply(arr[k][j], constMtrx[i][k]);
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr[i][j] = newArr[i][j];
			}
		}
	}
	
	public static void invSubBytes (int[][] arr) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int n = multiplyM257(arr[i][j], 74) ^ 5;
				arr[i][j] = multInv(n);
			}
		}
	}
	
	public static int[][] decrypt (int[][] arr, int[][][] keys) {
		createKeys(keys, keys[0]);
		int[][] curArr = addRoundKey(arr, keys[10]);
		
		for (int i = 1; i < 11; i++) {
			invShiftRows(curArr);
			invSubBytes(curArr);
			curArr = addRoundKey(curArr, keys[10 - i]);
			if (i < 10) {
				invMixColumns(curArr);
			}
		}
		
		return curArr;
	}

}
