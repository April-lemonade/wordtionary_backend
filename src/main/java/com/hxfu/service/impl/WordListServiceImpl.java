package com.hxfu.service.impl;

import com.hxfu.entity.Word;
import com.hxfu.entity.WordList;
import com.hxfu.mapper.UserMapper;
import com.hxfu.mapper.WordListMapper;
import com.hxfu.mapper.WordMapper;
import com.hxfu.service.WordListService;
import javafx.scene.control.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WordListServiceImpl implements WordListService {
    @Autowired
    private WordListMapper wordListMapper;
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private UserMapper userMapper;

    public List<WordList> getadminAll() {
        return wordListMapper.getadminAll();
    }

    @Override
    public int change(String bookId, String openid) {
        return wordListMapper.change(bookId, openid);
    }

    @Override
    public String getName(String bookId) {
        return wordListMapper.getName(bookId);
    }

    @Override
    public List<WordList> getuserAll(String openid) {
        return wordListMapper.getuserAll(openid);
    }

    @Override
    public int addList(String name, String word, String openid) {
        //新词书插入wordlist表
        wordListMapper.addList(name, openid);
        //新单词插入word表
        int listid = wordListMapper.findId(name);
        String[] words = word.split("\n");
        for (String i : words) {
            System.out.println("!!!!!!!!!!!!!!!!" + i);
            Word temp = new Word();
            temp.setWord(i);
            temp.setListid(listid);
            wordMapper.addWord(temp);
        }
        return 0;
    }

    @Override
    public int webaddList(String name, String word, String openid) {
        //新词书插入wordlist表
        wordListMapper.addList(name, openid);
        //新单词插入word表
        int listid = wordListMapper.findId(name);
        String[] words = word.split(",");
        for (String i : words) {
            System.out.println("!!!!!!!!!!!!!!!!" + i);
            Word temp = new Word();
            temp.setWord(i);
            temp.setListid(listid);
            wordMapper.addWord(temp);
        }
        return 0;
    }

    @Override
    public int fileaddlist(MultipartFile file, String name, String openid) {
        System.out.println(name);
        wordListMapper.addList(name, openid);
        int listid = wordListMapper.findId(name);
        //定义一个空数组
        List<String> list = new ArrayList<>();
        //定义数组中的对象，全局可使用
        String word = null;
        try {
            //1. 创建一个 workbook 对象
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //2. 获取 workbook 中表单的数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //3. 获取表单，先得到每一行，在得到每一列
                XSSFSheet sheet = workbook.getSheetAt(i);
                //4. 获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    //5. 跳过标题行
                    if (j == 0) {
                        continue;//跳过标题行
                    }
                    //6. 获取行
                    XSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//防止数据中间有空行
                    }
                    //7. 获取列数
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    System.out.println(row.getCell(0));
                    Word temp = new Word();
                    temp.setWord(row.getCell(0).toString());
                    temp.setListid(listid);
                    wordMapper.addWord(temp);
                    //循环获得每一行中每一列数据
                    /*
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        XSSFCell cell = row.getCell(k);

                        //pridict里边的excell是数字型，要把先转换成String类型
                        */
                    /*
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        */
                    /*

                        String cellValue = cell.getStringCellValue();
                    }
                       */
                }
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteList(String bookId) {
        wordListMapper.deleteList(bookId);
        wordMapper.deleteWord(bookId);
        return 0;
    }

    @Override
    public int changeListName(String name, String bookId) {
        return wordListMapper.changeListName(name, bookId);
    }

    @Override
    public Map<String, String> getProgress(String bookId, String openid) {
        Map<String, String> map = new HashMap<>();
        int all = wordMapper.getCounts(bookId);
        int dailyCount = userMapper.getDailyCount(openid);
        int current = userMapper.getWordId(openid);
        int leftCount = all - current;
        int leftDays = (int) Math.ceil((double) leftCount / dailyCount);
        float progress = (float) current / all;
        DecimalFormat df = new DecimalFormat("0.0000");//格式化小数
        String s = df.format(progress);//返回的是String类型
        map.put("progress", s);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        // 把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE, leftDays);
        // 这个时间就是日期往后推一天的结果
        Date finishDate = calendar.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(finishDate);
        map.put("finishDate", time);
        map.put("leftCount", leftCount + "");
        return map;
    }
}
