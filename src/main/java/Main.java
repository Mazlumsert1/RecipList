import controller.ReceiverController;
import controller.SenderController;
import entity.Loan;

public class Main {

    public static void main(String[] args) {

        ReceiverController receiver = new ReceiverController("guest", "datdb.cphbusiness.dk", "reciplist");
        SenderController send = new SenderController("guest","datdb.cphbusiness.dk","reciplist1");

        if (receiver.isReady()) {
            for (String msg : receiver.getMessages()) {
              send.sendMessage(msg);
            }
            receiver.close();
            send.close();
        }
    }

    }

