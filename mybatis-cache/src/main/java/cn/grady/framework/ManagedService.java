package cn.grady.framework;

import lombok.Getter;
import lombok.Setter;

/**
 * ClassName ManagedService
 * Description 后台服务
 * Create by gradyly
 * Date 2022/4/20
 */
@Getter
@Setter
public abstract  class ManagedService implements Orderable {

    protected boolean isStared = false;
    protected boolean isShuttingdown = false;

    public String getName(){
        return this.getClass().getSimpleName();
    }

    public void startup(){
        startupInner();
        isStared = true;

    }

    public void shutdown(){
        isShuttingdown = true;
        shutdownInner();
    }

    protected abstract void shutdownInner();

    protected abstract void startupInner();

    public abstract String getDescription();

    @Override
    public abstract int getOrder() ;
}
