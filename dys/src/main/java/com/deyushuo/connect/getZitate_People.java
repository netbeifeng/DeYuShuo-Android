package com.deyushuo.connect;


import com.deyushuo.base.ListItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class getZitate_People {
    public List<ListItem> getZitate(List list, String url)throws IOException {
        String url_zita = "http://zitate.net/die-besten-zitate";
        list.clear();
        Document doc = Jsoup.parse(new URL(url_zita).openStream(), "utf-8", url);
        Elements elements = doc.select("[class=quote]");
        Elements author = doc.select("[class=quote-btn]");
        Elements imageLink = doc.select("[class=quote-pp]");
        while (imageLink.size()<5)
            imageLink.add(new Element("<a src=\"\"></a>"));
        Elements weiter = doc.select("[title=→ Zitat]");
        for(int i=0;i<elements.size()-1;i++){
            list.add(new ListItem(elements.get(i).text(),author.get(i).text(),url+imageLink.get(i).attr("src"),url+weiter.get(i).attr("href")));
        }
        return  list;
    }



}
