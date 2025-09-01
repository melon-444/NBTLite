# Pixelize

一个用于将图片像素化处理的 Java 项目。

## 项目结构

- `main/src/main/java/com/melon/pixelize/App.java`：主程序入口，包含图片处理核心逻辑。
- `ignore/input.png`：输入图片示例。
- `ignore/preprocess.png`：输出图片示例。

## 功能简介

- 支持读取本地图片并进行像素化处理。
- 多线程加速像素计算。
- 支持自定义像素块大小，自动调整输出图片尺寸。
- 颜色映射采用自定义枚举 `App.Color` 实现，自动选择最接近的预设颜色。

## 快速开始

1. **准备输入图片**  
   将待处理图片放入 `ignore/input.png`。

2. **编译运行**  
   在项目根目录下执行：

   ```sh
   javac -d out main/src/main/java/com/melon/pixelize/App.java
   java -cp out com.melon.pixelize.App
   ```

   处理结果将输出到 `ignore/preprocess.png`。

## 主要代码说明

- 主处理流程在 `App.main` 方法中实现。
- 像素块颜色计算及映射在 `App.Color.getColorByRGBVal` 完成。
- 颜色距离计算见 `App.Color.colorDistance`。

## 依赖

- JDK 8+

## License

MIT

---
