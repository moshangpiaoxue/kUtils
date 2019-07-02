package com.libs.utils.fileUtil;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ author：mo
 * @ data：2019/2/13:13:55
 * @ 功能：IO 流对象相关
 */
public class IOUtil {
    /**
     * 关闭流对象
     * <p>API 将会对流对象进行判空, 并在关闭流对象时抓捕异常, 防止崩溃</p>
     *
     * @param stream 流对象
     */
    public static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭指针
     *
     * @param cursor 指针对象
     */
    public static void close(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 关闭多个流对象
     * <p>API 将会对流对象进行判空, 并在关闭流对象时抓捕异常, 防止崩溃</p>
     *
     * @param streams 多个流对象
     */
    public static void close(Closeable... streams) {
        for (Closeable stream : streams) {
            close(stream);
        }
    }

    /**
     * 关闭流对象, 不打印关闭时抛出的异常
     * <p>API 将会对流对象进行判空, 并在关闭流对象时抓捕异常, 防止崩溃</p>
     *
     * @param stream 流对象
     */
    public static void closeQuietly(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 关闭多个流对象, 不打印关闭时抛出的异常
     * <p>API 将会对流对象进行判空, 并在关闭流对象时抓捕异常, 防止崩溃</p>
     *
     * @param streams 多个流对象
     */
    public static void closeQuietly(Closeable... streams) {
        for (Closeable stream : streams) {
            close(stream);
        }
    }
}
