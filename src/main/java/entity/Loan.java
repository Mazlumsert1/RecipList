package entity;

public class Loan {
    private int ssn,creditScore,
            loanAmount,date;

    public Loan(int ssn, int creditScore, int loanAmount, int date) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
        this.date = date;
    }
    public Loan() {
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "ssn=" + ssn +
                ", creditScore=" + creditScore +
                ", loanAmount=" + loanAmount +
                ", date=" + date +
                '}';
    }


}
