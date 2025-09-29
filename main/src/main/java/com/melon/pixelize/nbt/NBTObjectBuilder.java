package com.melon.pixelize.nbt;

import java.util.Arrays;

public class NBTObjectBuilder {

    private rootElement lastBuilt = null;

    private NBTObjectBuilder() {
    }

    /**
     * Create a new Compound NBT element with name
     *
     * @param rootName the name of the root compound.
     * @return this builder instance
     */
    public static NBTObjectBuilder buildCompound(String rootName) {
        NBTObjectBuilder b = new NBTObjectBuilder();
        b.lastBuilt = new NBTCompound(rootName);
        return b;
    }

    /**
     * Create a new Compound NBT element with no name
     *
     * @return this builder instance
     */
    public static NBTObjectBuilder buildCompound() {
        NBTObjectBuilder b = buildCompound("");
        return b;
    }

    public static NBTObjectBuilder buildList(String rootName) {
        NBTObjectBuilder b = new NBTObjectBuilder();
        b.lastBuilt = new NBTList(rootName);
        return b;
    }

    public static NBTObjectBuilder buildList() {
        NBTObjectBuilder b = buildList("");
        return b;
    }

    public NBTObjectBuilder Byte(String keyName, byte value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTByte(keyName, value));
        return this;
    }

    public NBTObjectBuilder Boolean(String keyName, boolean value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTBoolean(keyName, value));
        return this;
    }

    public NBTObjectBuilder Short(String keyName, short value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTShort(keyName, value));
        return this;
    }

    public NBTObjectBuilder Int(String keyName, int value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTInt(keyName, value));
        return this;
    }

    public NBTObjectBuilder Long(String keyName, long value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTLong(keyName, value));
        return this;
    }

    public NBTObjectBuilder Float(String keyName, float value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTFloat(keyName, value));
        return this;
    }

    public NBTObjectBuilder Double(String keyName, double value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTDouble(keyName, value));
        return this;
    }

    public NBTObjectBuilder String(String keyName, String value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTString(keyName, value));
        return this;
    }

    public NBTObjectBuilder ByteArray(String keyName, byte[] value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTByteArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder IntArray(String keyName, int[] value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTIntArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder LongArray(String keyName, long[] value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(new NBTLongArray(keyName, value));
        return this;
    }

    public NBTObjectBuilder Compound(String keyName, NBTElement<?>... values) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        NBTCompound value = new NBTCompound(keyName, Arrays.asList(values));
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder directCompound(NBTCompound value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        value.isTransmitByNet(false);
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder List(String keyName, NBTElement<?>... values) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        NBTList value = new NBTList(keyName, values);
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder directList(NBTList value) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.addElement(value);
        return this;
    }

    public NBTObjectBuilder delete(String keyName) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.removeElement(keyName);
        return this;
    }

    public NBTObjectBuilder delete(NBTElement<?> element) {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        lastBuilt.removeElement(element);
        return this;
    }

    public NBTElement<?> end() {
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        if (lastBuilt instanceof NBTCompound result) {
            lastBuilt = null;
            return result;
        } else if (lastBuilt instanceof NBTList result) {
            lastBuilt = null;
            return result;
        }
        throw new IllegalStateException("The result element isn't a root Element.");
    }

    public NBTCompound endCompound(){
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        if (lastBuilt instanceof NBTCompound result) {
            lastBuilt = null;
            return result;
        }
        throw new IllegalStateException("The result element isn't a Compound Element.");
    }

    public NBTList endList(){
        if (lastBuilt == null)
            throw new IllegalStateException("Call build() before creating a new compound element");
        if (lastBuilt instanceof NBTList result) {
            lastBuilt = null;
            return result;
        }
        throw new IllegalStateException("The result element isn't a List Element.");
    }
}
