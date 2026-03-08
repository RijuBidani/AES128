/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes.encryption;

/**
 *
 * @author Riju Bidani
 */
public class Methods {

	private static final int BLOCK_SIZE = 16;
	
	//Permutations involved in AES
	
	public static void rotWord(int[] arr) {
		int num = arr[0];
		
		for (int i = 1; i < 4; i++) {
			arr[i - 1] = arr[i];
		}
		
		arr[3] = num;
	}
	
	public static void rotWord2 (int[] arr) {
		int[] arr1 = {arr[0], arr[1]};
		
		for (int i = 2; i < 4; i++) {
			arr[i - 2] = arr[i];
		}
		
		for (int i = 2; i < 4; i++) {
			arr[i] = arr1[i - 2];
		}
	}
	
	public static void rotWord3 (int[] arr) {
		int[] arr1 = {arr[0], arr[1], arr[2]};
		
		arr[0] = arr[3];
		
		for (int i = 1; i < 4; i++) {
			arr[i] = arr1[i - 1];
		}
	}
	
	//Finite field operations in AES
	
	public static int doubleNum (int n) {
		if (n >> 7 != 0) {
			return (n << 1) ^ 283;
		}
		return n << 1;
	}
	
	public static int multiply (int m1, int m2) {
		int prod = 0;
		
		while (m2 > 0) {
			if ((m2 & 1) != 0) {
				prod ^= m1;
			}
			m1 = doubleNum(m1);
			m2 >>= 1;
		}
		
		return prod;
	}
	
	public static int doubleNumM257 (int n) {
		if (n >> 7 != 0) {
			return (n << 1) ^ 257;
		}
		return n << 1;
	}
	
	public static int multiplyM257 (int m1, int m2) {
		int prod = 0;
		
		while (m2 > 0) {
			if ((m2 & 1) != 0) {
				prod ^= m1;
			}
			m1 = doubleNumM257(m1);
			m2 >>= 1;
		}
		
		return prod;
	}
	
	public static int multInv (int a) {
		int[] powers = new int[7];
		powers[0] = multiply (a, a);
		int rv = powers[0];
		
		for (int i = 1; i < 7; i++) {
			powers[i] = multiply (powers[i - 1], powers[i - 1]);
			rv = multiply (rv, powers[i]);
		}
		return rv;
	}
	
	// Key expansion operations in AES
	
	public static void subWord (int[] arr) {
		for (int i = 0; i < 4; i++) {
			int n = multInv(arr[i]);
			arr[i] = multiplyM257(n, 31) ^ 99;
		}
	}
	
	public static void rcon (int[] arr, int round) {
		int rcon = 1;
		for (int i = round; i > 1; i--) {
			rcon = doubleNum(rcon);
		}
		int[] arr1 = {rcon, 0, 0, 0};
		
		for (int i = 0; i < 4; i++) {
			arr[i] = arr[i] ^ arr1[i];
		}
	}
	
	public static void f (int[] arr, int round) {
		rotWord(arr);
		subWord(arr);
		rcon(arr, round);
	}
	
	// Round key addition for the 10 encryption/decryption rounds
	
	public static int[][] addRoundKey (int[][] arr, int[][] key) {
		int[][] newArr = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newArr[i][j] = arr[i][j] ^ key[i][j];
			}
		}
		return newArr;
	}
	
	//Actual key creation method
	
	public static void createKeys (int[][][] keys, int[][] key) {
		
		int[][] currentKey = key;
		for (int i = 1; i < 11; i++) {
			int[] word = new int[4];
			
			for (int j = 0; j < 4; j++) {
				word[j] = currentKey[j][3];
			}
			
			f(word, i);
			
			for (int j = 0; j < 4; j++) {
				keys[i][j][0] = currentKey[j][0] ^ word[j];
				keys[i][j][1] = currentKey[j][1] ^ keys[i][j][0];
				keys[i][j][2] = currentKey[j][2] ^ keys[i][j][1];
				keys[i][j][3] = currentKey[j][3] ^ keys[i][j][2];
			}
			
			currentKey = keys[i];
		}
	}
	
	protected static int[][] stringToState(String text) {
		validateBlockSize(text, "Input text");
		int[][] state = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = text.charAt(i + 4 * j);
			}
		}
		return state;
	}

	protected static String stateToString(int[][] state) {
		StringBuilder sb = new StringBuilder(BLOCK_SIZE);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sb.append((char) state[j][i]);
			}
		}
		return sb.toString();
	}

	protected static int[][][] buildRoundKeys(String key) {
		validateBlockSize(key, "Encryption key");
		int[][][] keys = new int[11][4][4];
		keys[0] = stringToState(key);
		return keys;
	}

	protected static void validateBlockSize(String input, String label) {
		if (input.length() != BLOCK_SIZE) {
			throw new IllegalArgumentException(label + " must be exactly 16 characters.");
		}
	}
	
}
