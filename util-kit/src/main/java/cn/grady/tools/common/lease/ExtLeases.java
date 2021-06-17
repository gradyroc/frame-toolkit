package cn.grady.tools.common.lease;

/**
 * @author grady
 * @version 1.0, on 2:21 2021/6/12.
 */
public class ExtLeases extends Leases {
    /**
     * Creates a lease monitor
     *
     * @param leaseCheckFrequency - how often the lease should be checked
     *                            (milliseconds)
     */
    public ExtLeases(int leaseCheckFrequency) {
        super(leaseCheckFrequency);
    }
}
