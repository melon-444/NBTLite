# NBTLite

NBTLite is a lightweight Java library for reading, writing, and manipulating Minecraft's Named Binary Tag (NBT) data format. It provides a set of classes to represent all standard NBT types, including compound, list, byte, short, int, long, float, double, string, byte array, int array, and long array. The library supports both binary NBT and SNBT (stringified NBT) formats.

## Features

- **NBT Element Classes**: Each NBT type is represented by a dedicated class, such as `com.melon.nbt.NBTCompound`, `com.melon.nbt.NBTList`, `com.melon.nbt.NBTInt`, etc.
- **Builder Pattern**: Easily construct complex NBT structures using `com.melon.nbt.NBTObjectBuilder`.
- **Binary and SNBT Decoding**: Parse binary NBT data with `com.melon.nbt.NBTDecoder` and SNBT strings with `com.melon.nbt.SNBTDecoder`.
- **GZip Support**: Read and write GZip-compressed NBT data using `com.melon.nbt.io.GZipNBTReader` and `com.melon.nbt.io.GZipNBTWriter`.
- **Unit Tests**: Example unit tests are provided in `com.melon.nbt.NBTTest`.

## Usage

NBTLite can be used to serialize and deserialize NBT data, making it suitable for Minecraft modding, data analysis, or any application that needs to interact with NBT files.

Example: Build a compound NBT structure

```java
NBTCompound compound = NBTObjectBuilder.buildCompound("root")
    .Int("level", 42)
    .String("name", "Steve")
    .List("inventory",
        new NBTString("item1"),
        new NBTString("item2")
    )
    .endCompound();
```

## Project Structure

- `src/main/java/com/melon/nbt/` — Core NBT classes and utilities
- `src/main/java/com/melon/nbt/io/` — I/O classes for reading/writing NBT data
- `src/test/java/com/melon/nbt/` — Unit tests

## License

This project is licensed under the MIT License. See [`LICENSE`](LICENSE ) for