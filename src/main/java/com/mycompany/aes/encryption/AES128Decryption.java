/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aes.encryption;

/**
 *
 * @author Riju Bidani
 */
public class AES128Decryption extends Methods {

	public static String decrypt(String ciphertext, String key) {
		int[][] state = stringToState(ciphertext);
		int[][][] keys = buildRoundKeys(key);
		int[][] decrypted = decryptState(state, keys);
		return stateToString(decrypted);
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
	
	private static int[][] decryptState (int[][] arr, int[][][] keys) {
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
