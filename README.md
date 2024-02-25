The Advanced Encryption Standard algorithm is a popular cryptographic algorithm for working with sensitive data. This project utilizes two-dimensional (2D) arrays/matrices to implement the encryption of a 128-bit 
block of text (referred to as plaintext) being the input. The program is written in Java.

AES typically has three different sizes for the encryption keys used in the algorithm. This project works with a 128-bit key (192-bit and 256-bit keys can also be used in other variants of the algorithm). The 
plaintext and the key can each be stored as a 4×4 matrix.

With a 128-bit key, the process of encryption involves 10 rounds of substitutions and permutations performed on the matrix representing the plaintext. Each round requires a different key, and these keys are 
derived before the 10 rounds using a process referred to as key expansion, that also requires multiple steps. 

Key expansion involves operations on each column or 32-bit word of the key from the previous round in the current round. The process generates 11 keys, since the very first key is used before even starting with the 
rounds of substitution and permutation. The very first key is simply XOR'd with the plaintext P (here, XOR refers to a Boolean logic operation). After this, the remaining 10 keys are computed by utilizing the
following algorithm:

Each new key depends on the previous key. For the very first round, the operations involved are performed on the "round 0" key or input key. The key for round 1 is computed by computing each column, or 32-bit word,
one by one. If the input key is referred to as K(0) and split into four 32-bit words each referred to as w(i) where i starts from 0, then we can represent the input key as follows:

K(0) = [w(0), w(1), w(2), w(3)]

Then the words of K(1) or the "round 1" key, i.e. w(4), w(5), w(6) and w(7) can be computed as follows:

w(i) = w(i - 4) ⊕ w(i - 1) (where ⊕ represents the XOR operation)

There is an exception, however. When i is a multiple of 4, e.g. 4, we have to process the second operand in the XOR operation i.e. w(i - 1) with certain permutation and substitution steps, referred to as the 
functions RotWord, SubWord and Rcon. To compute K(2) = [w(8), w(9), w(10), w(11)], we apply the same XOR operations and permutations and substitutions to K(1) = [w(4), w(5), w(6), w(7)] that we applied to K(0)
when computing K(1). This process is repeated 10 times to generate a total of 11 keys. The functions RotWord, SubWord and Rcon are explained below.

1) RotWord
   RotWord simply takes a 4-byte (32-bit) word represented as e.g. [a(0), a(1), a(2), a(3)] and returns [a(1), a(2), a(3), a(0)].
2) SubWord
   SubWord takes a 4-byte word [a(0), a(1), a(2), a(3)] and passes it through a lookup table (called the S-box) that maps an 8-bit input to an 8-bit output. The S-box is also used during the 10 encryption rounds
   in the main algorithm.
3) Rcon
   Rcon XORs the input 4-byte word with a round constant word array, which takes the form [rc(i), 0, 0, 0] where i is the round number.

After the first key K(0) has been XOR'd with the plaintext P and the 10 keys K(1), K(2), ..., K(10) have been generated, 10 rounds of substitution and permutation are applied to the output of "round 0" i.e. 
P' = P ⊕ K(0). In the first 9 rounds, four functions SubBytes, ShiftRows, MixColumns and AddRoundKey are applied, while the last round only differs in that the MixColumns step is not applied. These functions
are explained below:

1) SubBytes
   This function is similar to SubWord except that the input is not a 4-byte word, but a 4×4 matrix. Each element of the matrix is passed as input to the S-box and replaced by the output.
2) ShiftRows
   This function implements a cyclical rotation. The steps involved are:
   i) The first row is left as is.
   ii) The second row is shifted left by 1 byte.
   iii) The third row is shifted left by 2 bytes.
   iv) The fourth row is shifted left by 3 bytes.
3) MixColumns multiplies each column of the 4×4 input matrix with a 4×4 constant matrix. Here, the multiplication operation is performed on a special type of set and is thus not the same as regular multiplication.
4) AddRoundKey just XOR's the key for the current round i, i.e. K(i), with the output of the previous round.

The final output of the algorithm is a 4×4 matrix that is converted back into text by the program to reveal the final output. The program implements all the functions discussed above as well as a few helper 
functions. 
