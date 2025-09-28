package com.melon.pixelize.nbt.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import com.melon.pixelize.nbt.NBTElement;

public class NBTWriter extends OutputStream {

    private final GZIPOutputStream gzipOut;

    public NBTWriter(GZIPOutputStream gzipOut) {
        this.gzipOut = gzipOut;
    }

    @Override
    public void flush() {
        try {
            gzipOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        try {
            gzipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(int b) throws IOException {
        gzipOut.write(b);
    }

    public void write(NBTElement<?> element) {
        try {
            gzipOut.write(element.toBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
