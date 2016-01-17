package sma.model.agent;

public class AgentParameterSet
{
    public static Builder create()
    {
        return new Builder();
    }
    public static class Builder
    {
        public Builder()
        { }
        
        private int MAX_HISTORY = 10;
        private double K_MINUS = 0.3;
        private double K_PLUS = 0.1;
        private double ERROR = 0;
        private int NB_MOVE = 1;
        
        public Builder setMaxHistory(int maxHistory)
        {
            this.MAX_HISTORY = maxHistory;
            return this;
        }
        public Builder setKMinus(double kMinus)
        {
            this.K_MINUS = kMinus;
            return this;
        }
        public Builder setKPlus(double kPlus)
        {
            this.K_PLUS = kPlus;
            return this;
        }
        public Builder setError(double error)
        {
            this.ERROR = error;
            return this;
        }
        public Builder setNbMove(int nbMove)
        {
            this.NB_MOVE = nbMove;
            return this;
        }
        
        public AgentParameterSet build()
        {
            return new AgentParameterSet(
                    MAX_HISTORY,
                    K_MINUS,
                    K_PLUS,
                    ERROR,
                    NB_MOVE);
        }
    }
    
    public AgentParameterSet(
            int MAX_HISTORY,
            double K_MINUS,
            double K_PLUS,
            double ERROR,
            int NB_MOVE)
    {
        this.MAX_HISTORY = MAX_HISTORY;
        this.K_MINUS = K_MINUS;
        this.K_PLUS = K_PLUS;
        this.ERROR = ERROR;
        this.NB_MOVE = NB_MOVE;
    }
    
    public final int MAX_HISTORY;
    public final double K_MINUS;
    public final double K_PLUS;
    public final double ERROR;
    public final int NB_MOVE;
}
