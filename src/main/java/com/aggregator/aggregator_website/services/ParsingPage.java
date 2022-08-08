package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.dto.PageDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParsingPage {

    public PageDto getParsePage(String url) throws IOException,NullPointerException {
        PageDto pageDto = new PageDto();

        try{
            Document doc = Jsoup.connect(url).get();
            Element elementHOne = doc.select("h1").first();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
            Element paragraph = doc.select("p:not(:has(a))").first();

            parseElementHOne(pageDto,doc,elementHOne);
            parseElementImage(pageDto,images);
            parseElementParagraph(pageDto,paragraph);

            pageDto.setLink(url);
        }
        catch (NullPointerException e){
            throw e;
        }
        catch (IOException ex){
            throw ex;
        }
        return pageDto;
    }

    private void parseElementParagraph(PageDto pageDto, Element paragraph){
        String p = "";
        if (paragraph != null){
            p = paragraph.text();
            if (!p.isEmpty() && p != null && p.length() > 50){
                pageDto.setText(p);
            }
            else {
                pageDto.setText("Для получения полной информации нажмите на кнопку \"подробнее\"");
            }
        }
        else{
            pageDto.setText("Чтобы узнать полную информацию нажмите на кнопку \"подробнее\"");
        }

    }

    private void parseElementHOne(PageDto pageDto, Document doc, Element elementHOne){
        if(elementHOne != null){
            String title = elementHOne.text();
            if(!title.isEmpty() && title != null){
                pageDto.setTitle(title);
            }
            else{
                title ="На данном сайте отсутсвует запись в заголовках..";
                pageDto.setTitle(title);
            }
        }
        else {
            Element elementHTwo = doc.select("h2").first();

            String text1 = elementHTwo.text();

            if(!text1.isEmpty() && text1 != null){
                pageDto.setTitle(text1);
            }
            else {
                text1 ="На данном сайте отсутсвует запись в заголовках..";
                pageDto.setTitle(text1);
            }
        }
    }

    private void parseElementImage(PageDto pageDto,Elements images){
        Set<String> imgWidths = new HashSet<>();
        Set<String> imgList = new HashSet<>();
        Set<String> style = new LinkedHashSet<>();
        Set<String> style2 = new LinkedHashSet<>();

        for (Element image : images){
            String srcImg = image.attr("src");

            boolean startWord = srcImg.startsWith("https:");
            boolean start = srcImg.startsWith("http:");

            if(startWord | start){
                if(image.hasAttr("style")){
                    String stl = image.attr("style");
                    String pattern = "([a-zA-Z]+)\\s*:\\s*([\\d]+)([a-zA-Z]+)([;])\\s*\\s*([a-zA-Z]+)\\s*:\\s*([\\d]+)([a-zA-Z]+)([;])\\s*";

                    Pattern patter = Pattern.compile(pattern);
                    Matcher matcher = patter.matcher(stl);

                    if(matcher.matches()){

                        String width2 = stl.substring(stl.indexOf(":"),stl.indexOf(";"));

                        String w = width2.replaceAll("\\D+","");

                        if(Integer.parseInt(w) >= 680){
                            imgWidths.add(image.attr("src"));
                        }

                        for (String img : imgWidths){
                            style.add(img);
                        }
                    }
                    else {
                        style.add("img/comp2.jpg");
                    }
                }
                else if(image.hasAttr("width") && image.hasAttr("height")){
                    String imgWidth = image.attr("width");
                    String imgHeight = image.attr("height");

                    String imgW = imgWidth.replaceAll("\\D+","");
                    String imgH = imgHeight.replaceAll("\\D+","");

                    if(Integer.parseInt(imgW) >= 600 && Integer.parseInt(imgH) > 100){
                        imgList.add(image.attr("src"));
                    }
                    else {
                        style2.add("img/comp3.jpg");
                    }

                    for (String img : imgList){
                        style.add(img);
                    }

                }
                else {
                    style2.add(image.attr("src"));
                }
            }

        }

        String src ="";
        if (style != null && !style.isEmpty()){
            src = style.stream().findFirst().get();
        }
        else{
            src = style2.stream().findFirst().orElse(null);
        }

        pageDto.setImageUrl(src);
    }
}
