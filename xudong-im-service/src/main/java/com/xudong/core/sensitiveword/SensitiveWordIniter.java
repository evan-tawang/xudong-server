package com.xudong.core.sensitiveword;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 */
public class SensitiveWordIniter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordIniter.class);
    private String ENCODING = "UTF-8";

    private HashMap sensitiveWordMap;

    public SensitiveWordIniter() {
        super();
    }

    /**
     * @author chenming
     * @date 2014年4月20日 下午2:28:32
     * @version 1.0
     */
    @Deprecated
    public void initKeyWord() {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return sensitiveWordMap;
    }

    public void initKeyWord(Set<String> keyWordSet) {
        //将敏感词库加入到HashMap中
        addSensitiveWordToHashMap(keyWordSet);

        //return sensitiveWordMap;
    }

    /**
     * @param keyWordString 多个以','分割
     */
    public void initKeyWord(String keyWordString) {
        if (StringUtils.isBlank(keyWordString)) {
            LOGGER.error(">>>> Sensitive Word not inited");
        } else {
            String[] words = keyWordString.split(",");
            Set<String> wordsSet = new HashSet<>(words.length);

            for (String word : words) {
                wordsSet.add(word);
            }

            addSensitiveWordToHashMap(wordsSet);

            LOGGER.error(">>>> Sensitive Word inited，total 【{}】", wordsSet.size());
        }
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     * isEnd = 0
     * 国 = {<br>
     * isEnd = 1
     * 人 = {isEnd = 0
     * 民 = {isEnd = 1}
     * }
     * 男  = {
     * isEnd = 0
     * 人 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     * 五 = {
     * isEnd = 0
     * 星 = {
     * isEnd = 0
     * 红 = {
     * isEnd = 0
     * 旗 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     *
     * @param keyWordSet 敏感词库
     * @author chenming
     * @date 2014年4月20日 下午3:04:20
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @return
     * @throws Exception
     * @author chenming
     * @date 2014年4月20日 下午2:31:18
     * @version 1.0
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile(File file) throws Exception {
        Set<String> set = null;

        InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
        try {
            if (file.isFile() && file.exists()) {      //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {         //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            read.close();     //关闭文件流
        }
        return set;
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @return
     * @throws Exception
     * @author chenming
     * @date 2014年4月20日 下午2:31:18
     * @version 1.0
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile() throws Exception {
        String[] files = new String[]{
                "C:\\01.txt",
                "C:\\暴恐词库.txt",
                "C:\\反动词库.txt",
                "C:\\民生词库.txt",
                "C:\\其他词库.txt",
                "C:\\色情词库.txt",
                "C:\\色情词库.txt",
        };
        Set<String> words = new HashSet<>();
        for (String path : Arrays.asList(files)) {
            Set<String> word = readSensitiveWordFile(new File(path));
            if (CollectionUtils.isEmpty(word)) {
                continue;
            }
            words.addAll(word);
        }
        return words;
    }

    /**
     *
     */
    public HashMap getSensitiveWordMap() {
        return sensitiveWordMap;
    }
}