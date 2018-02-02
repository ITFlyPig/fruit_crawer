package com.wangyuelin.crawer.processor;

import com.wangyuelin.crawer.model.CookBookBean;
import com.wangyuelin.crawer.model.CookBookDetailBean;
import com.wangyuelin.crawer.model.MaterialBean;
import com.wangyuelin.crawer.model.StepBean;
import com.wangyuelin.crawer.processor.listener.Event;
import com.wangyuelin.crawer.service.FruitInfoService;
import com.wangyuelin.util.Constant;
import com.wangyuelin.util.TextUtil;
import org.apache.http.util.TextUtils;
import org.apache.log4j.BasicConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理每种水果做法的类
 */
@Component
public class CookbookProcessor implements PageProcessor, ApplicationListener<Event>{
    private HashMap<String, String> urlMap = new HashMap<String, String>();

    @Autowired
    private FruitInfoService fruitInfoService;

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private static String PREFIX = "http://so.meishi.cc/?&q=";
    private static String SUFIX = "&sort=onclick";
    private static String COOK_PREFIX = "http://www.meishij.net/zuofa";
    private StringBuilder materialStr;
    private StringBuilder stepStr;
    private List<String> fruits;
    private boolean added = false;



    public void process(Page page) {
        addUrl(page);//添加待处理的url

        String url = page.getUrl().get();
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith(COOK_PREFIX)) {//解析得到具体的做法
            String cookbookName = page.getHtml().xpath("//a[@id='tongji_title']/text()").get();
            System.out.println("获取得到的菜谱的名称：" + cookbookName);
            if (TextUtils.isEmpty(cookbookName)){
                return;
            }

            materialStr = new StringBuilder();
            String main = page.getHtml().xpath("//div[@class='yl zl clearfix']").get();//住料
            List<MaterialBean> mainList = parseMain(getElement(main));
            String assistant = page.getHtml().xpath("//div[@class='yl fuliao clearfix']").get();//辅料
            List<MaterialBean> assList = parseAssistant(getElement(assistant));

            String mainS = materialStr.toString();


            System.out.println("材料："  + mainS);
           fruitInfoService.saveCookbookDetailMaterials(cookbookName, mainS);

            //将材料保存到数据库
            fruitInfoService.saveMaterials(mainList, cookbookName);
            fruitInfoService.saveMaterials(assList, cookbookName);



            String stepStr = page.getHtml().xpath("//div[@class='editnew edit]").get();//步骤
            List<StepBean> steps = parseSteps(stepStr);

            //将菜谱的步骤保存到数据库

            fruitInfoService.saveSteps(steps, cookbookName);


        } else if (url.startsWith(PREFIX)) {//解析得到菜谱的简介
            List<String> cookBookList = page.getHtml().xpath("//div[@class='search2015_cpitem']").all();
           List<CookBookBean> cookList = parseIntro(cookBookList);
            for (CookBookBean cookBookBean:cookList){
                page.addTargetRequest(cookBookBean.getDetailUrl());
                break;
            }

            //将菜谱的简介保存到数据库
            fruitInfoService.saveCookbookIntros(cookList);

        }


    }

    public Site getSite() {
        return site;
    }

    /**
     * 添加待处理的url
     * @param page
     */
    private void addUrl(Page page){
        if (fruits == null){
            return;
        }
        if (!added){
            added = true;

        }

        int size = fruits.size();
        for (int i = 0; i < size; i++){
            String url = getUrl(fruits.get(i));
            page.addTargetRequest(url);
        }
    }




    /**
     * 获取拼接后的url
     * @param fruit
     * @return
     */
    public  String getUrl(String fruit) {
        if (TextUtils.isEmpty(fruit)) {
            return null;
        }
        String url = PREFIX + fruit + SUFIX;
        urlMap.put(url, fruit);
        return url;

    }

    /**
     * 菜谱的简介
     * @param cookBookList
     * @return
     */
    private List<CookBookBean> parseIntro(List<String> cookBookList) {
        if (cookBookList == null) {
            return null;
        }
        ArrayList<CookBookBean> books = new ArrayList<CookBookBean>();
        int size = cookBookList.size();
        for (int i = 0; i < size; i++) {
            CookBookBean cookBookBean = new CookBookBean();
            Element element = getElement(cookBookList.get(i));
            if (element == null){
                continue;
            }
            Element img = element.getElementsByTag("img").first();
            cookBookBean.setImg(img.attr("src"));
            Element a = element.getElementsByClass("cpn").first();
            cookBookBean.setName(a.text());
            cookBookBean.setDetailUrl(a.attr("href"));
            Element span = element.getElementsByTag("span").first();
            cookBookBean.setIntro(span.text());

            books.add(cookBookBean);
        }
        return books;
    }

    /**
     * 获取节点
     *
     * @param div
     * @return
     */
    private Element getElement(String div) {
        if (TextUtil.isEmpty(div)){
            return null;
        }
        return Jsoup.parse(div).getAllElements().first().getElementsByTag("div").first();
    }

    private List<CookBookDetailBean> parseDetail(String detal) {

        return null;
    }

    /**
     * 解析住料
     *
     * @param div
     * @return
     */
    private List<MaterialBean> parseMain(Element div) {
        ArrayList<MaterialBean> list = new ArrayList<MaterialBean>();
        if (div == null){
            return list;
        }

        Elements lis = div.getElementsByTag("li");
        int size = lis.size();
        for (int i = 0; i < size; i++) {
            MaterialBean materialBean = new MaterialBean();
            Element li = lis.get(i);
            Element cDiv = li.getElementsByTag("div").first();
            Element a = cDiv.getElementsByTag("a").first();
            if (a != null){
                materialBean.setName(a.text());//材料的名称
            }
            Element span = cDiv.getElementsByTag("span").first();
            if (span != null){
                materialBean.setNum(span.text());//材料的数量
            }
            Element img = li.getElementsByTag("img").first();
            if (img != null){
                materialBean.setImg(img.attr("src"));
            }

            materialBean.setType(1);

            if (TextUtil.isEmpty(materialStr.toString())){
                materialStr.append(materialBean.getName());
            }else {
                materialStr.append("," + materialBean.getName());
            }

            list.add(materialBean);

        }


        return list;
    }

    /**
     * 解析副料
     *
     * @param div
     * @return
     */
    private List<MaterialBean> parseAssistant(Element div) {
        ArrayList<MaterialBean> list = new ArrayList<MaterialBean>();
        if (div == null){
            return list;
        }

        Elements lis = div.getElementsByTag("li");
        int size = lis.size();
        for (int i = 0; i < size; i++) {
            MaterialBean materialBean = new MaterialBean();
            Element li = lis.get(i);
            Element h4 = li.getElementsByTag("h4").first();
            if (h4 != null){
                materialBean.setName(h4.getElementsByTag("a").first().text());//名称
            }
            Element span = li.getElementsByTag("span").first();
            if (span != null){
                materialBean.setNum(span.text());//数量
            }
            Element img = li.getElementsByTag("img").first();
            if (img != null){
                materialBean.setImg(img.attr("src"));
            }

            if (TextUtil.isEmpty(materialStr.toString())){
                materialStr.append(materialBean.getName());
            }else {
                materialStr.append("," + materialBean.getName());
            }
            materialBean.setType(2);
            list.add(materialBean);

        }

        return list;
    }


    /**
     * 解析得到步骤
     *
     * @param stepStr
     * @return
     */
    private List<StepBean> parseSteps(String stepStr) {
        if (stepStr == null) {
            return null;
        }
        ArrayList<StepBean> stepList = new ArrayList<StepBean>();
        Element stepContent = getElement(stepStr);
        if (stepContent == null){
            return stepList;

        }
        Elements childs = stepContent.children();
        int size = childs.size();

        for (int i = 0; i < size; i++){
            Element child = childs.get(i);
           String tagName = child.tagName();
            StepBean oneStep = null;
           if (tagName.equalsIgnoreCase("div")){
               oneStep =  parsePerStep(child);
           }else if (tagName.equalsIgnoreCase("p")){//纯图片
               Element img = child.children().first();
               if (img != null && img.tagName().equalsIgnoreCase("img")){
                   oneStep = new StepBean();
                   oneStep.setImg(img.attr("src"));
               }

           }else if (tagName.equalsIgnoreCase("h2")){
               oneStep = new StepBean();
               oneStep.setName(child.text());//标题
               //获取标题的子内容，其实是平级的
               int next = i + 1;
               if (next < size){
                   String tip  = childs.get(next).text();
                   oneStep.setTip(tip);
               }
           }
           if (oneStep != null){
               stepList.add(oneStep);
           }
        }
        return stepList;

    }

    /**
     * 解析一个步骤的div
     *
     * @param element
     * @return
     */
    private StepBean parsePerStep(Element element) {
        if (element == null) {
            return null;
        }
        StepBean step = new StepBean();
        Element em = element.getElementsByTag("em").first();
        if (em != null) {
            step.setIndex(em.text());
        }

        Element p = element.getElementsByTag("p").first();
        if (p != null) {
            step.setName(p.text());
        }

        Element img = element.getElementsByTag("img").first();
        if (img != null) {
            step.setImg(img.attr("src"));
        }

        return step;
    }

    //接收水果爬取完成的事件
    public void onApplicationEvent(Event event) {
        if (event.getOp() == Constant.EventType.MONTH_FRUIT_DONE){
//            start();
        }
    }

    /**
     * 开始爬取
     */
    public void start(){
        fruits = fruitInfoService.getFruits();
        Spider.create(this).addPipeline(new ConsolePipeline()).addUrl(getUrl("芒果")).thread(4).run();


    }


}
