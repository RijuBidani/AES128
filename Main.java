package com.riju.AES128;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you want to encrypt plaintext or decrypt ciphertext?");
		System.out.print("Enter D for decryption or E for encryption: ");
		char c = sc.next().charAt(0);
		if (c == 'D') {
			AES128Decryption.main(args);
		} else {
			AES128Encryption.main(args);
		}
	}

}
