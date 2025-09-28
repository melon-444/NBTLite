package com.melon.pixelize.nbt;

import java.util.Arrays;

public class NBTObjectBuilder {

    private NBTCompound lastBuilt = null;

    private NBTObjectBuilder() {
    }



    public static NBTObjectBuilder build(String rootName){
        NBTObjectBuilder b = new NBTObjectBuilder();
        b.lastBuilt = new NBTCompound(rootName);
        return b;
    }

    /**
     * Create a new Compound NBT element with no name, and it suits for net transmission.
     * 
     * @return this builder instance
     */
    public static NBTObjectBuilder build(){
        NBTObjectBuilder b = build("");
            b.lastBuilt.isTransmitByNet(true);
        return b;
    }

    public NBTObjectBuilder Byte(String keyName, byte value) {
        if(lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTByte(keyName, value));
        return this;
    }

    public NBTObjectBuilder Boolean(String keyName, boolean value) {
        if(lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTBoolean(keyName, value));
        return this;
    }

    public NBTObjectBuilder Short(String keyName, short value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTShort(keyName, value));
        return this;
    }

    public NBTObjectBuilder Int(String keyName, int value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTInt(keyName, value));
        return this;
    }

    public NBTObjectBuilder Long(String keyName, long value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTLong(keyName, value));
        return this;
    }

    public NBTObjectBuilder Float(String keyName, float value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTFloat(keyName, value));
        return this;
    }

    public NBTObjectBuilder Double(String keyName, double value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTDouble(keyName, value));
        return this;
    }

    public NBTObjectBuilder String(String keyName, String value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTString(keyName, value));
        return this;
    }

    public NBTObjectBuilder ByteArray(String keyName, byte[] value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTByteArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder IntArray(String keyName, int[] value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTIntArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder LongArray(String keyName, long[] value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTLongArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder Compound(String keyName, NBTElement<?>... values) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        NBTCompound value = new NBTCompound(keyName, Arrays.asList(values));
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder directCompound(NBTCompound value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        value.isTransmitByNet(false);
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder List(String keyName, NBTElement<?>... values) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        NBTList value = new NBTList(keyName, values);
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder directList(NBTList value) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder delete(String keyName) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.removeElement(keyName);
        return this;
    }

    public NBTObjectBuilder delete(NBTElement<?> element) {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.removeElement(element);
        return this;
    }

    public NBTCompound end() {
        if (lastBuilt == null) throw new IllegalStateException("Call build() before creating a new compound element");
        NBTCompound result = lastBuilt;
        lastBuilt = null;
        return result;
    }
}
