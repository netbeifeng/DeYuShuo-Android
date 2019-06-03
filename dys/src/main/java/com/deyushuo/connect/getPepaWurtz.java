package com.deyushuo.connect;

import com.deyushuo.base.ListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class getPepaWurtz {
    static String baseURL = "https://www.godic.net";
    public List<ListItem> getList(List list)throws IOException {
        list.clear();
        String url = "https://www.godic.net/ting/article?id=c04bcefd-c7ff-11e6-a1ed-000c29ffef9b";
        Document doc = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
        Elements elements = doc.select("[class=contents frap]").select("dl");
        //System.out.println(elements.toString());
        Elements imgs = elements.select("[class=img]").select("img");
        Elements titels = elements.select("dd").select("[class=title]");
        for(int i =0 ; i<elements.size();i++) {

            String titel =  titels.get(i).text();
            String link = baseURL+elements.select("a").get(i).attr("href");


            String imgLink = imgs.get(i).attr("src");
            list.add(new ListItem(titel,"Quelle:每日德语听力",imgLink,link));
            //System.out.println(titel+"\n"+link+"\n"+imgLink);
        }
        return list;
    }

    public String getVideoURL (String url) throws IOException{
        Document doc = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
        String video = doc.select("video").select("[class=video]").attr("src");

        //System.out.println(video);
        return video;
    }
}
