package com.melon.nbt;

public interface rootElement {
    public boolean removeElement(String keyname);
    public boolean removeElement(NBTElement<?> element);
    public void addElement(NBTElement<?> element);
}
