package Bagua.Interface;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;


public interface ISite
{
        // 지원되는 코인의 종류를 모두 구합니다.
    public abstract EnumCoins[] getAvailableCoins();

    // Bitcoin : coin = Value : 1 형태로 나와야합니다.
    // coin의 가격이 1일때의 비트코인 가격을 구해주세요.
    public abstract CoinInfo getCoinInfo(EnumCoins coin);

    // 모든 비트코인 데이터를 캐시합니다.
    public abstract void Refresh();
}