public class PairTwo
{
    private Thread th3;
    private Thread th4;
    private PairOne pairOne;

    public PairTwo(final String group, final String discipline)
    {

        th3 = new Thread()
        {
            public void run()
            {
                try
                {
                    for (int i = 100; i <= 500; i++)
                    {
                        System.out.print(" Th3: " + i);
                        try
                        {
                            Thread.sleep(Long.MAX_VALUE);
                        }
                        catch (InterruptedException e) {}
                        Main.printText("Th3 Disciplina: " + discipline);
                    }

                    pairOne.getTh2().join();
                    Main.printText("Th4 Grupa: " + group);

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };

        th4 = new Thread()
        {
            public void run()
            {
                try
                {
                    for (int i = 700; i >= 300; i--)
                    {
                        System.out.print(" Th4: " + i);
                        Thread.sleep(10);
                    }

                    pairOne.getTh2().join();
                    Main.printText("Th4 Grupa: " + group);

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    public Thread getTh3()
    {
        return th3;
    }

    public Thread getTh4()
    {
        return th4;
    }

    public void setPairOne(PairOne pairOne)
    {
        this.pairOne = pairOne;
    }

    public void start()
    {
        th3.start();
        th4.start();
    }
}