package com.melon.nbt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NBTTest {
    @Test
    public void NBTtest() {
        NBTCompound test =
        NBTObjectBuilder.buildCompound()
            .Boolean("a",false)
            .Byte("b",(byte)1)
            .Int("c",2)
            .ByteArray(new String("d"),new byte[] {3,4,5})
            .IntArray(new String("e"),new int[] {6,7,8})
            .LongArray(new String("f"),new long[] {9,10,11})
            .String("g","test")
            .Float("h",0.1f)
            .Double("i",0.2d)
            .directList(
                NBTObjectBuilder.buildList("j")
                .String("test1")
                .String("test2")
                .String("t3")
                .endList()
            )
            .directCompound(
                NBTObjectBuilder.buildCompound("k")
                .String("nested","test")
                .endCompound()
            )
            .String("l","\"\"\"test nested string\"\"\"")
            .endCompound();
            System.out.println("------direct------");
        System.out.println(test.toString());
        System.out.println();
        System.out.println();
        System.out.println();
            System.out.println("------transform------");
        System.out.println(NBTElement.asNBT(test.toString()).toString());
        
        assertTrue(NBTElement.asNBT(test.toString()).toString().equals(test.toString()));
    }
}
