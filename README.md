The Advanced Encryption Standard specifies a symmetric-key cryptographic algorithm i.e. the same key is used for encrypting/decrypting data. There are 2 inputs: text divided into 128-bit blocks, and a key which can be 128, 192 or 256 bits long. This project demonstrates AES-128 with a single block of text and a 128-bit key. After taking an input in the Main.java file to decide whether to implement encryption or decryption, either AES128Encryption.java or AES128Decryption.java have their main method called, at which point the user is asked to enter text which is either called the plaintext (for encryption) or the ciphertext (for decryption) as well as the key. 

The process of encryption or decryption involves 10 rounds of substitutions and permutations on the plaintext/ciphertext. The program uses 4×4 matrices to store the text. Before the rounds, we implement key expansion on the input key, which generates 10 subkeys (same for both encryption and decryption). During encryption, we XOR the plaintext with the input key followed by the 10 rounds of processing, each utilizing subkeys 1 to 10 respectively. During decryption, we XOR the ciphertext with the 10th subkey before 9 rounds of processing using subkeys 9 to 1 in that order, and a 10th and final round utilizing the input key.

Key expansion involves operations on each column/32-bit word of the key from the previous round in the current round. The 10 subkeys are computed as follows:

For the very first round, the operations involved are performed on the input key. The round 1 key is computed by computing each column one by one. If the input key is referred to as K(0) and split into four 32-bit words each referred to as w(i) where i starts from 0, then we can represent the input key as follows:

K(0) = [w(0), w(1), w(2), w(3)]

Then the words of K(1) or the "round 1" key, i.e. w(4), w(5), w(6) and w(7) can be computed as follows:

w(i) = w(i - 4) ⊕ w(i - 1) (where ⊕ represents the XOR operation)

When i is a multiple of 4, we have to process w(i - 1) with the functions RotWord, SubWord and Rcon. To compute K(2) = [w(8), w(9), w(10), w(11)], we apply the same XOR operations and (optionally) functions to K(1) = [w(4), w(5), w(6), w(7)] that we applied to K(0) when computing K(1). This process is repeated 10 times to generate a total of 11 keys. The functions RotWord, SubWord and Rcon are explained below.

1) RotWord:
   RotWord simply takes a 4-byte (32-bit) word represented as e.g. [a(0), a(1), a(2), a(3)] and returns [a(1), a(2), a(3), a(0)].
2) SubWord:
   SubWord takes a 4-byte word [a(0), a(1), a(2), a(3)] and substitutes each byte with a different byte. This substitution process is also used during the 10 encryption rounds in the main algorithm.
3) Rcon:
   Rcon XORs the input 4-byte word with a round constant word array, which takes the form [rc(i), 0, 0, 0] where i is the round number.

During encryption, after the first key K(0) has been XOR'd with the plaintext P and the 10 keys K(1), K(2), ..., K(10) have been generated, 10 rounds of substitution and permutation are applied to the output of P ⊕ K(0). In the first 9 rounds, four functions SubBytes, ShiftRows, MixColumns and AddRoundKey are applied in that order, while the last round skips MixColumns. These functions are explained below:

1) SubBytes:
   This function is similar to SubWord except that the input is not a 4-byte word, but a 4×4 matrix. Each byte in the matrix is substituted by a different byte.
2) ShiftRows:
   This function implements a cyclical rotation. The steps involved are:
   i) The first row is left as is.
   ii) The second row is shifted left by 1 byte.
   iii) The third row is shifted left by 2 bytes.
   iv) The fourth row is shifted left by 3 bytes.
3) MixColumns multiplies each column of the 4×4 input matrix with a 4×4 constant matrix. Here, the multiplication operation is performed on a special type of set instead of the set of base-10 (decimal) real numbers and is thus not the same as regular multiplication.
4) AddRoundKey just XOR's the key for the current round i, i.e. K(i), with the output of the previous round.

The final output of the algorithm is a 4×4 matrix that is converted back into text by the program to reveal the final output. A few helper functions are employed during this process.

Decryption is similar; four functions InvShiftRows, InvSubBytes, AddRoundKey and InvMixColumns are applied in that order in the first 9 rounds, while the last round skips the InvMixColumns step. The AddRoundKey
function is the same as the one in the encryption process, it just takes a different key as input (as explained in the 2nd and 3rd paragraphs). The functions used in decryption are explained below:

1) InvShiftRows:
   This function implements a cyclical rotation that is the exact opposite of the rotation implemented by ShiftRows. The steps are:
   i) The first row is left as is.
   ii) The second row is shifted right by 1 byte.
   iii) The third row is shifted right by 2 bytes.
   iv) The fourth row is shifted right by 3 bytes.
2) InvSubBytes:
   This function applies the exact inverse of the substitution appplied SubWord or SubBytes.
3) InvMixColumns multiplies each column of the input 4×4 matrix with a different 4×4 constant matrix, not the one used in MixColumns.

The output is again a 4×4 matrix that is, in a final step, converted into the decrypted text. A few helper functions are employed during decryption as well.
