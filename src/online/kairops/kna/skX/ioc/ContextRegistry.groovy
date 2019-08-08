package online.kairops.kna.skX.ioc

class ContextRegistry implements Serializable {
    private static IContext _context

    static void registerContext(IContext context) {
        _context = context
    }

    static void registerCommonContext(Object steps) {
        _context = new CommonContext(steps)
    }

    static void registerMvnContext(Object steps) {
        _context = new MvnContext(steps)
    }

    static IContext getContext() {
        return _context
    }
}
