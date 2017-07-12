package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;


public abstract class ISite
{
    // Bitcoin : coin = Value : 1 형태로 나와야합니다.
    // coin의 가격이 1일때의 비트코인 가격을 구해주세요.
    public abstract CoinInfo getCoinInfo(EnumCoins coin);

    // 모든 비트코인 데이터를 캐시합니다.
    public abstract void Refresh();

    // 캐시된 코인들에 대한 키를 만듭니다.
    protected String CreateKey(EnumCoins coin)
    {
        String Token = coin.toString();

        if(coin == EnumCoins._1ST | coin == EnumCoins._2GIVE)
        {
            // 앞부분의 _을 잘라냅니다.
            Token = Token.substring(1);
        }

        return Token;
    }
}