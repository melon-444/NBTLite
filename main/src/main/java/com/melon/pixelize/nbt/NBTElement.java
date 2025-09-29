package com.melon.pixelize.nbt;

/**
 * This is a recoding of Minecraft Java Edition Named Binary Tag (NBT) element
 * 
 * @param <T> The type of the payload
 * @author melon_444
 */
public abstract class NBTElement<T> {
    protected byte Type_ID;

    protected short keyNameLength;

    protected String keyName;

    protected T payLoad;

    public abstract byte[] toBytes();

    static String elementKeyName(byte[] input, int offset) {
        if (input[offset] == Type.END)
            return null;// get End
        int keyNameLength = (0xFF & input[offset + 1] << 8) | (0xFF & input[offset + 2]);
        byte[] keyNameBytes = new byte[keyNameLength];
        System.arraycopy(input, offset + 3, keyNameBytes, 0, keyNameLength);
        return new String(keyNameBytes);
    }

    static int elementPayLoadOffset(byte[] input, int offset) {
        if (input[offset] == Type.END)
            return -1;// get End
        int keyNameLength = (0xFF & input[offset + 1] << 8) | (0xFF & input[offset + 2]);
        return offset + 3 + keyNameLength;
    }

    /**
     * Convert a byte array to an NBT element.
     * 
     * @param bytes The byte array to convert.
     * @return The NBT element.
     */

    //TODO:complete the function
    public static NBTElement<?> asNBT(byte[] bytes) {
        int keyNameLength = elementPayLoadOffset(bytes, 0) - 3;
        String keyName = elementKeyName(bytes, 0);
        switch (bytes[0]) {
            case Type.BYTE:
                return new NBTByte(keyName, bytes[3 + keyNameLength]);
            case Type.SHORT:
                return new NBTShort(keyName,
                        (short) ((0xFF & bytes[3 + keyNameLength] << 8) | (0xFF & bytes[4 + keyNameLength])));
            case Type.INT:
                return new NBTInt(keyName,
                        (int) ((0xFF & bytes[3 + keyNameLength] << 24) | (0xFF & bytes[4 + keyNameLength] << 16)
                                | (0xFF & bytes[5 + keyNameLength] << 8)
                                | (0xFF & bytes[6 + keyNameLength])));
            case Type.LONG:
                return new NBTLong(keyName,
                        (long) ((0xFFL & bytes[3 + keyNameLength] << 56) | (0xFFL & bytes[4 + keyNameLength] << 48)
                                | (0xFFL & bytes[5 + keyNameLength] << 40) | (0xFFL & bytes[6 + keyNameLength] << 32)
                                | (0xFFL & bytes[7 + keyNameLength] << 24)
                                | (0xFFL & bytes[8 + keyNameLength] << 16) | (0xFFL & bytes[9 + keyNameLength] << 8)
                                | (0xFFL & bytes[10 + keyNameLength])));
            case Type.FLOAT:
                return new NBTFloat(keyName,
                        Float.intBitsToFloat(
                                (int) ((0xFF & bytes[3 + keyNameLength] << 24) | (0xFF & bytes[4 + keyNameLength] << 16)
                                        | (0xFF & bytes[5 + keyNameLength] << 8) | (0xFF & bytes[6 + keyNameLength]))));
            case Type.DOUBLE:
                return new NBTDouble(keyName,
                        Double.longBitsToDouble((long) ((0xFFL & bytes[3 + keyNameLength] << 56)
                                | (0xFFL & bytes[4 + keyNameLength] << 48)
                                | (0xFFL & bytes[5 + keyNameLength] << 40) | (0xFFL & bytes[6 + keyNameLength] << 32)
                                | (0xFFL & bytes[7 + keyNameLength] << 24)
                                | (0xFFL & bytes[8 + keyNameLength] << 16) | (0xFFL & bytes[9 + keyNameLength] << 8)
                                | (0xFFL & bytes[10 + keyNameLength]))));
            case Type.BYTE_ARRAY: {
                int arrLength = (int) ((0xFF & bytes[3 + keyNameLength] << 24) | (0xFF & bytes[4 + keyNameLength] << 16)
                        | (0xFF & bytes[5 + keyNameLength] << 8)
                        | (0xFF & bytes[6 + keyNameLength]));
                byte[] arr = new byte[arrLength];
                System.arraycopy(bytes, 7 + keyNameLength, arr, 0, arrLength);
                return new NBTByteArray(keyName, arr);
            }
            case Type.INT_ARRAY: {
                int arrLength = (int) ((0xFF & bytes[3 + keyNameLength] << 24) | (0xFF & bytes[4 + keyNameLength] << 16)
                        | (0xFF & bytes[5 + keyNameLength] << 8)
                        | (0xFF & bytes[6 + keyNameLength]));
                int[] arr = new int[arrLength];
                for (int i = 0; i < arrLength; i++) {
                    arr[i] = (int) ((0xFF & bytes[7 + keyNameLength + (i * 4)] << 24)
                            | (0xFF & bytes[8 + keyNameLength + (i * 4)] << 16)
                            | (0xFF & bytes[9 + keyNameLength + (i * 4)] << 8)
                            | (0xFF & bytes[10 + keyNameLength + (i * 4)]));
                }
                return new NBTIntArray(keyName, arr);
            }
            case Type.LONG_ARRAY: {
                int arrLength = (int) ((0xFF & bytes[3 + keyNameLength] << 24) | (0xFF & bytes[4 + keyNameLength] << 16)
                        | (0xFF & bytes[5 + keyNameLength] << 8)
                        | (0xFF & bytes[6 + keyNameLength]));
                long[] arr = new long[arrLength];
                for (int i = 0; i < arrLength; i++) {
                    arr[i] = (long) ((0xFFL & bytes[7 + keyNameLength + (i * 8)] << 56)
                            | (0xFFL & bytes[8 + keyNameLength + (i * 8)] << 48)
                            | (0xFFL & bytes[9 + keyNameLength + (i * 8)] << 40)
                            | (0xFFL & bytes[10 + keyNameLength + (i * 8)] << 32)
                            | (0xFFL & bytes[11 + keyNameLength + (i * 8)] << 24)
                            | (0xFFL & bytes[12 + keyNameLength + (i * 8)] << 16)
                            | (0xFFL & bytes[13 + keyNameLength + (i * 8)] << 8)
                            | (0xFFL & bytes[14 + keyNameLength + (i * 8)]));
                }
                return new NBTLongArray(keyName, arr);
            }
            case Type.STRING: {
                int strLength = (int) ((0xFF & bytes[3 + keyNameLength] << 8) | (0xFF & bytes[4 + keyNameLength]));
                byte[] strBytes = new byte[strLength];
                System.arraycopy(bytes, 5 + keyNameLength, strBytes, 0, strLength);
                return new NBTString(keyName, new String(strBytes));
            }
            case Type.LIST: {
                byte elementType = bytes[3 + keyNameLength];
                int arrLength = (int) ((0xFF & bytes[4 + keyNameLength] << 24) | (0xFF & bytes[5 + keyNameLength] << 16)
                        | (0xFF & bytes[6 + keyNameLength] << 8)
                        | (0xFF & bytes[7 + keyNameLength]));
                NBTElement<?>[] elements = new NBTElement<?>[arrLength];
                if (elementType == Type.COMPOUND) {

                } else if (elementType == Type.LIST) {

                } else {
                    int offset = 8 + keyNameLength;
                    for (int i = 0; i < arrLength; i++) {
                        int elementStart = offset;
                        int elementEnd = offset;
                        // find the end of the element
                        switch (elementType) {
                            case Type.BYTE:
                                elementEnd += 1;
                                break;
                            case Type.SHORT:
                                elementEnd += 2;
                                break;
                            case Type.INT:
                                elementEnd += 4;
                                break;
                            case Type.LONG:
                                elementEnd += 8;
                                break;
                            case Type.FLOAT:
                                elementEnd += 4;
                                break;
                            case Type.DOUBLE:
                                elementEnd += 8;
                                break;
                            case Type.BYTE_ARRAY:
                                int byteArrayLength = (int) ((0xFF & bytes[offset] << 24)
                                        | (0xFF & bytes[offset + 1] << 16) | (0xFF & bytes[offset + 2] << 8)
                                        | (0xFF & bytes[offset + 3]));
                                elementEnd += 4 + byteArrayLength;
                                break;
                            case Type.INT_ARRAY:
                                int intArrayLength = (int) ((0xFF & bytes[offset] << 24)
                                        | (0xFF & bytes[offset + 1] << 16) | (0xFF & bytes[offset + 2] << 8)
                                        | (0xFF & bytes[offset + 3]));
                                elementEnd += 4 + (intArrayLength * 4);
                                break;
                            case Type.LONG_ARRAY:
                                int longArrayLength = (int) ((0xFF & bytes[offset] << 24)
                                        | (0xFF & bytes[offset + 1] << 16) | (0xFF & bytes[offset + 2] << 8)
                                        | (0xFF & bytes[offset + 3]));
                                elementEnd += 4 + (longArrayLength * 8);
                                break;
                            case Type.STRING:
                                int strLength = (int) ((0xFF & bytes[offset] << 8) | (0xFF & bytes[offset + 1]));
                                elementEnd += 2 + strLength;
                                break;
                            default:
                                throw new IllegalArgumentException("Unsupported element type in list: " + elementType);
                        }
                        byte[] elementBytes = new byte[elementEnd - elementStart];
                        System.arraycopy(bytes, elementStart, elementBytes, 0, elementEnd - elementStart);
                        elements[i] = asNBT(elementBytes);
                    }
                }
                return new NBTList(keyName,elements);
            }
            case Type.COMPOUND: {

            }

            default:
                return null;
        }

    }

    public byte getType() {
        return toBytes()[0];
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
        keyNameLength = (short) keyName.getBytes().length;
    }

    public T getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(T payLoad) {
        this.payLoad = payLoad;
    }

    public static class Type {
        public static final byte END = 0;
        public static final byte BYTE = 1;
        public static final byte SHORT = 2;
        public static final byte INT = 3;
        public static final byte LONG = 4;
        public static final byte FLOAT = 5;
        public static final byte DOUBLE = 6;
        public static final byte BYTE_ARRAY = 7;
        public static final byte STRING = 8;
        public static final byte LIST = 9;
        public static final byte COMPOUND = 10;
        public static final byte INT_ARRAY = 11;
        public static final byte LONG_ARRAY = 12;
    }
}
