package com.melon.pixelize.nbt;
/**
 * Use Big Endian byte order and modified UTF-8 encoding (same as DataOutputStream.writeUTF)
 * for strings.
 */
public class NBTString extends NBTElement<String> {

    public NBTString(String keyName, String value) {
        if(keyName == null)
            keyName = "";
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.length();
        this.payLoad = value;
    }

    public NBTString(String value) {
        this(null, value);
    }

    

    @Override
    public byte[] toBytes() {
        byte[] strBytes = payLoad.getBytes();
        int strLength = strBytes.length;
        byte[] result = new byte[3 + keyNameLength + 2 + strLength];
        result[0] = NBTElement.Type.STRING;
        result[1] = (byte) ((0xFF) & keyNameLength >> 8);
        result[2] = (byte) ((0xFF) & keyNameLength);
        System.arraycopy(keyName.getBytes(), 0, result, 3, keyNameLength);
        result[3 + keyNameLength] = (byte) ((0xFF) & strLength >> 8);
        result[4 + keyNameLength] = (byte) ((0xFF) & strLength);
        System.arraycopy(strBytes, 0, result, 5 + keyNameLength, strLength);
        return result;
    }

    @Override
    public String toString(){
        return "\""+payLoad+"\"";
    }
    
}
