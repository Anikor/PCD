package pcd;

public class Lister {
    private final Controller window;

    public Lister(Controller window) {
        this.window = window;
    }

    public void list(ThreadGroup group, String indent) {
        window.show(indent + "Grup " + group.getName() + " (prioritate maximă " + group.getMaxPriority() + ")");
        Thread[] threads = new Thread[group.activeCount()];
        int count = group.enumerate(threads);
        for (int i = 0; i < count; i++) {
            window.show(indent + "  Fir " + threads[i].getName() + ", grup " + threads[i].getThreadGroup().getName()
                    + ", prioritate " + threads[i].getPriority());
        }
        ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];
        int groupCount = group.enumerate(groups, false);
        for (int i = 0; i < groupCount; i++) {
            list(groups[i], indent + "  ");
        }
    }
}
