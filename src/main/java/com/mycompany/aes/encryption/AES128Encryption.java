/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes.encryption;

/**
 *
 * @author Riju Bidani
 */
public class AES128Encryption extends Methods {
	
	public static String encrypt(String plaintext, String key) {
		int[][] state = stringToState(plaintext);
		int[][][] keys = buildRoundKeys(key);
		int[][] encrypted = encryptState(state, keys);
		return stateToString(encrypted);
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
	
	private static int[][] encryptState (int[][] arr, int[][][] keys) {
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
