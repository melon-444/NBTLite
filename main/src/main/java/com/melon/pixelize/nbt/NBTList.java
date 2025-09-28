package com.melon.pixelize.nbt;

public class NBTList extends NBTElement<NBTElement<?>[]> {

    private int type = 0;

    public NBTList(String keyName, NBTElement<?>[] value) {
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.getBytes().length;
        this.payLoad = value;
        type = value != null && value.length > 0 ? value[0].getType() : 0;
    }

    @Override
    public void setPayLoad(NBTElement<?>[] payLoad) {
        this.payLoad = payLoad;
        type = payLoad != null && payLoad.length > 0 ? payLoad[0].getType() : 0;
    }

    @Override
    public byte[] toBytes() {
        int totalLength = 3 + keyNameLength + 5; // Type + Key Length + Key Name + Element Type + List Length
        if (payLoad != null)
            for (NBTElement<?> element : payLoad) {
                totalLength += element.toBytes().length;
            }
        byte[] result = new byte[totalLength];
        result[0] = NBTElement.Type.LIST;
        result[1] = (byte) ((0xFF) & keyNameLength >> 8);
        result[2] = (byte) ((0xFF) & keyNameLength);
        System.arraycopy(keyName.getBytes(), 0, result, 3, keyNameLength);
        int index = 3 + keyNameLength;
        if (payLoad != null && payLoad.length > 0) {
            result[index++] = (byte) ((0xFF) & type);
        } else {
            result[index++] = NBTEnd.getEnd(); // Empty list
            return result;
        }
        result[index++] = (byte) ((0xFF) & (payLoad.length >> 24));
        result[index++] = (byte) ((0xFF) & (payLoad.length >> 16));
        result[index++] = (byte) ((0xFF) & (payLoad.length >> 8));
        result[index++] = (byte) ((0xFF) & payLoad.length);
        for (NBTElement<?> element : payLoad) {
            byte[] elementBytes = element.toBytes();
            System.arraycopy(elementBytes, 0, result, index, elementBytes.length);
            index += elementBytes.length;
        }
        return result;
    }

}
