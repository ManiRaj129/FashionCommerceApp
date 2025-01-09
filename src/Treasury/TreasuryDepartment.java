package src.Treasury;

import src.Treasury.src.PayrollController;

public class TreasuryDepartment {

    public void start() throws Exception {
        PayrollController controller = new PayrollController();
        controller.run();
    }
}
