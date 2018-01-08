# Asynchronous Ratcheting Tree

Asynchronous Ratcheting Tree (ART) is a protocol for end-to-end encrypted group
messaging. It aims to provide scalable group messaging while maintaining strong
security guarantees.

This repository contains the implementation used for the results in [our ART paper](https://eprint.iacr.org/2017/666).
It contains implementations of ART and a pairwise Diffie-Hellman based ratcheting protocol which we used for comparison.

## Requirements

Asynchronous Ratcheting Trees requires or works with:
* Java 1.8.
* Apache Maven 1.8.
* Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files
  (http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html).
* Apache Thrift 0.10.0 (just for rebuilding the Thrift definitions).

## Building and executing
The Makefile in this directory will compile our source and then run the
benchmarks; simply run

	make

## The Team

The team who worked on the Asynchronous Ratcheting Tree paper are as follows:

From Facebook:
* Jon Millican

From Oxford University:
* Katriel Cohn-Gordon
* Cas Cremers
* Luke Garratt
* Kevin Milner

We are grateful to our collaborators from Oxford University for the considerable
time and expertise that they put in to this paper, and into the formal proofs of
the ART protocol.

## License

Asynchronous Ratcheting Tree is CC-BY-NC-licensed.
