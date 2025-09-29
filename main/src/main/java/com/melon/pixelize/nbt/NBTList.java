package com.melon.pixelize.nbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NBTList extends NBTElement<List<NBTElement<?>>> {

    private int type = 0;

    public NBTList(String keyName, List<NBTElement<?>> value) {
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.getBytes().length;
        this.payLoad = value;
        type = value != null && value.size() > 0 ? value.get(0).getType() : 0;
    }

    public NBTList(String keyName, NBTElement<?>[] value) {
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.getBytes().length;
        this.payLoad = Arrays.asList(value);
        type = value != null && value.length > 0 ? value[0].getType() : 0;
    }

    public NBTList(String keyName) {
        this(keyName,new ArrayList<>());
    }

    @Override
    public void setPayLoad(List<NBTElement<?>> payLoad) {
        this.payLoad = payLoad;
        type = payLoad != null && payLoad.size() > 0 ? payLoad.get(0).getType() : 0;
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
        if (payLoad != null && payLoad.size() > 0) {
            result[index++] = (byte) ((0xFF) & type);
        } else {
            result[index++] = NBTEnd.getEnd(); // Empty list
            return result;
        }
        result[index++] = (byte) ((0xFF) & (payLoad.size() >> 24));
        result[index++] = (byte) ((0xFF) & (payLoad.size() >> 16));
        result[index++] = (byte) ((0xFF) & (payLoad.size() >> 8));
        result[index++] = (byte) ((0xFF) & payLoad.size());
        for (NBTElement<?> element : payLoad) {
            byte[] elementBytes = element.toBytes();
            System.arraycopy(elementBytes, 0, result, index, elementBytes.length);
            index += elementBytes.length;
        }
        return result;
    }

}
