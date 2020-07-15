package com.songjian.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: webin
 * @date: 2020/7/15 22:42
 * @description:
 * @version: 0.0.1
 */
public class Test01 {

    @Test
    public void testCreateIndex() throws IOException {

        // 指定索引库存放的路径
        String pathDirectory = "D:\\temp\\index";
        // 打开目录
        FSDirectory directory = new FSDirectory.open(Paths.get(pathDirectory));
        // 创建分词器
        StandardAnalyzer analyzer= new StandardAnalyzer();
        // 创建索引配置信息对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建索引写对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 执行数据源的位置
        File dir = new File("D:\\temp\\source");
        for (File file : dir.listFiles()) {
            // 获取数据源数据
            // 文件名
            String fileName = file.getName();
            // 获取文件的内容
            String fileContent = FileUtils.readFileToString(file);
            // 文件的路径
            String filePath = file.getPath();
            // 获取文件的大小
            long fileSize = FileUtils.sizeOf(file);
            //创建文件名域
            // 第一个参数：域的名称
            // 第二个参数：域的内容
            //第三个参数：是否存储
            TextField textField = new TextField("filename", fileName, Field.Store.YES);
            TextField contentField = new TextField("content", fileContent, Field.Store.YES);
            StoredField storedField = new StoredField("path", filePath);
            StoredField sizeField = new StoredField("size", fileSize);
            Document document = new Document();
            document.add(textField);
            document.add(storedField);
            document.add(contentField);
            document.add(sizeField);
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }
}
