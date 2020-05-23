import java.util.Scanner;

class Channel {
    private String receiverThreadMessage;
    private String senderThreadMessage;
    private Scanner scanner;

    public Channel() {
        receiverThreadMessage = "";
        senderThreadMessage = "";
        scanner = new Scanner(System.in);
    }

    public void receive() {
        synchronized (this) {
            outer :
            while (true) {
                if (senderThreadMessage.length() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Sender sent: " + senderThreadMessage);
                switch (senderThreadMessage) {
                    case "1":
                        receiverThreadMessage = "Good Morning";
                        break;
                    case "2":
                        receiverThreadMessage = "Good Afternoon";
                        break;
                    case "3":
                        receiverThreadMessage = "Good Night";
                        break;
                    case "4":
                        break outer;
                }
                senderThreadMessage = "";
                notify();
            }
            receiverThreadMessage = "End";
            notify();
        }
    }

    public void send() {
        synchronized (this) {
            sendFirstMessage();
            while (true) {
                if (receiverThreadMessage.length() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Receiver sent: " + receiverThreadMessage);
                if (receiverThreadMessage.equals("End"))
                    break;
                senderThreadMessage = scanner.nextLine();
                receiverThreadMessage = "";
                notify();
            }
        }
    }

    private void sendFirstMessage() {
        senderThreadMessage = scanner.nextLine();
        notify();
    }
}


class Receiver implements Runnable {

    private Channel channel;

    public Receiver(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        channel.receive();
    }
}

class Sender implements Runnable {

    private Channel channel;

    public Sender(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        channel.send();
    }
}

public class SyncMessaging {
    public static void main(String[] args) {
        Channel channel = new Channel();
        Receiver receiver = new Receiver(channel);
        Sender sender = new Sender(channel);

        new Thread(receiver).start();
        new Thread(sender).start();
    }
}
