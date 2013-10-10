package com.boissinot.jenkins.csvexporter;

import com.boissinot.jenkins.csvexporter.service.http.HttpResourceContentFetcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrateur
 * Date: 09/10/13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-csv.xml");
        HttpResourceContentFetcher resourceContentFetcher = applicationContext.getBean("resourceContentFetcher", HttpResourceContentFetcher.class);

        String url = "http://controle/cgi-bin/viewvc.cgi/GD/Tools/NxExtractor/pom.xml?revision=release_1_17_1";


        for (int i = 0; i < 1870; i++) {
            System.out.println(i);
            resourceContentFetcher.getContent(url);
        }

    }
}
