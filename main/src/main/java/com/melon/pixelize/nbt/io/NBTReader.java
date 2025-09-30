package com.melon.pixelize.nbt.io;

import java.io.IOException;
import java.io.InputStream;

import com.melon.pixelize.nbt.NBTElement;

public abstract class NBTReader extends InputStream {
    public abstract NBTElement<?> readNBTElement() throws IOException;
}
