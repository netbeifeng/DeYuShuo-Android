package com.deyushuo.connect;

import com.deyushuo.base.ListItem;
import com.deyushuo.base.Nachrichtenleicht_Artikel_Item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class getLife_LeichtNachrichten{

    public List<ListItem> getNew(List list, String url)throws IOException {
        //String url = "https://www.nachrichtenleicht.de/nachrichten.2005.de.html";
        list.clear();
        String suffix = "";
        if(url.equals("https://www.nachrichtenleicht.de/nachrichten.2005.de.html")){
            suffix = "nachrichten";
        } else if (url.equals("https://www.nachrichtenleicht.de/kultur.2006.de.html")){
            suffix = "kultur";
        } else if (url.equals("https://www.nachrichtenleicht.de/vermischtes.2007.de.html")){
            suffix = "vermischtes";
        } else if (url.equals("https://www.nachrichtenleicht.de/sport.2004.de.html")){
            suffix = "sport";
        }

        Document doc = Jsoup.connect(url).validateTLSCertificates(true).get();

        Elements elements = doc.select("article");

        Elements date = elements.select("[class=dra-lsp-element-datum dra-lsp-color-font-inline-"+suffix+"]");
        //System.out.println(date.get(0).text());

        Elements pics = elements.select("img");
        //System.out.println(pics.get(0).attr("src"));

        Elements titel = elements.select("[class=dra-lsp-element-headline]").select("a");
        //System.out.println(titel.get(0).text());

        String link = titel.get(0).attr("href").replaceAll("html", "print");
        //System.out.println(link);// Link to Print

        for(int i=0;i<elements.size()-1;i++){
            list.add(new ListItem(titel.get(i).text(),date.get(i).text(),pics.get(i).attr("src"),titel.get(i).attr("href")));
        }
        return list;
    }

    public List<ListItem> getMorePages (List list,String url,int page) throws IOException{
        list.clear();
        String suffix = "";
        if(url.equals("https://www.nachrichtenleicht.de/nachrichten.2005.de.html")){
            suffix = "nachrichten";
        } else if (url.equals("https://www.nachrichtenleicht.de/kultur.2006.de.html")){
            suffix = "kultur";
        } else if (url.equals("https://www.nachrichtenleicht.de/vermischtes.2007.de.html")){
            suffix = "vermischtes";
        } else if (url.equals("https://www.nachrichtenleicht.de/sport.2004.de.html")){
            suffix = "sport";
        }

        url += ("?drbm:page="+page);
        Document doc = Jsoup.connect(url).validateTLSCertificates(true).get();

        Elements elements = doc.select("article");

        Elements date = elements.select("[class=dra-lsp-element-datum dra-lsp-color-font-inline-"+suffix+"]");
        //System.out.println(date.get(0).text());

        Elements pics = elements.select("img");
        //System.out.println(pics.get(0).attr("src"));

        Elements titel = elements.select("[class=dra-lsp-element-headline]").select("a");
        //System.out.println(titel.get(0).text());

        String link = titel.get(0).attr("href").replaceAll("html", "print");
        //System.out.println(link);// Link to Print

        for(int i=0;i<elements.size()-1;i++){
            list.add(new ListItem(titel.get(i).text(),date.get(i).text(),pics.get(i).attr("src"),titel.get(i).attr("href")));
        }
        return list;

    }


    public Nachrichtenleicht_Artikel_Item getDetails(String url) throws IOException{
        Nachrichtenleicht_Artikel_Item na = new Nachrichtenleicht_Artikel_Item();
        url = "https://www.nachrichtenleicht.de/" + url;
        Document doc = Jsoup.connect(url).validateTLSCertificates(true).get();



        Elements sec = doc.select("[class=dra-lsp-artikel-haupttext-box]");
        String audio_Link=doc.select("audio").attr("src");
        na.setAudio_Link(audio_Link);
        //System.out.println(doc2.select("audio").attr("src"));

        String sub_Titel = sec.select("[class=dra-lsp-artikel-haupttex-kurztext]").text();
        na.setSub_Titel(sub_Titel);
        //System.out.println(sub_Titel);

        String str_main_Context = "";
        Elements main_Context = sec.select("[class=dra-lsp-artikel-haupttext-absatz rte]").select("p");
        for(int i=0;i<main_Context.size();i++) {
            str_main_Context = str_main_Context+ (main_Context.get(i).text()+"\n");
        }
        na.setMain_Context(str_main_Context);
        //System.out.println(main_Context.toString());

        Elements erklar = doc.select("[class=dra-lsp-artikel-woerterbuch-begriff]");
        //System.out.println(erklar.toString());
        Elements wort = erklar.select("b");
        erklar.select("b").remove();
        erklar.select("br").remove();
        Elements begriffe = erklar.select("li");

        //System.out.println(erklar.toString());

        List<Map<String,String>> list = new ArrayList<>();;
        for (int i=0; i<wort.size();i++) {
            Map<String,String> map = new HashMap<String, String>();
            map.put(wort.get(i).text(), begriffe.get(i).text());
            list.add(map);
        }
        na.setDiscrption(list);
        //System.out.println(list.toString());


        return na;
    }
    /**
     * 现在很多站点都是SSL对数据传输进行加密，这也让普通的HttpConnection无法正常的获取该页面的内容，
     * 而Jsoup起初也对此没有做出相应的处理，
     * 想了一下是否可以让Jsoup可以识别所有的SSL加密过的页面，查询了一些资料，发现可以为本地HttpsURLConnection配置一个“万能证书”，其原理是就是：
     * 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
     * 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
     * 后来Jsoup Connection提供了validateTLSCertificates(boolean validate)//是否进行TLS证书验证,不推荐
     */
    static {
        try {
            // 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            // 创建随机证书生成工厂
            //SSLContext context = SSLContext.getInstance("TLS");
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());

            // 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
