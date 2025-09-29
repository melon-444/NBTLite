package com.melon.pixelize.nbt;

public class NBTShort extends NBTElement<Short> {

    public NBTShort(String keyName, short value) {
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.getBytes().length;
        this.payLoad = value;
    }

    @Override
    public byte[] toBytes() {
        byte[] result = new byte[3 + 2 + keyNameLength];
        result[0] = NBTElement.Type.SHORT;
        result[1] = (byte)((0xFF)&keyNameLength>>8);
        result[2] = (byte)((0xFF)&keyNameLength);
        System.arraycopy(keyName.getBytes(), 0, result, 3, keyNameLength);
        result[3 + keyNameLength] = (byte)(0xFF&payLoad>>8);
        result[4 + keyNameLength] = (byte)(0xFF&payLoad);
        return result;
    }

}
