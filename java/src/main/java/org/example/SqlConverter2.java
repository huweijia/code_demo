package org.example;

import com.google.common.collect.Lists;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SqlConverter2 {
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("saas迁移数据客户车辆分类uat.sql");
        String sql = "insert  into `merchant_vehicle_classification`(biz_id,shop_id,tenant_id,code,name,inclusive_range_price,exclusive_range_price,remark,status,create_by,create_time,update_by,update_time,delete_status) values";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
        String saasBatchSql = bufferedReader.readLine();
        saasBatchSql = saasBatchSql.replaceAll(sql, "");
        int index = saasBatchSql.indexOf("values ");
        saasBatchSql = saasBatchSql.substring(index + 7);
        String[] splitSqlArray = saasBatchSql.split("\\)");
        List<String> sqls = new ArrayList<>();
        for(String splitSql : splitSqlArray) {
            if(splitSql.equals(";")) continue;
            if(splitSql.startsWith(",")) {
                splitSql = splitSql.replaceFirst(",","");
                splitSql = splitSql + ");";
            } else {
                splitSql = splitSql + ");";
            }
//            System.out.println(sql + splitSql);
            sqls.add(sql + "values (" + splitSql.substring(splitSql.indexOf(",") + 1));
        }
        bufferedReader.close();
        //1万1分割
        List<List<String>> partitionLists = Lists.partition(sqls, 10000);
        //写入文件
        int fileIndex = 0;
        for(List<String> partitionList : partitionLists) {
            File file = new File("saas迁移数据客户车辆uat_"+ (fileIndex++) + ".sql");
            if(!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for(String temp : partitionList) {
                bufferedWriter.write(temp + "\n");
            }
            bufferedWriter.close();
        }

    }
}
