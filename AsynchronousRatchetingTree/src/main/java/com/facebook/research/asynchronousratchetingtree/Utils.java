/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.art.message.thrift.NodeStruct;
import com.facebook.research.asynchronousratchetingtree.art.tree.Node;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;

import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class Utils {
  public static void print(String a, Object... args) {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
    System.out.printf("[" + f.format(new Date()) + "] " + a + "\n", args);
  }

  public static void printHex(byte[] a) {
    print(DatatypeConverter.printHexBinary(a));
  }

  public static void printTree(Node tree) {
    NodeStruct struct = Node.toThrift(tree);
    TSerializer serialiser = new TSerializer(new TSimpleJSONProtocol.Factory());
    try {
      Utils.print(new String(serialiser.serialize(hexifyNodeStruct(struct))));
    } catch (TException e) {
      Utils.except(e);
    }
  }

  public static NodeStruct hexifyNodeStruct(NodeStruct tree) {
    if (tree == null) {
      return null;
    }
    NodeStruct result = new NodeStruct();
    result.setPublicKey(DatatypeConverter.printHexBinary(tree.getPublicKey()).getBytes());
    result.setLeft(hexifyNodeStruct(tree.getLeft()));
    result.setRight(hexifyNodeStruct(tree.getRight()));
    return result;
  }

  /**
   * Throw a RuntimeException to simplify the example code by avoiding Java's explicit error handling.
   * Yet another reason that this could should absolutely never be used as-is.
   * The method throws, but also has the exception as its return type, so we can make the compiler happy by throwing
   * the output, or just call it raw.
   */
  public static RuntimeException except(Exception e) {
    print(e.getClass().getSimpleName());
    print(e.getMessage());
    e.printStackTrace();
    throw new RuntimeException();
  }

  public static RuntimeException except(String s) {
    print(s);
    throw new RuntimeException();
  }

  public static byte[] serialise(TBase thrift) {
    TSerializer serialiser = new TSerializer(new TCompactProtocol.Factory());
    try {
      return serialiser.serialize(thrift);
    } catch (TException e) {
      throw Utils.except(e);
    }
  }

  public static <TObject extends TBase> void deserialise(TObject object, byte[] data) {
    TDeserializer deserialiser = new TDeserializer(new TCompactProtocol.Factory());
    try {
      deserialiser.deserialize(object, data);
    } catch (TException e) {
      Utils.except(e);
    }
  }

  public static long startBenchmark() {
    return new Date().toInstant().toEpochMilli();
  }

  public static long getBenchmarkDelta(long start) {
    return new Date().toInstant().toEpochMilli() - start;
  }
}
