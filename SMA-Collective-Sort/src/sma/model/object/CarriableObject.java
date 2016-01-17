package sma.model.object;

import java.math.BigInteger;
import java.util.Objects;

public class CarriableObject
{
    private CarriableObject()
    {
        this.uid = getUID();
    }
    private final BigInteger uid;
    
    private static BigInteger suid = BigInteger.ZERO;
    private static synchronized BigInteger getUID()
    {
        suid = suid.add(BigInteger.ONE);
        return suid;
    }
    
    public static final CarriableObject A = new CarriableObject();
    public static final CarriableObject B = new CarriableObject();

    @Override
    public boolean equals(Object o)
    {
        if(o == this)
            return true;
        
        if(o instanceof CarriableObject)
            return ((CarriableObject)o).uid.compareTo(uid) == 0;
        
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.uid);
        return hash;
    }
}
