package com.melon.pixelize.nbt.io;

import java.io.IOException;
import java.io.OutputStream;

import com.melon.pixelize.nbt.NBTElement;

public abstract class NBTWriter extends OutputStream {

    public abstract void write(NBTElement<?> element) throws IOException;

}
