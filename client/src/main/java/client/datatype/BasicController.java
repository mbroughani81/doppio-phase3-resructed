package client.datatype;

import shared.loop.Loop;
import shared.request.RequestListener;

import java.util.LinkedList;

public class BasicController {
    RequestListener listener;
    private LinkedList<BasicController> childControllers = new LinkedList<>();

    public RequestListener getListener() {
        return listener;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public Runnable getUpdateAction() {
        return () -> {};
    }

    public Runnable getRequestAction() {
        return () -> {};
    }

    final public void addToChildControllers(BasicController basicController) {
        basicController.setListener(getListener());
        childControllers.add(basicController);
    }

    final public void clearChildControllers() {
        childControllers.clear();
    }

    final public void runChildControllerRequest() {
        try {
            for (BasicController basicController : childControllers) {
                basicController.getRequestAction().run();
            }
        } catch (Exception e) {

        }
    }



    final public void runChildControllerUpdate() {
        for (BasicController basicController : childControllers) {
            basicController.getUpdateAction().run();
        }
    }
}
