package com.adpf.modules.gdtm.solr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class HBaseIndex {
    /**
     * @param args
     * @throws IOException
     * @throws SolrServerException
     */
    public static void main(String[] args) throws IOException,
            SolrServerException {
        final Configuration conf;
        HttpSolrServer solrServer = new HttpSolrServer(
                "http://192.168.40.120:8983/solr/new_core"); // 因为服务端是用的Solr自带的jetty容器，默认端口号是8983

        conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "bulktest"); // 这里指定HBase表名称
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("cf1")); // 这里指定HBase表的列族
        scan.setCaching(500);
        scan.setCacheBlocks(false);
        ResultScanner ss = table.getScanner(scan);

        System.out.println("start ...");
        int i = 0;
        try {
            for (Result r : ss) {
                SolrInputDocument solrDoc = new SolrInputDocument();
                solrDoc.addField("rowkey", new String(r.getRow()));
                for (KeyValue kv : r.raw()) {
                    String fieldName = new String(kv.getQualifier());
                    String fieldValue = new String(kv.getValue());
                    System.out.println(0);
                    if (fieldName.equals("name")) {
                    	System.out.println(1);
                        solrDoc.addField(fieldName, fieldValue);
                    }
                    System.out.println(3);
                }
                solrServer.add(solrDoc);
                solrServer.commit(true, true, true);
                i = i + 1;
                System.out.println("已经成功处理 " + i + " 条数据");
            }
            ss.close();
            table.close();
            System.out.println("done !");
        } catch (IOException e) {
        } finally {
            ss.close();
            table.close();
            System.out.println("erro !");
        }
    }

} 
