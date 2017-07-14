package Bagua.Interface;

import java.io.IOException;

import Bagua.Base.CoinPrice;
import Bagua.Base.EnumCoin;

import org.json.simple.parser.ParseException;

public interface ICachedSite
{
    // 사이트에서 다시 정보를 가저오고 캐시합니다.
    public void Refresh() throws IOException, ParseException;
    
    // 캐시된 사이트의 가격정보를 가져옵니다.
    // 가격정보는 변수명의 coin의 갯수가 1일때의 그 사이트의 BTC 가격을 비율로 가져옵니다.
    public CoinPrice getPrice(EnumCoin coin);
}