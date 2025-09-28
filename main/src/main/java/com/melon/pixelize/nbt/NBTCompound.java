package com.melon.pixelize.nbt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTCompound extends NBTElement<List<NBTElement<?>>> {

    private boolean trasmitByNet = false;

    public NBTCompound(String keyName, List<NBTElement<?>> value) {
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.getBytes().length;
        this.payLoad = value;
    }

    public NBTCompound(String keyName) {
        this(keyName, new ArrayList<>());
    }

    public void isTransmitByNet(boolean value) {
        this.trasmitByNet = value;
    }

    public void addElement(NBTElement<?> element) {
        this.payLoad.add(element);
    }

    public boolean removeElement(NBTElement<?> element) {
        return this.payLoad.remove(element);
    }

    public boolean removeElement(String keyname) {
        Iterator<NBTElement<?>> it = payLoad.iterator();
        while (it.hasNext()) {
            NBTElement<?> element = it.next();
            if (element.getKeyName().equals(keyname)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public byte[] toBytes() {
        int totalLength;
        if (trasmitByNet)
            totalLength = 1; // Type (1), no key name.
        else
            totalLength = 1 + 2 + keyNameLength; // Type (1) + KeyNameLength (2) + KeyName

        for (NBTElement<?> element : payLoad) {
            totalLength += element.toBytes().length;
        }
        totalLength += 1; // End tag

        byte[] result = new byte[totalLength];
        int index = 0;

        result[index++] = NBTElement.Type.COMPOUND;
        if (!trasmitByNet) {
            result[index++] = (byte) ((0xFF) & keyNameLength >> 8);
            result[index++] = (byte) ((0xFF) & keyNameLength);
            System.arraycopy(keyName.getBytes(), 0, result, index, keyNameLength);
            index += keyNameLength;
        }

        for (NBTElement<?> element : payLoad) {
            byte[] elementBytes = element.toBytes();
            System.arraycopy(elementBytes, 0, result, index, elementBytes.length);
            index += elementBytes.length;
        }

        result[index] = NBTEnd.getEnd(); // End tag

        return result;
    }
}
