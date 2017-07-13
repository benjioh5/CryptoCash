package Bagua;

import java.util.EnumMap;

import Bagua.Base.CoinInfo;
import Bagua.Base.EnumCoins;
import Bagua.Base.EnumSites;
import Bagua.Interface.ISite;
import Bagua.Sites.*;

public class CryptoCash
{
    EnumMap<EnumSites, ISite> Sites;

    public CryptoCash() throws Exception
    {
        Sites.put(EnumSites.Bittrex, new BittrexAPI());
    }
    public void Refresh(EnumSites site)
    {
        Sites.get(site).Refresh();
    }
    public CoinInfo getCoinInfo(EnumSites site, EnumCoins coin)
    {
        return Sites.get(site).getCoinInfo(coin);
    }
}