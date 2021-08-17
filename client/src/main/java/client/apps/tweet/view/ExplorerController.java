package client.apps.tweet.view;

import client.apps.mainpage.view.MainPageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.request.GetExplorerRequest;
import shared.response.GetExplorerResponse;

public class ExplorerController extends MainPageController {

    ExplorerRootController explorerRootController;

    public void setExplorerRoot(Parent root, ExplorerRootController explorerRootController) {
        setCenter(root);
        this.explorerRootController = explorerRootController;
        this.addToChildControllers(explorerRootController);
    }

    public void updateExplorer(GetExplorerResponse getExplorerResponse) {
        Platform.runLater(() -> explorerRootController.updateExplorer(getExplorerResponse.getTweets()));
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            getListener().listen(new GetExplorerRequest());
            runChildControllerRequest();
        };
    }
}
