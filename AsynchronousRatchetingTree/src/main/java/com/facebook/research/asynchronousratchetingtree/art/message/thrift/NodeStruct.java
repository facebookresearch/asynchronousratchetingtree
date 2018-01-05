/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.facebook.research.asynchronousratchetingtree.art.message.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-01-05")
public class NodeStruct implements org.apache.thrift.TBase<NodeStruct, NodeStruct._Fields>, java.io.Serializable, Cloneable, Comparable<NodeStruct> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NodeStruct");

  private static final org.apache.thrift.protocol.TField PUBLIC_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("publicKey", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LEFT_FIELD_DESC = new org.apache.thrift.protocol.TField("left", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField RIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("right", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new NodeStructStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new NodeStructTupleSchemeFactory();

  public java.nio.ByteBuffer publicKey; // required
  public NodeStruct left; // optional
  public NodeStruct right; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PUBLIC_KEY((short)1, "publicKey"),
    LEFT((short)2, "left"),
    RIGHT((short)3, "right");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PUBLIC_KEY
          return PUBLIC_KEY;
        case 2: // LEFT
          return LEFT;
        case 3: // RIGHT
          return RIGHT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.LEFT,_Fields.RIGHT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PUBLIC_KEY, new org.apache.thrift.meta_data.FieldMetaData("publicKey", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.LEFT, new org.apache.thrift.meta_data.FieldMetaData("left", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "NodeStruct")));
    tmpMap.put(_Fields.RIGHT, new org.apache.thrift.meta_data.FieldMetaData("right", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "NodeStruct")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NodeStruct.class, metaDataMap);
  }

  public NodeStruct() {
  }

  public NodeStruct(
    java.nio.ByteBuffer publicKey)
  {
    this();
    this.publicKey = org.apache.thrift.TBaseHelper.copyBinary(publicKey);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NodeStruct(NodeStruct other) {
    if (other.isSetPublicKey()) {
      this.publicKey = org.apache.thrift.TBaseHelper.copyBinary(other.publicKey);
    }
    if (other.isSetLeft()) {
      this.left = new NodeStruct(other.left);
    }
    if (other.isSetRight()) {
      this.right = new NodeStruct(other.right);
    }
  }

  public NodeStruct deepCopy() {
    return new NodeStruct(this);
  }

  @Override
  public void clear() {
    this.publicKey = null;
    this.left = null;
    this.right = null;
  }

  public byte[] getPublicKey() {
    setPublicKey(org.apache.thrift.TBaseHelper.rightSize(publicKey));
    return publicKey == null ? null : publicKey.array();
  }

  public java.nio.ByteBuffer bufferForPublicKey() {
    return org.apache.thrift.TBaseHelper.copyBinary(publicKey);
  }

  public NodeStruct setPublicKey(byte[] publicKey) {
    this.publicKey = publicKey == null ? (java.nio.ByteBuffer)null : java.nio.ByteBuffer.wrap(publicKey.clone());
    return this;
  }

  public NodeStruct setPublicKey(java.nio.ByteBuffer publicKey) {
    this.publicKey = org.apache.thrift.TBaseHelper.copyBinary(publicKey);
    return this;
  }

  public void unsetPublicKey() {
    this.publicKey = null;
  }

  /** Returns true if field publicKey is set (has been assigned a value) and false otherwise */
  public boolean isSetPublicKey() {
    return this.publicKey != null;
  }

  public void setPublicKeyIsSet(boolean value) {
    if (!value) {
      this.publicKey = null;
    }
  }

  public NodeStruct getLeft() {
    return this.left;
  }

  public NodeStruct setLeft(NodeStruct left) {
    this.left = left;
    return this;
  }

  public void unsetLeft() {
    this.left = null;
  }

  /** Returns true if field left is set (has been assigned a value) and false otherwise */
  public boolean isSetLeft() {
    return this.left != null;
  }

  public void setLeftIsSet(boolean value) {
    if (!value) {
      this.left = null;
    }
  }

  public NodeStruct getRight() {
    return this.right;
  }

  public NodeStruct setRight(NodeStruct right) {
    this.right = right;
    return this;
  }

  public void unsetRight() {
    this.right = null;
  }

  /** Returns true if field right is set (has been assigned a value) and false otherwise */
  public boolean isSetRight() {
    return this.right != null;
  }

  public void setRightIsSet(boolean value) {
    if (!value) {
      this.right = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PUBLIC_KEY:
      if (value == null) {
        unsetPublicKey();
      } else {
        if (value instanceof byte[]) {
          setPublicKey((byte[])value);
        } else {
          setPublicKey((java.nio.ByteBuffer)value);
        }
      }
      break;

    case LEFT:
      if (value == null) {
        unsetLeft();
      } else {
        setLeft((NodeStruct)value);
      }
      break;

    case RIGHT:
      if (value == null) {
        unsetRight();
      } else {
        setRight((NodeStruct)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PUBLIC_KEY:
      return getPublicKey();

    case LEFT:
      return getLeft();

    case RIGHT:
      return getRight();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PUBLIC_KEY:
      return isSetPublicKey();
    case LEFT:
      return isSetLeft();
    case RIGHT:
      return isSetRight();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof NodeStruct)
      return this.equals((NodeStruct)that);
    return false;
  }

  public boolean equals(NodeStruct that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_publicKey = true && this.isSetPublicKey();
    boolean that_present_publicKey = true && that.isSetPublicKey();
    if (this_present_publicKey || that_present_publicKey) {
      if (!(this_present_publicKey && that_present_publicKey))
        return false;
      if (!this.publicKey.equals(that.publicKey))
        return false;
    }

    boolean this_present_left = true && this.isSetLeft();
    boolean that_present_left = true && that.isSetLeft();
    if (this_present_left || that_present_left) {
      if (!(this_present_left && that_present_left))
        return false;
      if (!this.left.equals(that.left))
        return false;
    }

    boolean this_present_right = true && this.isSetRight();
    boolean that_present_right = true && that.isSetRight();
    if (this_present_right || that_present_right) {
      if (!(this_present_right && that_present_right))
        return false;
      if (!this.right.equals(that.right))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPublicKey()) ? 131071 : 524287);
    if (isSetPublicKey())
      hashCode = hashCode * 8191 + publicKey.hashCode();

    hashCode = hashCode * 8191 + ((isSetLeft()) ? 131071 : 524287);
    if (isSetLeft())
      hashCode = hashCode * 8191 + left.hashCode();

    hashCode = hashCode * 8191 + ((isSetRight()) ? 131071 : 524287);
    if (isSetRight())
      hashCode = hashCode * 8191 + right.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(NodeStruct other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPublicKey()).compareTo(other.isSetPublicKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPublicKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.publicKey, other.publicKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLeft()).compareTo(other.isSetLeft());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLeft()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.left, other.left);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRight()).compareTo(other.isSetRight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.right, other.right);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("NodeStruct(");
    boolean first = true;

    sb.append("publicKey:");
    if (this.publicKey == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.publicKey, sb);
    }
    first = false;
    if (isSetLeft()) {
      if (!first) sb.append(", ");
      sb.append("left:");
      if (this.left == null) {
        sb.append("null");
      } else {
        sb.append(this.left);
      }
      first = false;
    }
    if (isSetRight()) {
      if (!first) sb.append(", ");
      sb.append("right:");
      if (this.right == null) {
        sb.append("null");
      } else {
        sb.append(this.right);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class NodeStructStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public NodeStructStandardScheme getScheme() {
      return new NodeStructStandardScheme();
    }
  }

  private static class NodeStructStandardScheme extends org.apache.thrift.scheme.StandardScheme<NodeStruct> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, NodeStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PUBLIC_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.publicKey = iprot.readBinary();
              struct.setPublicKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LEFT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.left = new NodeStruct();
              struct.left.read(iprot);
              struct.setLeftIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.right = new NodeStruct();
              struct.right.read(iprot);
              struct.setRightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, NodeStruct struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.publicKey != null) {
        oprot.writeFieldBegin(PUBLIC_KEY_FIELD_DESC);
        oprot.writeBinary(struct.publicKey);
        oprot.writeFieldEnd();
      }
      if (struct.left != null) {
        if (struct.isSetLeft()) {
          oprot.writeFieldBegin(LEFT_FIELD_DESC);
          struct.left.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.right != null) {
        if (struct.isSetRight()) {
          oprot.writeFieldBegin(RIGHT_FIELD_DESC);
          struct.right.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class NodeStructTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public NodeStructTupleScheme getScheme() {
      return new NodeStructTupleScheme();
    }
  }

  private static class NodeStructTupleScheme extends org.apache.thrift.scheme.TupleScheme<NodeStruct> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, NodeStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPublicKey()) {
        optionals.set(0);
      }
      if (struct.isSetLeft()) {
        optionals.set(1);
      }
      if (struct.isSetRight()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPublicKey()) {
        oprot.writeBinary(struct.publicKey);
      }
      if (struct.isSetLeft()) {
        struct.left.write(oprot);
      }
      if (struct.isSetRight()) {
        struct.right.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NodeStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.publicKey = iprot.readBinary();
        struct.setPublicKeyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.left = new NodeStruct();
        struct.left.read(iprot);
        struct.setLeftIsSet(true);
      }
      if (incoming.get(2)) {
        struct.right = new NodeStruct();
        struct.right.read(iprot);
        struct.setRightIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

