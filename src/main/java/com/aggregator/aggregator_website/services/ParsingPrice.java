package com.aggregator.aggregator_website.services;

import com.aggregator.aggregator_website.dto.MonitoringPriceDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ParsingPrice {

    public String getParsePriceCitilink(String url) throws IOException, NullPointerException{
        return getParsingPrice(url,".ProductPagePriceSection__default-price_current-price");
    }

    public String getParsePriceRegard(String url) throws IOException, NullPointerException{
        return getParsingPrice(url,".PriceBlock_price__3hwFe");
    }

    public String getСomputerMarket(String url)throws IOException, NullPointerException{
        return getParsingPrice(url,".cnt-price");
    }

    public String getQuke(String url) throws IOException, NullPointerException{
        return getParsingPrice(url,".price__value");
    }

    public String getKNS(String url)throws IOException, NullPointerException{
        return getParsingPrice(url,".price-val");
    }

    private String getParsingPrice(String url, String selector) throws IOException, NullPointerException  {
        String price = "";
        try {
            Document document = Jsoup.connect(url).get();
            Element div = document.select(selector).first();
            if(div != null){
                price = div.text();
            }
        }
        catch (NullPointerException ex){
            throw ex;
        }
        catch (IOException e) {
            throw e;
        }
        return price;
    }
}
