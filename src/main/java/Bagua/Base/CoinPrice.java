package Bagua.Base;


public class CoinPrice
{
    public Double          Ask         = 0.0;
    public Double          Bid         = 0.0;

    public Double          Highest     = 0.0;
    public Double          Lowest      = 0.0;

    public CoinPrice()
    {
        
    }
    public CoinPrice(Double ask, Double bid, Double highest, Double lowest)
    {
        Ask = ask;
        Bid = bid;
        Highest = highest;
        Lowest  = lowest;
    }
}