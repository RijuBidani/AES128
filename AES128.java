package com.riju.AES128;

import java.util.ArrayList;
import java.util.Scanner;

public class AES128 {
	public static void main(String[] args) {
		
		String[][] Sbox = new String[17][17];
		Sbox[0] = new String[] {"", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f"};
		Sbox[1] = new String[] {"00", "63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"};
		Sbox[2] = new String[] {"10", "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"};
		Sbox[3] = new String[] {"20", "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"};
		Sbox[4] = new String[] {"30", "04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"};
		Sbox[5] = new String[] {"40", "09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"};
		Sbox[6] = new String[] {"50", "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"};
		Sbox[7] = new String[] {"60", "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"};
		Sbox[8] = new String[] {"70", "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"};
		Sbox[9] = new String[] {"80", "cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"};
		Sbox[10] = new String[] {"90", "60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"};
		Sbox[11] = new String[] {"a0", "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"};
		Sbox[12] = new String[] {"b0", "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"};
		Sbox[13] = new String[] {"c0", "ba", "78", "25", "2e", "1c", "a6", "b4", "c6",	"e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"};
		Sbox[14] = new String[] {"d0", "70", "3e", "b5", "66", "48", "03", "f6", "0e",	"61", "35", "57", "b9", "86", "c1", "1d", "9e"};
		Sbox[15] = new String[] {"e0", "e1", "f8", "98", "11", "69", "d9", "8e", "94",	"9b", "1e", "87", "e9", "ce", "55", "28", "df"};
		Sbox[16] = new String[] {"f0", "8c", "a1", "89", "0d", "bf", "e6", "42", "68",	"41", "99", "2d", "0f", "b0", "54", "bb", "16"};
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Please enter the plaintext: ");
		String p = sc.nextLine();
		int[][] pint = new int[4][4];
		String[][] ps = new String[4][4];
		int idx = 0;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				pint[j][i] = p.charAt(idx);
				ps[j][i] = Integer.toHexString(pint[j][i]);
				idx++;
			}
		}
		
//		System.out.print("Plaintext in hex: ");
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(ps[j][i] + " ");
//			}
//		}
		System.out.print("Please enter the encryption key: ");
		String k0 = sc.nextLine();
		int[][] k0int = new int[4][4];
		int[][][] keys = new int[11][4][4];
		String[][] k0s = new String[4][4];
		int idx1 = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				k0int[j][i] = k0.charAt(idx1);
				k0s[j][i] = Integer.toHexString(k0int[j][i]);
				idx1++;
			}
		}
		
		keys[0] = k0int;
		
//		System.out.print("Encrpytion key in hex: ");
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(Integer.toHexString(keys[0][j][i]) + " ");
//			}
//		}
 		
		String[][] encryptedHex = encrypt(pint, keys, Sbox);
//		System.out.print("Encrypted plaintext in hex is: ");
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(encryptedHex[j][i] + " ");
//			}
//		}
		System.out.println();
		System.out.print("Encrypted text is: ");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print((char) Integer.parseInt(encryptedHex[j][i], 16) + " ");
			}
		}
		
	}
	
	public static void rotWord(int[] arr) {
		int num = arr[0];
		for (int i = 1; i < 4; i++) {
			arr[i - 1] = arr[i];
		}
		arr[3] = num;
	}
	
	public static void rotWordStr(String[] arr) {
		String str = arr[0];
		for (int i = 1; i < 4; i++) {
			arr[i - 1] = arr[i];
		}
		arr[3] = str;
	}
	
	public static void rotWord2Str (String[] arr) {
		String[] arr1 = {arr[0], arr[1]};
		
		for (int i = 2; i < 4; i++) {
			arr[i - 2] = arr[i];
		}
		
		for (int i = 2; i < 4; i++) {
			arr[i] = arr1[i - 2];
		}
	}
	
	public static void rotWord3Str (String[] arr) {
		String[] arr1 = {arr[0], arr[1], arr[2]};
		
		arr[0] = arr[3];
		
		for (int i = 1; i < 4; i++) {
			arr[i] = arr1[i - 1];
		}
	}
	
	public static void shiftRows (String[][] arr) {
		rotWordStr(arr[1]);
		rotWord2Str(arr[2]);
		rotWord3Str(arr[3]);
	}
	
	public static void mixColumns (int[][] arr) {
		int[][] constMtrx = {{2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2}};
		int[][] newArr = new int[4][4];
		for (int j = 0; j < arr[0].length; j++) {
			for (int i = 0; i < constMtrx.length; i++) {
				for (int k = 0; k < arr.length; k++) {
						switch (constMtrx[i][k]) {
							case 1:
								newArr[i][j] ^= arr[k][j];
								break;
							case 2:
								if (arr[k][j] == 0) {
									newArr[i][j] ^= 0;
								}
								else {
									newArr[i][j] ^= xor2(arr[k][j]);
								}
								break;
							case 3:
								if (arr[k][j] == 0) {
									newArr[i][j] ^= 0;
								}
								else {
									newArr[i][j] ^= xor3 (arr[k][j]);
								}
								break;
						}
					}
				}
			}
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr[i][j] = newArr[i][j];
			}
		}
	}
	
	private static int xor2 (int n) {
		String s = Integer.toBinaryString(n);
		int x = 1;
		ArrayList<Integer> ls = new ArrayList<>();
		
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '1') {
				ls.add(s.length() - i - 1 + x);
			}
		}
		
		StringBuilder str = new StringBuilder(s + "0");
		for (int i = ls.get(0); i < ls.get(ls.size() - 1) + 1; i++) {
			if (ls.contains(i)) {
				str.setCharAt(ls.get(ls.size() - 1) - i, '1');
			} else {
				str.setCharAt(ls.get(ls.size() - 1) - i, '0');
			}
		}
		
		int num = Integer.parseInt(str.toString(), 2);
		if (num > 255) {
			num ^= 283;
		}
		return num;
	}

	private static int xor3 (int n) {
		String s = Integer.toBinaryString(n);
		int x = 1;
		ArrayList<Integer> ls1 = new ArrayList<>();
		ArrayList<Integer> ls2 = new ArrayList<>();
		
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '1') {
				ls1.add(s.length() - i - 1);
				ls2.add(s.length() - i - 1 + x);
			}
		}
		
		ArrayList<Integer> ls = new ArrayList<>();
		int min = Math.min(ls1.get(0), ls2.get(0));
		int max = Math.max(ls1.get(ls1.size() - 1), ls2.get(ls2.size() - 1));
		
		for (int i = min; i <= max; i++) {
			if ((ls1.contains(i) && !ls2.contains(i)) || (ls2.contains(i) && !ls1.contains(i))) {
				ls.add(i);
			}
		}
		
		StringBuilder str = new StringBuilder(s + "0");
		for (int i = 0; i < ls.get(ls.size() - 1) + 1; i++) {
			if (ls.contains(i)) {
				str.setCharAt(ls.get(ls.size() - 1) - i, '1');
			} else {
				str.setCharAt(ls.get(ls.size() - 1) - i, '0');
			}
		}
		
		int num = Integer.parseInt(str.toString(), 2);
		if (num > 255) {
			num ^= 283;
		}
		return num;
	}
	
	public static int indexOf (String[] sarr, String str) {
		for (int i = 0; i < sarr.length; i++) {
			if (sarr[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void subWord (String[] sarr, String[][] Sbox) {
		for (int i = 0; i < 4; i++) {
			if (sarr[i].length() == 1) {
				String s = "0" + sarr[i];
				sarr[i] = Sbox[1][indexOf(Sbox[0], s)];
			} else {
				String s1 = String.valueOf(sarr[i].charAt(0)) + "0";
				String s2 = "0" + String.valueOf(sarr[i].charAt(1));
				int index1 = -1;
				for (int j = 0; j < 17; j++) {
					if (Sbox[j][0].equals(s1)) {
						index1 = j;
					}
				}
				sarr[i] = Sbox[index1][indexOf(Sbox[0], s2)];
			} 
		}
	}
	
	public static void subBytes (String[][] sarr, String[][] Sbox) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (sarr[i][j].length() == 1) {
					String s = "0" + sarr[i][j];
					sarr[i][j] = Sbox[1][indexOf(Sbox[0], s)];
				} else {
					String s1 = String.valueOf(sarr[i][j].charAt(0)) + "0";
					String s2 = "0" + String.valueOf(sarr[i][j].charAt(1));
					int index1 = -1;
					for (int k = 0; k < 17; k++) {
						if (Sbox[k][0].equals(s1)) {
							index1 = k;
						}
					}
					sarr[i][j] = Sbox[index1][indexOf(Sbox[0], s2)];
				}
			}
		}
	}
	
	public static String[] rcon (int[] arr, int round) {
		int cst = 0;
		int l2r = 27;
		if (round < 9) {
			cst = (int) Math.pow(2, round - 1);
		} else {
			cst = (round - 8) * l2r;
		}
		int[] arr1 = {cst, 0, 0, 0};
		String[] arr1s = new String[4];
		
		for (int i = 0; i < 4; i++) {
			arr[i] = arr[i] ^ arr1[i];
			arr1s[i] = Integer.toHexString(arr[i]);
		}
		
		return arr1s;
	}
	
	public static int[][] addRoundKey (int[][] arr, int[][] key) {
		int[][] newArr = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newArr[i][j] = arr[i][j] ^ key[i][j];
			}
		}
		return newArr;
	}
	
	public static String[][] encrypt (int[][] arr, int[][][] keys, String[][] Sbox) {
		int[][] arr0 = addRoundKey (arr, keys[0]);
		createKeys(keys, keys[0], Sbox);
		String[][] arr0s = new String[4][4];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				arr0s[i][j] = Integer.toHexString(arr0[i][j]);
			}
		}
		
		String[][] currentArr = arr0s;
		
		for (int i = 1; i < 11; i++) {
			subBytes(currentArr, Sbox);
			shiftRows(currentArr);
			int[][] currentArrI = new int[4][4];
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					currentArrI[j][k] = Integer.parseInt(currentArr[j][k], 16);
				}
			}
			if (i < 10) {
				mixColumns(currentArrI);
			}
			int[][] currentArrIM = addRoundKey(currentArrI, keys[i]);
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					currentArr[j][k] = Integer.toHexString(currentArrIM[j][k]);
				}
			}
		}
		
		return currentArr;
	}
	
	public static void createKeys (int[][][] keys, int[][] key, String[][] Sbox) {
		
		int[][] currentKey = key;
		for (int i = 1; i < 11; i++) {
			int[] wlast = new int[4];
			String[] wlasts = new String[4];
			
			for (int j = 0; j < 4; j++) {
				wlast[j] = currentKey[j][3];
			}
			
			rotWord(wlast);
			
			for (int j = 0; j < 4; j++) {
				wlasts[j] = Integer.toHexString(wlast[j]);
			}
			
			subWord(wlasts, Sbox);
			
			int[] wlastsi = new int[4];
			
			for (int j = 0; j < 4; j++) {
				wlastsi[j] = Integer.parseInt(wlasts[j], 16);
			}
			
			String[] wlastsis = rcon(wlastsi, i);
			
			int[] wlastsisi = new int[4];
			
			for (int j = 0; j < 4; j++) {
				wlastsisi[j] = Integer.parseInt(wlastsis[j], 16);
			}
			
			for (int j = 0; j < 4; j++) {
				keys[i][j][0] = currentKey[j][0] ^ wlastsisi[j];
				keys[i][j][1] = currentKey[j][1] ^ keys[i][j][0];
				keys[i][j][2] = currentKey[j][2] ^ keys[i][j][1];
				keys[i][j][3] = currentKey[j][3] ^ keys[i][j][2];
			}
			
			currentKey = keys[i];
		}
	}
	
}
